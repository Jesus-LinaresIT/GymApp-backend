package com.ogap.gymapp.modules.training.dto;

import com.ogap.gymapp.modules.training.AccessType;

import java.util.List;
import java.util.UUID;

public record CourseResponseDTO(
      UUID id,
      String title,
      String description,
      AccessType accessType,
      List<PhaseResponseDTO> phases) {
}
