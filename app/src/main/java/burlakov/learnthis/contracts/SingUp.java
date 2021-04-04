package burlakov.learnthis.contracts;

public interface SingUp {

    interface View {
        void showError(String message);

        void showSuccessMessage(String message);
    }

    interface Presenter {
        void singUp(String email, String password);
    }
}
