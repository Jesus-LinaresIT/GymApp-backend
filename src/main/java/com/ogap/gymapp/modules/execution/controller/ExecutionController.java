package com.ogap.gymapp.modules.execution.controller;

import com.ogap.gymapp.helpers.SecurityPass;
import com.ogap.gymapp.modules.execution.dto.WorkoutLogRequestDTO;
import com.ogap.gymapp.modules.execution.service.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/execution")
@RequiredArgsConstructor
@Tag(name = "Execution", description = "Workout execution and logging")
public class ExecutionController {

    private final WorkoutService workoutService;
    private final SecurityPass securityPass;

    @PostMapping("/log")
    @Operation(summary = "Log Workout", description = "Logs a list of executed sets for a workout")
    public ResponseEntity<Void> logWorkout(
         @AuthenticationPrincipal Jwt jwt,
         @RequestBody List<WorkoutLogRequestDTO> logs) {

       workoutService.logWorkout(jwt.getSubject(), logs);

      return ResponseEntity.ok().build();
    }
}
