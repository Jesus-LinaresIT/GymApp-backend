package com.ogap.gymapp.modules.user.dto;

public record ProgressStatusDTO(
      String currentCourseName,
      String currentPhaseName,
      Double progressPercentage) {
}
