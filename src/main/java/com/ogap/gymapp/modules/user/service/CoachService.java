package com.ogap.gymapp.modules.user.service;

import com.ogap.gymapp.modules.user.CoachStudentRelationship;
import com.ogap.gymapp.modules.user.RelationshipStatus;
import com.ogap.gymapp.modules.user.User;
import com.ogap.gymapp.modules.user.repository.CoachStudentRelationshipRepository;
import com.ogap.gymapp.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoachService {

   private final UserRepository userRepository;
   private final CoachStudentRelationshipRepository relationshipRepository;

   @Transactional
   public String generateInviteCode(String clerkId) {
      User coach = userRepository.findByClerkId(clerkId)
            .orElseThrow(() -> new RuntimeException("Coach not found"));

      // Simple random 6-char code. Conflict collision handling omitted for MVP.
      String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
      coach.setInviteCode(code);
      userRepository.save(coach);

      return code;
   }

   @Transactional
   public void linkStudentToCoach(String inviteCode, String studentClerkId) {
      if (inviteCode == null || inviteCode.isBlank())
         throw new RuntimeException("Invalid invite code");

      User student = userRepository.findByClerkId(studentClerkId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

      // Find coach by invite code. Ideally UserRepository should have
      // findByInviteCode.
      // For MVP, if not indexed, we might scan or need a repo method.
      // Let's add findByInviteCode to UserRepository first.
      User coach = userRepository.findByInviteCode(inviteCode)
            .orElseThrow(() -> new RuntimeException("Invalid invite code or coach not found"));

      if (coach.getId().equals(student.getId())) {
         throw new RuntimeException("Cannot link to yourself");
      }

      // Check if already linked
      relationshipRepository.findByCoachAndStudent(coach, student)
            .ifPresent(rel -> {
               throw new RuntimeException("Already linked to this coach");
            });

      CoachStudentRelationship relationship = CoachStudentRelationship.builder()
            .coach(coach)
            .student(student)
            .status(RelationshipStatus.ACTIVE)
            .build();

      relationshipRepository.save(relationship);
   }

   @Transactional(readOnly = true)
   public List<User> getMyStudents(String coachClerkId) {
      User coach = userRepository.findByClerkId(coachClerkId)
            .orElseThrow(() -> new RuntimeException("Coach not found"));

      return relationshipRepository.findByCoachAndStatus(coach, RelationshipStatus.ACTIVE).stream()
            .map(rel -> {
                User student = rel.getStudent();

                return User.builder()
                        .id(student.getId())
                        .fullName(student.getFullName())
                        .hasActiveSubscription(student.isHasActiveSubscription())
                        .build();
            })
            .toList();
   }
}
