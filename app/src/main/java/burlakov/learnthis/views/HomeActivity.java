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
    Button singUpButton;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        singUpButton = findViewById(R.id.buttonSingUp);
        logInButton = findViewById(R.id.buttonLogIn);
        singUpButton.setOnClickListener(this);
        logInButton.setOnClickListener(this);
    }

    /**
     * Определяет действия кликов на кнопки для переходов.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonSingUp) {
            Intent intent = new Intent(this, SingUpChoiceActivity.class);
            startActivity(intent);
        } else if (id == R.id.buttonLogIn) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }
}
