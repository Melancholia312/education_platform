package com.melancholia.educationplatform.course.step;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

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

    /*@Column(name = "lectionText", nullable = false)
    private String image;*/

    public TestStep(String question, boolean multiAnswers) {
        this.question = question;
        this.multiAnswers = multiAnswers;
    }

}
