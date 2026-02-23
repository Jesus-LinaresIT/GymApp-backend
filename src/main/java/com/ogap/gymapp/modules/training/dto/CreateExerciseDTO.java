package com.ogap.gymapp.modules.training.dto;

import java.util.UUID;

public record CreateExerciseDTO(
      UUID routineId,
      String name,
      Integer sets,
      String reps,
      Double weight,
      Integer rir,
      Integer orderIndex) {
}
