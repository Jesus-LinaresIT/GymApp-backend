package com.ogap.gymapp.modules.user.repository;

import com.ogap.gymapp.modules.user.CoachStudentRelationship;
import com.ogap.gymapp.modules.user.RelationshipStatus;
import com.ogap.gymapp.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CoachStudentRelationshipRepository extends JpaRepository<CoachStudentRelationship, UUID> {

    @Query("SELECT r FROM CoachStudentRelationship r JOIN FETCH r.student WHERE r.coach = :coach AND r.status = :status")
   List<CoachStudentRelationship> findByCoachAndStatus(@Param("coach") User coach, @Param("status") RelationshipStatus status);

   Optional<CoachStudentRelationship> findByCoachAndStudent(User coach, User student);
}
