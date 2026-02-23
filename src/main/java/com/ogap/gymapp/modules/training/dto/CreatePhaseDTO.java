package com.ogap.gymapp.modules.training.dto;

import java.util.UUID;

public record CreatePhaseDTO(
      UUID courseId,
      String name,
      Integer orderIndex) {
}
