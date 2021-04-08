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
         * Метод должен выводить ошибку на экран
         *
         * @param message Сообщение ошибки
         */
        void showError(String message);
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
