package burlakov.learnthis.presenters.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.LogIn;
import burlakov.learnthis.util.EmailValidator;

/**
 * Диалог для востановления пароля
 */
public class ForgotPasswordDialog extends DialogFragment {
    MaterialEditText email;
    FirebaseAuth auth;
    LogIn.View view;

    public ForgotPasswordDialog(LogIn.View view) {
        this.view = view;
    }


    /**
     * Создает диалог с сообщением
     */

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final ViewGroup nullParent = null;
        builder.setView(inflater.inflate(R.layout.dialog_forgot_password, nullParent))
                .setPositiveButton(R.string.ok, (dialog, id) -> sendEmail())
                .setNegativeButton(R.string.back, (dialog, id) -> ForgotPasswordDialog.this.getDialog().cancel());
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        auth = FirebaseAuth.getInstance();
        email = getDialog().findViewById(R.id.email);
        EmailValidator.setValidateMaterialEditView(email, getContext());
    }

    /**
     * Отправка сообщения на эмайл
     */
    private void sendEmail() {
        if (org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email.getText().toString())) {
            auth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    view.showMessage(getResources().getString(R.string.forgot_password_success_message), true);
                } else {
                    view.showMessage(task.getException().getMessage(), false);
                }
            });
        } else {
            view.showMessage(getResources().getString(R.string.error_email), false);
        }
    }
}
