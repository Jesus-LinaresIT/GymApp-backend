package com.ogap.gymapp.modules.training.controller;

import com.ogap.gymapp.helpers.SecurityPass;
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
@RequestMapping("/api/v1/training") // Requirement says /api/v1/training/routine/today
@RequiredArgsConstructor
@Tag(name = "Training Routine", description = "Operations related to daily training routines")
public class TrainingController {

   private final CourseService courseService;
   private final SecurityPass securityHelper;

   @GetMapping("/routine/today")
   @Operation(summary = "Get Today's Routine", description = "Returns the routine for the current day based on user progress")
   public ResponseEntity<RoutineDayResponseDTO> getRoutineForToday(@AuthenticationPrincipal Jwt jwt) {

      return ResponseEntity.ok(courseService.getRoutineForToday(jwt.getSubject()));
   }
}
