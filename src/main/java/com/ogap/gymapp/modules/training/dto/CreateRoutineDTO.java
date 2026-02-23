package com.ogap.gymapp.modules.training.dto;

import java.util.UUID;

public record CreateRoutineDTO(
      UUID phaseId,
      String title,
      String dayLabel,
      Integer orderIndex) {
}
