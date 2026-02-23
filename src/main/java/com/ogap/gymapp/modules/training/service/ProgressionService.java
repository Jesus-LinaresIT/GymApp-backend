package com.ogap.gymapp.modules.training.service;

import com.ogap.gymapp.modules.execution.repository.WorkoutLogRepository;
import com.ogap.gymapp.modules.notifications.service.NotificationService;
import com.ogap.gymapp.modules.training.Phase;
import com.ogap.gymapp.modules.training.Routine;
import com.ogap.gymapp.modules.training.repository.PhaseRepository;
import com.ogap.gymapp.modules.user.User;
import com.ogap.gymapp.modules.user.UserProgress;
import com.ogap.gymapp.modules.user.dto.ProgressStatusDTO;
import com.ogap.gymapp.modules.user.repository.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressionService {

   private final UserProgressRepository userProgressRepository;
   private final WorkoutLogRepository workoutLogRepository;
   private final PhaseRepository phaseRepository;
   private final NotificationService notificationService;

   @Transactional
   public void checkAndAdvancePhase(User user) {
      UserProgress progress = userProgressRepository.findByUserId(user.getId())
            .orElseThrow(() -> new RuntimeException("User progress not found"));

      Phase currentPhase = progress.getCurrentPhase();
      if (currentPhase == null)
         return;

      List<Routine> phaseRoutines = currentPhase.getRoutines();
      // Check if user has logged at least one exercise for EACH routine in the phase
      // This is a simplified logic. Real logic might check "all exercises in
      // routine".

      long completedRoutinesCount = phaseRoutines.stream()
            .filter(routine -> hasCompletedRoutine(user, routine))
            .count();

      // 2. Si ya hizo A, B y C (todas las rutinas base), avanzamos de FASE.
      // NOTA: En la vida real, una fase dura 4 semanas.
      // Para este MVP, avanzamos apenas complete el ciclo una vez.
      if (completedRoutinesCount == phaseRoutines.size() && !phaseRoutines.isEmpty()) {
         advanceToNextPhase(progress, currentPhase, user);
      }
   }

   private boolean hasCompletedRoutine(User user, Routine routine) {
      // Check if ANY exercise in this routine has a log by this user
      // Ideally we check if *sufficient* exercises were done.
      return workoutLogRepository.existsByUserAndExercise_Routine(user, routine);
   }

   private void advanceToNextPhase(UserProgress progress, Phase currentPhase, User user) {
      // Find next phase by orderIndex
      List<Phase> coursePhases = currentPhase.getCourse().getPhases();

      Phase nextPhase = coursePhases.stream()
            .filter(p -> p.getOrderIndex() > currentPhase.getOrderIndex())
            .min(Comparator.comparingInt(Phase::getOrderIndex)) // Assumes sorted or we sort
            .orElse(null);

      if (nextPhase != null) {
         System.out.println("🚀 AVANCE DE FASE: " + currentPhase.getName() + " -> " + nextPhase.getName());
         progress.setCurrentPhase(nextPhase);
         progress.setNextRoutineIndex(0);
         userProgressRepository.save(progress);

         notificationService.sendPhaseAdvancedNotification(user, nextPhase.getName());
      } else {
         System.out.println("🏆 CURSO COMPLETADO");
         notificationService.sendCourseCompletedNotification(user, currentPhase.getCourse().getTitle());
      }
   }

   @Transactional(readOnly = true)
   public ProgressStatusDTO getProgressStatus(User user) {
      UserProgress progress = userProgressRepository.findByUserId(user.getId())
            .orElseThrow(() -> new RuntimeException("User progress not found"));

      String courseName = progress.getCurrentCourse() != null ? progress.getCurrentCourse().getTitle() : "N/A";
      String phaseName = progress.getCurrentPhase() != null ? progress.getCurrentPhase().getName() : "N/A";

      // Calculate percentage of current phase
      double percentage = 0.0;
      if (progress.getCurrentPhase() != null) {
         List<Routine> routines = progress.getCurrentPhase().getRoutines();
         if (!routines.isEmpty()) {
            long completed = routines.stream().filter(r -> hasCompletedRoutine(user, r)).count();
            percentage = (double) completed / routines.size() * 100.0;
         }
      }

      return new ProgressStatusDTO(courseName, phaseName, percentage);
   }
}
