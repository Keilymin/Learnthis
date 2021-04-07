package burlakov.learnthis.presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.LogIn;
import burlakov.learnthis.views.MenuActivity;

public class LogInPresenter implements LogIn.Presenter {
    LogIn.View view;
    FirebaseAuth auth;
    Context context;

    public LogInPresenter(LogIn.View view, Context context) {
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
        this.context = context;
    }

    @Override
    public void logIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user.isEmailVerified()) {
                            Log.d("S", "signInWithEmail:success");
                            Intent intent = new Intent(context, MenuActivity.class);
                            context.startActivity(intent);
                        } else {
                            view.showError(context.getResources().getString(R.string.error_email_verified));
                        }
                    } else {
                        view.showError(task.getException().getMessage());
                    }
                });
    }
}
