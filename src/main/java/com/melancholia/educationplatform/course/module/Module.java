package com.melancholia.educationplatform.course.module;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.course.step.Step;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Module")
@Table(name = "module")
@Data
@NoArgsConstructor
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "module_id",
            updatable = false
    )
    private long id;

    @Column(name = "module_number")
    private long moduleNumber;

    @Column(name = "module_name")
    private String name;

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Step> steps;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
