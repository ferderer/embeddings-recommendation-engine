package de.ferderer.ere.api.lesson;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Lesson")
@Getter
@Setter
public class LessonEntity implements Lesson {
    @Id
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private Language lang;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private CEFR cefr;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LessonEntity other && Objects.equals(id, other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
