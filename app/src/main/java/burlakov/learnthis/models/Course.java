package burlakov.learnthis.models;

/**
 * Класс-модель курсов
 */
public class Course {
    /**
     * Название курса
     */
    private String name;
    /**
     * id Курса
     */
    private String id;
    /**
     * id Создателя
     */
    private String creatorId;

    public Course(String name, String creatorId) {
        if (!name.equals("")) {
            this.name = name;
        } else throw new RuntimeException("Имя не может состоять из пустого поля");
        this.creatorId = creatorId;
    }

    public Course() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.equals("")) {
            this.name = name;
        } else throw new RuntimeException("Имя не может состоять из пустого поля");
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
