package burlakov.learnthis.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.LogIn;
import burlakov.learnthis.views.HomeActivity;
import burlakov.learnthis.presenters.dialogs.EditEmailDialog;
import burlakov.learnthis.presenters.dialogs.EditImageDialog;
import burlakov.learnthis.presenters.dialogs.EditNameDialog;
import burlakov.learnthis.presenters.dialogs.ForgotPasswordDialog;
import burlakov.learnthis.presenters.dialogs.MessageDialog;

/**
 * Общий фрагмент для настроек акаунта
 */
public class OptionsFragmentActivity extends Fragment implements View.OnClickListener, LogIn.View {
    Button singOut;
    Button editEmail;
    Button editPassword;
    Button editName;
    Button editImage;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_options, container, false);
        singOut = root.findViewById(R.id.singOut);

        editEmail = root.findViewById(R.id.editEmail);
        editEmail.setOnClickListener(this);
        editPassword = root.findViewById(R.id.editPassword);
        editPassword.setOnClickListener(this);
        editName = root.findViewById(R.id.editName);
        editName.setOnClickListener(this);
        editImage = root.findViewById(R.id.editImage);
        editImage.setOnClickListener(this);

        singOut.setOnClickListener(t -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            Intent intent = new Intent(root.getContext(), HomeActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void showMessage(String message, Boolean isPositive) {
        MessageDialog dialog = new MessageDialog(message, root.getContext(), isPositive);
        dialog.show(getFragmentManager(), "dia");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.editEmail) {
            EditEmailDialog dialog = new EditEmailDialog(this);
            dialog.show(getFragmentManager(), "dia");
        } else if (id == R.id.editPassword) {
            ForgotPasswordDialog dialog = new ForgotPasswordDialog(this);
            dialog.show(getFragmentManager(), "dia");
        } else if (id == R.id.editName) {
            EditNameDialog dialog = new EditNameDialog(this);
            dialog.show(getFragmentManager(), "dia");
        } else if (id == R.id.editImage) {
            EditImageDialog dialog = new EditImageDialog(this);
            dialog.show(getFragmentManager(), "dia");
        }
    }
}