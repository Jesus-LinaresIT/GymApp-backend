package com.ogap.gymapp.modules.execution.controller;

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
@RequestMapping("/api/public/v1/execution")
@RequiredArgsConstructor
@Tag(name = "Execution", description = "Workout execution and logging")
public class ExecutionController {

   private final WorkoutService workoutService;

   @PostMapping("/log")
   @Operation(summary = "Log Workout", description = "Logs a list of executed sets for a workout")
   public ResponseEntity<Void> logWorkout(
         @AuthenticationPrincipal Jwt jwt,
         @RequestBody List<WorkoutLogRequestDTO> logs) {
       String clerkId;

       if (jwt != null) {
           // Caso Producción: Tenemos token real de Clerk
           clerkId = jwt.getSubject();
       } else {
           // Caso Desarrollo/Prueba: No hay token, usamos el ID del SQL seed
           // IMPORTANTE: Este ID debe coincidir con el que pusiste en la tabla 'user_progress'
           clerkId = "user_fantasma_clerk"; // <--- Pon aquí el UUID o texto que usaste en el INSERT de SQL
           System.out.println("⚠️ ADVERTENCIA: Usando ID de prueba hardcodeado: " + clerkId);
       }

       workoutService.logWorkout(clerkId, logs);

      return ResponseEntity.ok().build();
   }
}
