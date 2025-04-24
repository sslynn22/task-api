package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @Setter
    @JoinColumn(name = "project_id")
    @ManyToOne(optional = false)
    private Project project;

    @Setter
    @NotNull
    @Length(max = 50)
    private String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
