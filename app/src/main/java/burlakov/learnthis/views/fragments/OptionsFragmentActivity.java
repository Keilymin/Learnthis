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
import burlakov.learnthis.views.HomeActivity;
import burlakov.learnthis.views.dialogs.EditEmailDialog;
import burlakov.learnthis.views.dialogs.MessageDialog;

public class OptionsFragmentActivity extends Fragment implements View.OnClickListener {
    Button singOut;
    Button editEmail;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_options, container, false);
        singOut = root.findViewById(R.id.singOut);
        editEmail = root.findViewById(R.id.editEmail);
        editEmail.setOnClickListener(this);
        singOut.setOnClickListener(t -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            Intent intent = new Intent(root.getContext(), HomeActivity.class);
            startActivity(intent);
        });
        return root;
    }

    public void showMessage(String message, Boolean isPositive) {
        MessageDialog dialog = new MessageDialog(message, root.getContext(), isPositive);
        dialog.show(getFragmentManager(), "dia");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.editEmail){
            EditEmailDialog dialog = new EditEmailDialog(this);
            dialog.show(getFragmentManager(), "dia");
        }
    }
}