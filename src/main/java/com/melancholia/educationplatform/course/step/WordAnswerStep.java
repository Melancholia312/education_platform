package com.melancholia.educationplatform.course.step;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "WordAnswerStep")
@Table(name = "word_answer_step")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WordAnswerStep extends Step {

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;

    @OneToMany(mappedBy = "step", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Solution> solutions;

    /*@Column(name = "lectionText", nullable = false)
    private String image;*/
}