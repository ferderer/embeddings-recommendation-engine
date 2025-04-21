package de.ferderer.ere.api.lesson;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    @Query("from Lesson order by cefr, lang, title")
    public List<Lesson> find();

    @Query(value = """
        WITH input_lessons AS (SELECT :id1 AS lesson_id, 1.0 AS weight
            UNION ALL SELECT :id2 AS lesson_id, 0.67 AS weight WHERE :id2 IS NOT NULL
            UNION ALL SELECT :id3 AS lesson_id, 0.33 AS weight WHERE :id3 IS NOT NULL
        ),
        ranked_similarities AS (
            SELECT
                l.id AS similar_lesson_id,
                l.title AS similar_lesson_title,
                l.lang AS similar_lesson_language,
                l.cefr AS similar_lesson_cefr,
                SUM(
                    i.weight * (
                        0.5 * COALESCE(s.similarity, 0) +
                        0.3 * CASE WHEN l.lang = il.lang THEN 1.0 ELSE 0.0 END +
                        0.2 * (1 - ABS(
                            (CASE l.cefr
                                WHEN 'A1' THEN 1 WHEN 'A2' THEN 2 WHEN 'B1' THEN 3
                                WHEN 'B2' THEN 4 WHEN 'C1' THEN 5 WHEN 'C2' THEN 6
                            END) -
                            (CASE il.cefr
                                WHEN 'A1' THEN 1 WHEN 'A2' THEN 2 WHEN 'B1' THEN 3
                                WHEN 'B2' THEN 4 WHEN 'C1' THEN 5 WHEN 'C2' THEN 6
                            END)
                        ) / 5)
                    )
                ) AS weighted_similarity_score
            FROM lesson l
            CROSS JOIN input_lessons i
            JOIN lesson il ON il.id = i.lesson_id
            LEFT JOIN lesson_similarities s ON (s.lesson_id1 = i.lesson_id AND s.lesson_id2 = l.id)
                OR (s.lesson_id2 = i.lesson_id AND s.lesson_id1 = l.id)
            WHERE l.id NOT IN (SELECT lesson_id FROM input_lessons)
            GROUP BY l.id, l.title, l.lang, l.cefr
            ORDER BY weighted_similarity_score DESC
            LIMIT 5
        )
        SELECT
            similar_lesson_id id,
            similar_lesson_title title,
            similar_lesson_language lang,
            similar_lesson_cefr cefr
        FROM ranked_similarities
        """, nativeQuery = true)
    List<LessonDto> findRecommendations(
        @Param("id1") Long id1,
        @Param("id2") Long id2,
        @Param("id3") Long id3,
        @Param("ids") List<Long> ids
    );
}
