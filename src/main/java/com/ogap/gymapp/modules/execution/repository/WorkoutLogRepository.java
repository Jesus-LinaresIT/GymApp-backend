package com.ogap.gymapp.modules.execution.repository;

import com.ogap.gymapp.modules.execution.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import com.ogap.gymapp.modules.training.Routine;
import com.ogap.gymapp.modules.user.User;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, UUID> {
   boolean existsByUserAndExercise_Routine(User user, Routine routine);
}
