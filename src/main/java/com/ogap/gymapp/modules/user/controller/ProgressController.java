package com.ogap.gymapp.modules.user.controller;

import com.ogap.gymapp.helpers.SecurityPass;
import com.ogap.gymapp.modules.training.service.ProgressionService;
import com.ogap.gymapp.modules.user.User;
import com.ogap.gymapp.modules.user.dto.ProgressStatusDTO;
import com.ogap.gymapp.modules.user.repository.UserRepository;
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
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
@Tag(name = "Progression", description = "User progression status")
public class ProgressController {

   private final ProgressionService progressionService;
   private final UserRepository userRepository;
   private final SecurityPass securityPass;

   @GetMapping("/status")
   @Operation(summary = "Get Progress Status", description = "Returns current course, phase, and completion percentage")
   public ResponseEntity<ProgressStatusDTO> getProgressStatus(@AuthenticationPrincipal Jwt jwt) {

      User user = userRepository.findByClerkId(jwt.getSubject())
            .orElseThrow(() -> new RuntimeException("User not found"));

      return ResponseEntity.ok(progressionService.getProgressStatus(user));
   }
}
