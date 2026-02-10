package com.ogap.gymapp.modules.training.service;

import com.ogap.gymapp.modules.training.Course;
import com.ogap.gymapp.modules.training.dto.CourseResponseDTO;
import com.ogap.gymapp.modules.training.mapper.TrainingMapper;
import com.ogap.gymapp.modules.training.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogap.gymapp.modules.training.Routine;
import com.ogap.gymapp.modules.training.dto.RoutineDayResponseDTO;
import com.ogap.gymapp.modules.user.User;
import com.ogap.gymapp.modules.user.UserProgress;
import com.ogap.gymapp.modules.user.repository.UserProgressRepository;
import com.ogap.gymapp.modules.user.repository.UserRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

   private final CourseRepository courseRepository;
   private final TrainingMapper trainingMapper;
   private final UserRepository userRepository;
   private final UserProgressRepository userProgressRepository;

   @Transactional(readOnly = true)
   public List<CourseResponseDTO> getAllCourses() {
      return courseRepository.findAllWithAllDetails().stream()
            .map(trainingMapper::toCourseDTO)
            .toList();
   }

   @Transactional(readOnly = true)
   public RoutineDayResponseDTO getRoutineForToday(String clerkId) {
      User user = userRepository.findByClerkId(clerkId)
            .orElseThrow(() -> new RuntimeException("User not found"));

      if (!user.isHasActiveSubscription()) {
         throw new RuntimeException("User does not have an active subscription");
      }

      UserProgress progress = userProgressRepository.findByUserId(user.getId())
            .orElseThrow(() -> new RuntimeException("User progress not found"));

      if (progress.getCurrentPhase() == null) {
         throw new RuntimeException("No active phase found for user");
      }

      List<Routine> routines = progress.getCurrentPhase().getRoutines();
      if (routines.isEmpty()) {
         throw new RuntimeException("No routines found in current phase");
      }

      int index = progress.getNextRoutineIndex() % routines.size();
      Routine routine = routines.get(index);

      return new RoutineDayResponseDTO(
            progress.getCurrentPhase().getName(),
            index + 1,
            trainingMapper.toRoutineDTO(routine));
   }
}
