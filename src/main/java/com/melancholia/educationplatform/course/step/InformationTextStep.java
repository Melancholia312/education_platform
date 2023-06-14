package com.melancholia.educationplatform.course.step;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "InformationTextStep")
@Table(name = "information_text_step")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InformationTextStep extends Step {

    @Column(name = "lectionText", nullable = false, length = 5000)
    private String lectionText;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "video")
    private String video;

}