package burlakov.learnthis.contracts;

import burlakov.learnthis.models.User;

public interface LogIn {

    interface View {
        /**
         * Метод должен выводить ошибку на экран
         *
         * @param message Сообщение ошибки
         */
        void showError(String message);
    }

    interface Presenter {
        void logIn(String email, String password);
    }
}
