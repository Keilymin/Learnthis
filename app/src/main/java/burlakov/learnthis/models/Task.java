package burlakov.learnthis.models;

import java.util.Date;
import java.util.List;

/**
 * Класс задача
 */
public class Task extends CourseElement {
    /**
     * Последний срок сдачи
     */
    Date deadline;
    /**
     * Максимальный балл
     */
    int maxMark;

    public Task(String theme, String courseId, Date deadline, int maxMark) {
        super(theme, courseId);
        if (deadline.after(new Date())) {
            this.deadline = deadline;
        } else throw new RuntimeException("Дата сдачи не может быть установленной в прошлом");
        if (maxMark > 0) {
            this.maxMark = maxMark;
        } else throw new RuntimeException("Оценка не может быть меньше единицы");
    }

    public Task(String theme, List<Material> materials, String courseId, Date deadline, int maxMark) {
        super(theme, materials, courseId);
        if (deadline.after(new Date())) {
            this.deadline = deadline;
        } else throw new RuntimeException("Дата сдачи не может быть установленной в прошлом");
        if (maxMark > 0) {
            this.maxMark = maxMark;
        } else throw new RuntimeException("Оценка не может быть меньше единицы");
    }

    public Task() {
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        if (deadline.after(new Date())) {
            this.deadline = deadline;
        } else throw new RuntimeException("Дата сдачи не может быть установленной в прошлом");
    }

    public int getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(int maxMark) {
        if (maxMark > 0) {
            this.maxMark = maxMark;
        } else throw new RuntimeException("Оценка не может быть меньше единицы");
    }
}
