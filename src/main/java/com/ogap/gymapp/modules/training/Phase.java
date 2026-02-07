package com.ogap.gymapp.modules.training;

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
@Table(name = "phases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phase {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(nullable = false)
   private String name;

   @Column(nullable = false)
   private Integer orderIndex;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "course_id", nullable = false)
   private Course course;

   @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL, orphanRemoval = true)
   @Builder.Default
   private List<Routine> routines = new ArrayList<>();
}
