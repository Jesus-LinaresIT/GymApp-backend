package com.ogap.gymapp.modules.training.mapper;

import com.ogap.gymapp.modules.training.*;
import com.ogap.gymapp.modules.training.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainingMapper {

   public CourseResponseDTO toCourseDTO(Course course) {
      if (course == null)
         return null;
      List<PhaseResponseDTO> phases = course.getPhases().stream()
            .map(this::toPhaseDTO)
            .toList();
      return new CourseResponseDTO(
            course.getId(),
            course.getTitle(),
            course.getDescription(),
            course.getAccessType(),
            phases);
   }

   public PhaseResponseDTO toPhaseDTO(Phase phase) {
      if (phase == null)
         return null;
      List<RoutineResponseDTO> routines = phase.getRoutines().stream()
            .map(this::toRoutineDTO)
            .toList();
      return new PhaseResponseDTO(
            phase.getId(),
            phase.getName(),
            phase.getOrderIndex(),
            routines);
   }

   public RoutineResponseDTO toRoutineDTO(Routine routine) {
      if (routine == null)
         return null;
      List<ExerciseResponseDTO> exercises = routine.getExercises().stream()
            .map(this::toExerciseDTO)
            .toList();
      return new RoutineResponseDTO(
            routine.getId(),
            routine.getDayLabel(),
            exercises);
   }

   public ExerciseResponseDTO toExerciseDTO(Exercise exercise) {
      if (exercise == null)
         return null;
      return new ExerciseResponseDTO(
            exercise.getId(),
            exercise.getName(),
            exercise.getMuscleGroup(),
            exercise.getSets(),
            exercise.getRepsTarget(),
            exercise.getWeightSuggested(),
            exercise.getRirTarget(),
            exercise.getRestSeconds());
   }
}
