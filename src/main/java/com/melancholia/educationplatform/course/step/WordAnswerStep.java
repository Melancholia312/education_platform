package com.melancholia.educationplatform.course.step;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "step", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Solution> solutions;

    @Column(name = "image")
    private String image;

}