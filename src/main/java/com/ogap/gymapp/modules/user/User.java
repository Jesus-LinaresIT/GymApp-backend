package com.ogap.gymapp.modules.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@Entity
@Table(name = "app_users") // 'users' is often a reserved keyword in DBs
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(nullable = false, unique = true)
   private String clerkId;

   @Column(nullable = false, unique = true)
   private String email;

   @Builder.Default
   private boolean hasActiveSubscription = false;
}
