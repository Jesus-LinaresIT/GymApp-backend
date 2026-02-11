package com.ogap.gymapp.modules.training;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@Entity
@Table(name = "exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(nullable = false)
   private String name;

   private String muscleGroup;

   @Column(nullable = false, name = "sets_target")
   private Integer sets;

   private String repsTarget; // String to allow ranges like "8-12" or specific notes

   private Double weightSuggested;

   private Integer rirTarget; // Reps In Reserve

   private Integer restSeconds;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "routine_id", nullable = false)
   @JsonIgnore
   private Routine routine;
}
