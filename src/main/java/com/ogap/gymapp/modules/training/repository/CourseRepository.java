package com.ogap.gymapp.modules.training.repository;

import com.ogap.gymapp.modules.training.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    @Query("SELECT DISTINCT c FROM Course c JOIN c.phases p JOIN p.routines r JOIN r.exercises e")
    List<Course> findAllWithAllDetails();
}
