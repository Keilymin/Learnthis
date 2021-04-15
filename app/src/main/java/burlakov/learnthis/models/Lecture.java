package burlakov.learnthis.models;

import java.util.List;

/**
 * Класс лекция
 */
public class Lecture extends CourseElement {
    public Lecture(String theme, String courseId) {
        super(theme, courseId);
    }

    public Lecture(String theme, List<Material> materials, String courseId) {
        super(theme, materials, courseId);
    }

    public Lecture() {
    }
}
