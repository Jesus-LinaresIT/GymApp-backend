package com.ogap.gymapp.modules.execution.service;

import com.ogap.gymapp.modules.execution.WorkoutLog;
import com.ogap.gymapp.modules.execution.dto.SetLogDTO;
import com.ogap.gymapp.modules.execution.dto.WorkoutLogRequestDTO;
import com.ogap.gymapp.modules.execution.repository.WorkoutLogRepository;
import com.ogap.gymapp.modules.training.Exercise;
import com.ogap.gymapp.modules.training.repository.ExerciseRepository;
import com.ogap.gymapp.modules.user.User;
import com.ogap.gymapp.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {

   private final WorkoutLogRepository workoutLogRepository;
   private final ExerciseRepository exerciseRepository; // Need to create optional, using generic JPA for now
   private final UserRepository userRepository;

   @Transactional
   public void logWorkout(String clerkId, List<WorkoutLogRequestDTO> logs) {
      User user = userRepository.findByClerkId(clerkId)
            .orElseThrow(() -> new RuntimeException("User not found"));

       List<WorkoutLog> entitiesToSave = new ArrayList<>();

       // 1. Recorremos cada "bloque" de ejercicio recibido
       for (WorkoutLogRequestDTO logDTO : logs) {

           Exercise exercise = exerciseRepository.findById(logDTO.exerciseId())
                   .orElseThrow(() -> new RuntimeException("Exercise not found: " + logDTO.exerciseId()));

           // Contador para saber si es el set 1, 2, 3...
           AtomicInteger setCounter = new AtomicInteger(1);

           // 2. Recorremos los sets DENTRO de ese ejercicio
           List<WorkoutLog> setsForThisExercise = logDTO.sets().stream().map(setDetail ->
                WorkoutLog.builder()
                   .user(user)
                   .exercise(exercise)
                   .setsDone(setCounter.getAndIncrement())
                   .reps(setDetail.reps())
                   .weight(setDetail.weight())
                   .rir(setDetail.rir())
                   .build()
           ).toList();

           entitiesToSave.addAll(setsForThisExercise);
       }

      workoutLogRepository.saveAll(entitiesToSave);
   }
}
