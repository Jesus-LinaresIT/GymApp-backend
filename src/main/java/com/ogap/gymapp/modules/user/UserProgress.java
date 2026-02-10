package com.ogap.gymapp.modules.user;

import com.ogap.gymapp.modules.training.Course;
import com.ogap.gymapp.modules.training.Phase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@Entity
@Table(name = "user_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProgress {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "current_course_id")
   private Course currentCourse;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "current_phase_id")
   private Phase currentPhase;

   @Builder.Default
   private Integer nextRoutineIndex = 0; // Defines which routine in the phase list to serve next (0, 1, 2...)
}
