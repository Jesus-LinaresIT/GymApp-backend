package com.ogap.gymapp.modules.execution;

import com.ogap.gymapp.modules.training.Exercise;
import com.ogap.gymapp.modules.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "workout_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutLog {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "exercise_id", nullable = false)
   private Exercise exercise;

   @JoinColumn(name = "sets_done")
   private Integer setsDone;

   private Integer reps;

   private Double weight;

   private Integer rir;

   @CreationTimestamp
   private LocalDateTime loggedAt;
}
