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
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(nullable = false)
   private String title;

   @Column(columnDefinition = "TEXT")
   private String description;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private AccessType accessType;

   @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
   @Builder.Default
   private List<Phase> phases = new ArrayList<>();
}
