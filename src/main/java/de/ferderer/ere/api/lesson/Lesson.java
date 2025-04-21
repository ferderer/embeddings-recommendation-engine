package de.ferderer.ere.api.lesson;

public interface Lesson {
    Long getId();
    String getTitle();
    Language getLang();
    CEFR getCefr();
}
