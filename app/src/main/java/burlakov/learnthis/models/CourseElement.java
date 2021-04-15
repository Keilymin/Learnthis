package burlakov.learnthis.models;

import java.util.List;

/**
 * Базовый элемент курса
 */
public abstract class CourseElement {
    /**
     * Название темы
     */
    private String theme;
    /**
     * Материалы
     */
    private List<Material> materials;
    /**
     * Айди элемента
     */
    private String id;
    /**
     * Айди курса
     */
    private String courseId;

    public CourseElement(String theme, String courseId) {
        if (!theme.equals("")) {
            this.theme = theme;
        } else throw new RuntimeException("Тема не может состоять из пустого поля");
        this.courseId = courseId;
    }

    public CourseElement(String theme, List<Material> materials, String courseId) {
        this.theme = theme;
        this.materials = materials;
        this.courseId = courseId;
    }

    public CourseElement() {
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        if (!theme.equals("")) {
            this.theme = theme;
        } else throw new RuntimeException("Тема не может состоять из пустого поля");
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials.clear();
        this.materials.addAll(materials);
    }
}
