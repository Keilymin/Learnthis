package burlakov.learnthis.presenters.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

import burlakov.learnthis.R;
import burlakov.learnthis.util.EmailValidator;
import burlakov.learnthis.views.fragments.OptionsFragmentActivity;

/**
 * Диалог для изменения эмайла
 */
public class EditEmailDialog extends DialogFragment {
    OptionsFragmentActivity view;
    MaterialEditText email;
    EditText password;
    MaterialEditText newEmail;
    TextView error;
    Button change;

    public EditEmailDialog(OptionsFragmentActivity view) {
        this.view = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final ViewGroup nullParent = null;
        builder.setView(inflater.inflate(R.layout.dialog_edit_email, nullParent))
                .setNegativeButton(R.string.back, (dialog, id) -> EditEmailDialog.this.getDialog().cancel());
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        email = getDialog().findViewById(R.id.email);
        newEmail = getDialog().findViewById(R.id.newEmail);
        password = getDialog().findViewById(R.id.password);
        error = getDialog().findViewById(R.id.error);
        EmailValidator.setValidateMaterialEditView(email, getContext());
        EmailValidator.setValidateMaterialEditView(newEmail, getContext());
        change = getDialog().findViewById(R.id.change);
        change.setOnClickListener(t -> changeEmail());
    }

    /**
     * Изменяет текущий эмайл
     */
    private void changeEmail() {
        if (org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email.getText().toString())) {
            if (org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(newEmail.getText().toString())) {
                if (password.getText().toString().length() < 24 && password.getText().toString().length() >= 6) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    final FirebaseUser user = auth.getCurrentUser();
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(email.getText().toString(), password.getText().toString());
                    user.reauthenticate(credential)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                    user1.verifyBeforeUpdateEmail(newEmail.getText().toString())
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    view.showMessage(getResources().getString(R.string.edited_email), true);
                                                } else {
                                                    error.setText(task1.getException().getMessage());
                                                }
                                            });
                                } else {
                                    error.setText(task.getException().getMessage());
                                }
                            });
                } else {
                    error.setText(getResources().getString(R.string.error_password));
                }
            } else {
                error.setText(getResources().getString(R.string.new_email_error_message));
            }
        } else {
            error.setText(getResources().getString(R.string.old_email_error_message));
        }
    }
}
