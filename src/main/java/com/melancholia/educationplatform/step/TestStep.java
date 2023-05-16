package com.melancholia.educationplatform.step;


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

    @Column(name = "answer_id", nullable = false)
    private String question;

    @OneToMany(mappedBy = "testStep", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Answer> answers;

    @OneToMany(mappedBy = "step", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Solution> solutions;

    public TestStep(String question) {
        this.question = question;
    }

}
