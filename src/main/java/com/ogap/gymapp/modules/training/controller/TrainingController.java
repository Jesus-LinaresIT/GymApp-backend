package com.ogap.gymapp.modules.training.controller;

import com.ogap.gymapp.modules.training.dto.RoutineDayResponseDTO;
import com.ogap.gymapp.modules.training.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/v1/training") // Requirement says /api/v1/training/routine/today
@RequiredArgsConstructor
@Tag(name = "Training Routine", description = "Operations related to daily training routines")
public class TrainingController {

   private final CourseService courseService;

   @GetMapping("/routine/today")
   @Operation(summary = "Get Today's Routine", description = "Returns the routine for the current day based on user progress")
   public ResponseEntity<RoutineDayResponseDTO> getRoutineForToday(@AuthenticationPrincipal Jwt jwt) {
       String userId;

       if (jwt != null) {
           // Caso Producción: Tenemos token real de Clerk
           userId = jwt.getSubject();
       } else {
           // Caso Desarrollo/Prueba: No hay token, usamos el ID del SQL seed
           // IMPORTANTE: Este ID debe coincidir con el que pusiste en la tabla 'user_progress'
           userId = "user_fantasma_clerk"; // <--- Pon aquí el UUID o texto que usaste en el INSERT de SQL
           System.out.println("⚠️ ADVERTENCIA: Usando ID de prueba hardcodeado: " + userId);
       }

      return ResponseEntity.ok(courseService.getRoutineForToday(userId));
   }
}
