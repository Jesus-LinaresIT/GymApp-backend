package com.ogap.gymapp.modules.user.controller;

import com.ogap.gymapp.helpers.SecurityPass;
import com.ogap.gymapp.modules.user.User;
import com.ogap.gymapp.modules.user.dto.ProgressStatusDTO;
import com.ogap.gymapp.modules.user.service.CoachService;
import com.ogap.gymapp.modules.training.service.ProgressionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Coach-Student", description = "Coach management and student linking")
public class CoachController {

   private final CoachService coachService;
   private final ProgressionService progressionService;
   private final SecurityPass  securityPass;

   @PostMapping("/coach/invite")
   @PreAuthorize("hasRole('COACH')")
   @Operation(summary = "Generate Invite Code", description = "Generates a temporary invite code for students")
   public ResponseEntity<Map<String, String>> generateInvite(@AuthenticationPrincipal Jwt jwt) {

       String code = coachService.generateInviteCode(jwt.getSubject());

       return ResponseEntity.ok(Map.of("code", code));
   }

   @PostMapping("/student/link")
    @PreAuthorize("hasRole('USER')") // Optional: strictly enforce or allow
   // anyone logged in
   @Operation(summary = "Link to Coach", description = "Link current user to a coach via invite code")
   public ResponseEntity<Void> linkStudent(@AuthenticationPrincipal Jwt jwt, @RequestBody Map<String, String> body) {

       String code = body.get("code");
       coachService.linkStudentToCoach(code, jwt.getSubject());

       return ResponseEntity.ok().build();
   }

   @GetMapping("/coach/students")
   @PreAuthorize("hasRole('COACH')")
   @Operation(summary = "List Students", description = "List all active students for the coach with their progress")
   public ResponseEntity<List<StudentProgressResponse>> getStudents(@AuthenticationPrincipal Jwt jwt) {

       List<User> students = coachService.getMyStudents(jwt.getSubject());

       List<StudentProgressResponse> response = students.stream().map(student -> {
           ProgressStatusDTO progress = testSafeGetProgress(student);
           return new StudentProgressResponse(student.getId(), student.getEmail(), progress);
       }).toList();

       return ResponseEntity.ok(response);
   }

   // Helper record/class for response
   public record StudentProgressResponse(java.util.UUID id, String email, ProgressStatusDTO progress) {
   }

   private ProgressStatusDTO testSafeGetProgress(User student) {
      try {
         return progressionService.getProgressStatus(student);
      } catch (Exception e) {
         return null; // Handle students with no progress/course assigned logic
      }
   }
}
