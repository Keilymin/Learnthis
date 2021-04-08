package burlakov.learnthis.models;

/**
 * Перечисление допустимых ролей пользователей
 */
public enum Role {
    TEACHER("Teacher"),
    LEARNER("Learner"),
    PARENT("Parent");

    private String roleName;


    private Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}

