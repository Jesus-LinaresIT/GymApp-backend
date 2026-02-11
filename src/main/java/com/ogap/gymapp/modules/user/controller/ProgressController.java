package com.ogap.gymapp.modules.user.controller;

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
@RequestMapping("/api/public/v1/progress")
@RequiredArgsConstructor
@Tag(name = "Progression", description = "User progression status")
public class ProgressController {

   private final ProgressionService progressionService;
   private final UserRepository userRepository;

   @GetMapping("/status")
   @Operation(summary = "Get Progress Status", description = "Returns current course, phase, and completion percentage")
   public ResponseEntity<ProgressStatusDTO> getProgressStatus(@AuthenticationPrincipal Jwt jwt) {
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

      User user = userRepository.findByClerkId(clerkId)
            .orElseThrow(() -> new RuntimeException("User not found"));

      return ResponseEntity.ok(progressionService.getProgressStatus(user));
   }
}
