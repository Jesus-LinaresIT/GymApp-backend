package com.ogap.gymapp.modules.training.service;

import com.ogap.gymapp.modules.training.Course;
import com.ogap.gymapp.modules.training.dto.CourseResponseDTO;
import com.ogap.gymapp.modules.training.mapper.TrainingMapper;
import com.ogap.gymapp.modules.training.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

   private final CourseRepository courseRepository;
   private final TrainingMapper trainingMapper;

   @Transactional(readOnly = true)
   public List<CourseResponseDTO> getAllCourses() {
      return courseRepository.findAll().stream()
            .map(trainingMapper::toCourseDTO)
            .toList();
   }

   // Add more methods as needed (create, getById, etc.)
}
