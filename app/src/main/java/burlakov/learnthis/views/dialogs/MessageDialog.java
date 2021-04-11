package burlakov.learnthis.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import burlakov.learnthis.R;

/**
 * Базовый диалог для показа сообщений пользователю
 */
public class MessageDialog extends DialogFragment {
    String message;
    Context context;
    Boolean isPositive;

    ImageView imageView;
    TextView textView;

    public MessageDialog(String message, Context context, Boolean isPositive) {
        this.message = message;
        this.context = context;
        this.isPositive = isPositive;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final ViewGroup nullParent = null;
        builder.setView(inflater.inflate(R.layout.dialog_message, nullParent))
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    MessageDialog.this.getDialog().cancel();
                });
        return builder.create();
    }

    /**
     * В зависимости от параметра isPositive, показывает положительное или негативное сообщение
     */
    @Override
    public void onStart() {
        super.onStart();
        imageView = getDialog().findViewById(R.id.imageView);
        textView = getDialog().findViewById(R.id.message);

        if (isPositive) {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.success));
        }
        textView.setText(message);
    }
}
