package com.ogap.gymapp.modules.user.repository;

import com.ogap.gymapp.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
   Optional<User> findByClerkId(String clerkId);

   Optional<User> findByInviteCode(String inviteCode);
}
