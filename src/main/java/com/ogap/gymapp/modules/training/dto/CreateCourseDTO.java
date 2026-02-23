package com.ogap.gymapp.modules.training.dto;

import com.ogap.gymapp.modules.training.AccessType;

public record CreateCourseDTO(
      String title,
      String description,
      String level,
      AccessType accessType,
      Double price) {
}
