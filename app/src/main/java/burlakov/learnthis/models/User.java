package burlakov.learnthis.models;

import org.apache.commons.validator.routines.EmailValidator;
import org.jetbrains.annotations.NotNull;

/**
 * Класс модель пользователя приложения
 */
public class User {
    /**
     * Имя
     */
    private String firstName;
    /**
     * Фамилия
     */
    private String secondName;
    /**
     * Эмайл
     */
    private String email;
    /**
     * Айди, присвоеный БД
     */
    private String id;
    /**
     * Путь к изображению аватара
     */
    private String avatarImage;
    /**
     * Роль пользователя
     */
    private Role role;

    public User(@NotNull String firstName, @NotNull String secondName, @NotNull String email, @NotNull Role role) {
        if (!firstName.equals("")) {
            this.firstName = firstName;
        } else throw new RuntimeException("Имя не может состоять из пустого поля");
        if (!secondName.equals("")) {
            this.secondName = secondName;
        } else throw new RuntimeException("Фамилия не может состоять из пустого поля");
        if (EmailValidator.getInstance().isValid(email)) {
            this.email = email;
        } else throw new RuntimeException("Не валидный эмайл");
        this.role = role;
    }

    public User(String firstName, String secondName, String email, String id, String avatarImage) {
        if (!firstName.equals("")) {
            this.firstName = firstName;
        } else throw new RuntimeException("Имя не может состоять из пустого поля");
        if (!secondName.equals("")) {
            this.secondName = secondName;
        } else throw new RuntimeException("Фамилия не может состоять из пустого поля");
        if (EmailValidator.getInstance().isValid(email)) {
            this.email = email;
        } else throw new RuntimeException("Не валидный эмайл");
        if (!id.equals("")) {
            this.id = id;
        } else throw new RuntimeException("Айди не может быть пустым полем");
        if (!avatarImage.equals("")) {
            this.avatarImage = avatarImage;
        } else throw new RuntimeException("Аватар не может быть пустым полем");
    }

    public User(@NotNull String firstName, @NotNull String secondName, @NotNull String email, @NotNull String id, @NotNull String avatarImage, @NotNull Role role) {
        if (!firstName.equals("")) {
            this.firstName = firstName;
        } else throw new RuntimeException("Имя не может состоять из пустого поля");
        if (!secondName.equals("")) {
            this.secondName = secondName;
        } else throw new RuntimeException("Фамилия не может состоять из пустого поля");
        if (EmailValidator.getInstance().isValid(email)) {
            this.email = email;
        } else throw new RuntimeException("Не валидный эмайл");
        if (!id.equals("")) {
            this.id = id;
        } else throw new RuntimeException("Айди не может быть пустым полем");
        if (!avatarImage.equals("")) {
            this.avatarImage = avatarImage;
        } else throw new RuntimeException("Аватар не может быть пустым полем");
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull String firstName) {
        if (!firstName.equals("")) {
            this.firstName = firstName;
        } else throw new RuntimeException("Имя не может состоять из пустого поля");
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(@NotNull String secondName) {
        if (!secondName.equals("")) {
            this.secondName = secondName;
        } else throw new RuntimeException("Фамилия не может состоять из пустого поля");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        if (EmailValidator.getInstance().isValid(email)) {
            this.email = email;
        } else throw new RuntimeException("Не валидный эмайл");
    }

    public String getId() {
        return id;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(@NotNull String avatarImage) {
        if (!avatarImage.equals("")) {
            this.avatarImage = avatarImage;
        } else throw new RuntimeException("Аватар не может быть пустым полем");
    }

    public Role getRole() {
        return role;
    }
}
