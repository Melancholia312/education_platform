package com.melancholia.educationplatform.course.step;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Answer")
@Table(name = "answer")
@Data
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "answer_id",
            updatable = false
    )
    private Long id;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @Column(name = "answer_text", nullable = false)
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "step_id")
    private Step testStep;

}