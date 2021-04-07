package burlakov.learnthis.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import burlakov.learnthis.R;

/**
 * Класс View главного меню для выбора входа или регистрации
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button signUpButton;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        signUpButton = findViewById(R.id.buttonSignUp);
        logInButton = findViewById(R.id.buttonLogIn);
        signUpButton.setOnClickListener(this);
        logInButton.setOnClickListener(this);
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
