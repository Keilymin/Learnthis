package burlakov.learnthis.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import burlakov.learnthis.R;
import burlakov.learnthis.views.dialogs.ForgotPasswordDialog;
import burlakov.learnthis.views.dialogs.MessageDialog;

/**
 * Класс View главного меню для выбора входа или регистрации
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button signUpButton;
    Button logInButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        signUpButton = findViewById(R.id.buttonSignUp);
        logInButton = findViewById(R.id.buttonLogIn);
        signUpButton.setOnClickListener(this);
        logInButton.setOnClickListener(this);
        if (currentUser != null) {
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
        }
    }

    /**
     * Определяет действия кликов на кнопки для переходов.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonSignUp) {
            Intent intent = new Intent(this, SignUpChoiceActivity.class);
            startActivity(intent);
        } else if (id == R.id.buttonLogIn) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }
}
