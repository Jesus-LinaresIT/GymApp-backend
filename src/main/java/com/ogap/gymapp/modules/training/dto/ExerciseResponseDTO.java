package com.ogap.gymapp.modules.training.dto;

import java.util.UUID;

public record ExerciseResponseDTO(
      UUID id,
      String name,
      String muscleGroup,
      Integer sets,
      String repsTarget,
      Double weightSuggested,
      Integer rirTarget,
      Integer restSeconds) {
}
