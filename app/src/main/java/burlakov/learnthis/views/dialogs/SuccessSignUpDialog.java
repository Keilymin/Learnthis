package burlakov.learnthis.views.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import burlakov.learnthis.R;
import burlakov.learnthis.views.LogInActivity;


/**
 * Диалог о успешной регистрации
 */
public class SuccessSignUpDialog extends DialogFragment {

    /**
     * Создает диалог с сообщением
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final ViewGroup nullParent = null;
        builder.setView(inflater.inflate(R.layout.dialog_success_sign_up, nullParent))
                .setPositiveButton(R.string.ok, (dialog, id) -> goToLogIn());
        return builder.create();
    }

    /**
     * Переход ко входу
     */
    void goToLogIn() {
        Intent intent = new Intent(getContext(), LogInActivity.class);
        getContext().startActivity(intent);
    }
}
