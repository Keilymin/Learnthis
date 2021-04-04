package burlakov.learnthis.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import burlakov.learnthis.R;

/**
 * Класс View для выбора типа пользователя при регистрации
 */
public class SingUpChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    Button imTeacher;
    Button imLearner;
    Button imParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_choice);

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
        Intent intent = new Intent(this, SingUpChoiceActivity.class);
        int id = v.getId();
        if (id == R.id.buttonTeacher) {
            intent.putExtra("choice", 1);
        } else if (id == R.id.buttonLearner) {
            intent.putExtra("choice", 2);
        } else if (id == R.id.buttonParent) {
            intent.putExtra("choice", 3);
        }
        startActivity(intent);
    }
}