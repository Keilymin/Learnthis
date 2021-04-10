package burlakov.learnthis.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.LogIn;
import burlakov.learnthis.util.EmailValidator;

public class ForgotPasswordDialog extends DialogFragment {
    MaterialEditText email;
    FirebaseAuth auth;
    LogIn.View view;
    Context context;

    public ForgotPasswordDialog(LogIn.View view, Context context) {
        this.view = view;
        this.context = context;
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
        EmailValidator validator = new EmailValidator(getResources().getString(R.string.email_error_message));
        email.addValidator(validator);
        email.validate();
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                email.validate();
            }
        });
    }

    private void sendEmail() {
        if (org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email.getText().toString())) {
            auth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    view.showError(context.getResources().getString(R.string.forgot_password_success_message));
                } else {
                    view.showError(task.getException().getMessage());
                }
            });
        }else {
            view.showError(context.getResources().getString(R.string.error_email));
        }
    }
}
