package com.ogap.gymapp.modules.training.controller;

import com.ogap.gymapp.modules.training.dto.CourseResponseDTO;
import com.ogap.gymapp.modules.training.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/courses")
@RequiredArgsConstructor
@Tag(name = "Training", description = "Training management API")
public class CourseController {

   private final CourseService courseService;

   @GetMapping
   @Operation(summary = "Get all courses", description = "Retrieves a list of all available training courses")
   public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
      return ResponseEntity.ok(courseService.getAllCourses());
   }
}
