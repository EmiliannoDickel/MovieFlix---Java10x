package com.dickel.movieflix.entity;

import jakarta.persistence.*;
import lombok.*;
//adicionado builder para facilitar a criação de objetos
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;


}
