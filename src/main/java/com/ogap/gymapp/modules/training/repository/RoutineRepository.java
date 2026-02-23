package com.ogap.gymapp.modules.training.repository;

import com.ogap.gymapp.modules.training.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, UUID> {
}
