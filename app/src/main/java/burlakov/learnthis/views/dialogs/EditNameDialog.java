package burlakov.learnthis.views.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import burlakov.learnthis.R;
import burlakov.learnthis.views.fragments.OptionsFragmentActivity;

/**
 * Диалог для изменения текущего имени и фамилии
 */
public class EditNameDialog extends DialogFragment implements View.OnClickListener {
    OptionsFragmentActivity view;
    EditText firstName;
    EditText secondName;
    Button edit;
    TextView error;
    FirebaseAuth auth;

    public EditNameDialog(OptionsFragmentActivity view) {
        this.view = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final ViewGroup nullParent = null;
        builder.setView(inflater.inflate(R.layout.dialog_edit_name, nullParent))
                .setNegativeButton(R.string.back, (dialog, id) -> EditNameDialog.this.getDialog().cancel());
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        firstName = getDialog().findViewById(R.id.firstName);
        secondName = getDialog().findViewById(R.id.secondName);
        edit = getDialog().findViewById(R.id.edit);
        error = getDialog().findViewById(R.id.error);

        edit.setOnClickListener(this);
    }

    /**
     * При клике на кнопку изменить, меняет имя и фамилию
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.edit) {
            if (firstName.getText().toString().length() > 0) {
                if (secondName.getText().toString().length() > 0) {
                    auth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = auth.getCurrentUser();
                    String[] strings = Objects.requireNonNull(currentUser.getDisplayName()).split(" ");
                    String role = strings[2];
                    String nameInfo = strings[0] + " " + strings[1];
                    if (!(secondName.getText().toString() + " " + firstName.getText().toString()).equals(nameInfo)) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(secondName.getText().toString() + " " + firstName.getText().toString() + " " + role)
                                .build();
                        currentUser.updateProfile(profileUpdate);

                        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                        View hView = navigationView.getHeaderView(0);
                        TextView nm = hView.findViewById(R.id.name);
                        nm.setText(secondName.getText().toString() + " " + firstName.getText().toString());

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(role + "s").child(auth.getUid());
                        HashMap<String, Object> hashMap = new HashMap<>();

                        hashMap.put("firstName", firstName.getText().toString());
                        hashMap.put("secondName", secondName.getText().toString());

                        myRef.updateChildren(hashMap).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                view.showMessage(getResources().getString(R.string.success), true);
                            } else {
                                view.showMessage(task.getException().getMessage(), false);
                            }
                        });
                    } else {
                        error.setText(getResources().getString(R.string.equals));
                    }
                } else {
                    error.setText(getResources().getString(R.string.error_secondName));
                }
            } else {
                error.setText(getResources().getString(R.string.error_firstName));
            }
        }
    }
}
