package burlakov.learnthis.contracts;

import burlakov.learnthis.models.User;

/**
 * Контракт для входа
 */
public interface LogIn {
    /**
     * Контракт для View
     */
    interface View {
        /**
         * Метод должен выводить сообщение на экран
         *
         * @param message Сообщение ошибки
         */
        void showMessage(String message,Boolean isPositive);

    }

    /**
     * Контракт для Presenter'a
     */
    interface Presenter {
        /**
         * Вход в систему
         *
         * @param email    Эмайл
         * @param password Пароль
         */
        void logIn(String email, String password);
    }
}
