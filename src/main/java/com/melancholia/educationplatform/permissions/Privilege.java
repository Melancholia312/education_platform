package com.melancholia.educationplatform.permissions;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "Privilege")
@Table(name = "зrivilege")
@Data
@NoArgsConstructor
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "privilege_id",
            updatable = false
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Privilege(String name){
        this.name = name;
    }
}
