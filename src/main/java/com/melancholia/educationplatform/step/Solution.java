package com.melancholia.educationplatform.step;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity(name = "Solution")
@Table(name = "solution")
@NoArgsConstructor
@Data
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "solution_id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "step_id")
    private Step step;

    @Column(name = "solution_date")
    private Date solutionDate;

    @Column(name = "is_solved_correct")
    private boolean isSolvedCorrect;

    public Solution(Step step, Date solutionDate, boolean isSolvedCorrect) {
        this.step = step;
        this.solutionDate = solutionDate;
        this.isSolvedCorrect = isSolvedCorrect;
    }

}
