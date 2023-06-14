package com.melancholia.educationplatform.course.step;


import com.melancholia.educationplatform.course.module.Module;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "TestStep")
@Table(name = "test_step")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestStep extends Step {

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "multi_answers", nullable = false)
    private boolean multiAnswers;

    @OneToMany(mappedBy = "testStep", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answers;

    @OneToMany(mappedBy = "step", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Solution> solutions;

    @Column(name = "image")
    private String image;

}
