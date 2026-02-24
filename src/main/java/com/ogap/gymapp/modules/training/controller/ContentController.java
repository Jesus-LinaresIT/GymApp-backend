package com.ogap.gymapp.modules.training.controller;

import com.ogap.gymapp.modules.training.dto.*;
import com.ogap.gymapp.modules.training.service.ContentManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cms")
@RequiredArgsConstructor
@Tag(name = "CMS", description = "Content Management System for Coaches and Admins")
public class ContentController {

   private final ContentManagementService contentManagementService;

   @PostMapping("/courses")
   @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
   @Operation(summary = "Create Course", description = "Creates a new training course")
   public ResponseEntity<CourseResponseDTO> createCourse(
         @AuthenticationPrincipal Jwt jwt,
         @RequestBody CreateCourseDTO dto) {
      return ResponseEntity.ok(contentManagementService.createCourse(dto, jwt));
   }

   @PostMapping("/phases")
   @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
   @Operation(summary = "Create Phase", description = "Creates a new phase within a course")
   public ResponseEntity<PhaseResponseDTO> createPhase(@RequestBody CreatePhaseDTO dto) {
      return ResponseEntity.ok(contentManagementService.createPhase(dto));
   }

   @PostMapping("/routines")
   @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
   @Operation(summary = "Create Routine", description = "Creates a new routine within a phase")
   public ResponseEntity<RoutineResponseDTO> createRoutine(@RequestBody CreateRoutineDTO dto) {
      return ResponseEntity.ok(contentManagementService.createRoutine(dto));
   }

   @PostMapping("/exercises")
   @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
   @Operation(summary = "Create Exercise", description = "Creates a new exercise within a routine")
   public ResponseEntity<ExerciseResponseDTO> createExercise(@RequestBody CreateExerciseDTO dto) {
      return ResponseEntity.ok(contentManagementService.createExercise(dto));
   }
}
