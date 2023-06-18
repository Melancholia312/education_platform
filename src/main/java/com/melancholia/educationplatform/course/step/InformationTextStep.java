package com.melancholia.educationplatform.course.step;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "InformationTextStep")
@Table(name = "information_text_step")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InformationTextStep extends Step {

    @NotEmpty(message = "Текст лекции не может быть пустым")
    @Size(min = 100, max = 5000,  message = "Текст лекции должен быть длинной от 100 до 5000 символов")
    @Column(name = "lectionText", nullable = false, length = 5000)
    private String lectionText;

    @NotEmpty(message = "Заголовок не может быть пустым")
    @Size(min = 3, max = 50,  message = "Заголовок должен быть длинной от 3 до 50 символов")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "video")
    private String video;

}