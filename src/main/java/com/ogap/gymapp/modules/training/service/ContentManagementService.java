package com.ogap.gymapp.modules.training.service;

import com.ogap.gymapp.helpers.SecurityPass;
import com.ogap.gymapp.modules.training.Course;
import com.ogap.gymapp.modules.training.Exercise;
import com.ogap.gymapp.modules.training.Phase;
import com.ogap.gymapp.modules.training.Routine;
import com.ogap.gymapp.modules.training.dto.*;
import com.ogap.gymapp.modules.training.mapper.TrainingMapper;
import com.ogap.gymapp.modules.training.repository.CourseRepository;
import com.ogap.gymapp.modules.training.repository.ExerciseRepository;
import com.ogap.gymapp.modules.training.repository.PhaseRepository;
import com.ogap.gymapp.modules.training.repository.RoutineRepository;
import com.ogap.gymapp.modules.user.User;
import com.ogap.gymapp.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentManagementService {

   private final CourseRepository courseRepository;
   private final PhaseRepository phaseRepository;
   private final RoutineRepository routineRepository;
   private final ExerciseRepository exerciseRepository;
   private final UserRepository userRepository;
   private final TrainingMapper trainingMapper;
   private final SecurityPass securityPass;

   @Transactional
   public CourseResponseDTO createCourse(CreateCourseDTO dto, Jwt clerkId) {


      User author = userRepository.findByClerkId(clerkId.getSubject())
            .orElseThrow(() -> new RuntimeException("Author (Coach) not found"));

      Course course = Course.builder()
            .title(dto.title())
            .description(dto.description())
            .level(dto.level())
            .accessType(dto.accessType())
            .price(dto.price())
            .author(author)
            .build();

      course = courseRepository.save(course);
      return trainingMapper.toCourseDTO(course);
   }

   @Transactional
   public PhaseResponseDTO createPhase(CreatePhaseDTO dto) {
      Course course = courseRepository.findById(dto.courseId())
            .orElseThrow(() -> new RuntimeException("Course not found"));

      Phase phase = Phase.builder()
            .course(course)
            .name(dto.name())
            .orderIndex(dto.orderIndex())
            .build();

      phase = phaseRepository.save(phase);
      return trainingMapper.toPhaseDTO(phase);
   }

   @Transactional
   public RoutineResponseDTO createRoutine(CreateRoutineDTO dto) {
      Phase phase = phaseRepository.findById(dto.phaseId())
            .orElseThrow(() -> new RuntimeException("Phase not found"));

      Routine routine = Routine.builder()
            .phase(phase)
            .title(dto.title())
            .dayLabel(dto.dayLabel())
            .orderIndex(dto.orderIndex())
            .build();

      routine = routineRepository.save(routine);
      return trainingMapper.toRoutineDTO(routine);
   }

   @Transactional
   public ExerciseResponseDTO createExercise(CreateExerciseDTO dto) {
      Routine routine = routineRepository.findById(dto.routineId())
            .orElseThrow(() -> new RuntimeException("Routine not found"));

      Exercise exercise = Exercise.builder()
            .routine(routine)
            .name(dto.name())
            .sets(dto.sets())
            .repsTarget(dto.reps())
            .weightSuggested(dto.weight())
            .rirTarget(dto.rir())
            .orderIndex(dto.orderIndex())
            .build();

      exercise = exerciseRepository.save(exercise);
      return trainingMapper.toExerciseDTO(exercise);
   }
}
