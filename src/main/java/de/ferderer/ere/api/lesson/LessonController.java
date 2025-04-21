package de.ferderer.ere.api.lesson;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
@RequiredArgsConstructor
public class LessonController {
    private final LessonRepository repo;

    @GetMapping("/api/lessons")
    public List<Lesson> question() {
        return repo.find();
    }

    @GetMapping("/api/recommend")
    public List<LessonDto> recommend(@RequestParam("ids") List<Long> ids) {
        if (isEmpty(ids)) {
            throw new IllegalArgumentException("At least one lesson ID is required");
        }

        Long id1 = ids.get(0);
        Long id2 = ids.size() > 1 ? ids.get(1) : null;
        Long id3 = ids.size() > 2 ? ids.get(2) : null;

        return repo.findRecommendations(id1, id2, id3, ids);
    }
}
