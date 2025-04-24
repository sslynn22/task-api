package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Setter
    @NotNull
    @Length(max = 50)
    private String writerId;

    @Setter
    @NotNull
    private LocalDateTime createdAt;

    @Setter
    @NotNull
    @Column(columnDefinition = "text")
    private String content;

    @JoinColumn(name = "task_id")
    @ManyToOne(optional = false)
    private Task task;
}
