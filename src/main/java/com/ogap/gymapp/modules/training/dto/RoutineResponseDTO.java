package com.ogap.gymapp.modules.training.dto;

import java.util.List;
import java.util.UUID;

public record RoutineResponseDTO(
      UUID id,
      String dayLabel,
      List<ExerciseResponseDTO> exercises) {
}
