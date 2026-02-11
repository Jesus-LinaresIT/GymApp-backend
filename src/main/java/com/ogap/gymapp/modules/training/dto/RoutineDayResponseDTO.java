package com.ogap.gymapp.modules.training.dto;

public record RoutineDayResponseDTO(
      String phaseName,
      Integer dayNumber, // e.g. 1 for first day, or index
      RoutineResponseDTO routine) {
}
