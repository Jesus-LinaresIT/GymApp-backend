package com.ogap.gymapp.modules.execution.dto;

import java.util.List;
import java.util.UUID;

public record WorkoutLogRequestDTO(
      UUID exerciseId,
      List<SetLogDTO> sets){
}