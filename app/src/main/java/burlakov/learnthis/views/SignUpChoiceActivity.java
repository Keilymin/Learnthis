package burlakov.learnthis.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import burlakov.learnthis.R;

/**
 * Класс View для выбора типа пользователя при регистрации
 */
public class SignUpChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    Button imTeacher;
    Button imLearner;
    Button imParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_choice);

        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        imTeacher = findViewById(R.id.buttonTeacher);
        imLearner = findViewById(R.id.buttonLearner);
        imParent = findViewById(R.id.buttonParent);

        imTeacher.setOnClickListener(this);
        imLearner.setOnClickListener(this);
        imParent.setOnClickListener(this);
    }

    /**
     * В зависимости от выбора кнопки передает следующему активити значение выбора.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        int id = v.getId();
        if (id == R.id.buttonTeacher) {
            intent.putExtra("role", 1);
        } else if (id == R.id.buttonLearner) {
            intent.putExtra("role", 2);
        } else if (id == R.id.buttonParent) {
            intent.putExtra("role", 3);
        }
        startActivity(intent);
    }
}