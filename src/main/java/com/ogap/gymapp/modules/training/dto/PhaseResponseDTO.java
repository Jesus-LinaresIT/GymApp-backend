package com.ogap.gymapp.modules.training.dto;

import java.util.List;
import java.util.UUID;

public record PhaseResponseDTO(
      UUID id,
      String name,
      Integer orderIndex,
      List<RoutineResponseDTO> routines) {
}
