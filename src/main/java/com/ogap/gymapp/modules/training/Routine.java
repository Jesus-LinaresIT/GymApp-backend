package com.ogap.gymapp.modules.training;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "routines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Routine {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(nullable = false)
   private String dayLabel; // e.g., 'A', 'B'

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "phase_id", nullable = false)
   @JsonIgnore
   private Phase phase;

   @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
   @Builder.Default
   private List<Exercise> exercises = new ArrayList<>();
}
