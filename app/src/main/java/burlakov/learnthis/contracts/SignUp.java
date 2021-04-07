package burlakov.learnthis.contracts;

import burlakov.learnthis.models.User;

/**
 * Контракт для регистрации акаунтов
 */
public interface SignUp {

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

        /**
         * Вывод сообщение об успешной регистрации
         */
        void showSuccessMessage();
    }


    /**
     * Контракт для Presenter'a
     */
    interface Presenter {
        /**
         * Метод регистрирует юзера в системе
         *
         * @param user Юзер который регистрируется
         * @param password его пароль
         */
        void signUp(User user, String password);
    }
}
