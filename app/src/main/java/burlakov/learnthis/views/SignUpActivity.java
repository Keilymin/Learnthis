package burlakov.learnthis.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.SignUp;
import burlakov.learnthis.models.Role;
import burlakov.learnthis.models.User;
import burlakov.learnthis.presenters.SignUpPresenter;
import burlakov.learnthis.util.EmailValidator;
import burlakov.learnthis.views.dialogs.SuccessSignUpDialog;

/**
 * View для регистрации в системе
 */
public class SignUpActivity extends AppCompatActivity implements SignUp.View, View.OnClickListener {
    MaterialEditText firstName;
    MaterialEditText secondName;
    MaterialEditText email;
    MaterialEditText password;
    MaterialEditText replyPassword;
    SignUpPresenter presenter;
    TextView message;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        firstName = findViewById(R.id.firstName);
        secondName = findViewById(R.id.secondName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        replyPassword = findViewById(R.id.replyPassword);
        message = findViewById(R.id.message);
        signUpButton = findViewById(R.id.buttonSignUp);
        presenter = new SignUpPresenter(this, this);

        EmailValidator validator = new EmailValidator(getResources().getString(R.string.email_error_message));
        email.addValidator(validator);
        email.validate();
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                email.validate();
            }
        });

        signUpButton.setOnClickListener(this);

        message.setTextColor(Color.RED);
    }

    /**
     * Проверка параметров на правильный ввод
     */
    private void signUp() {
        if (firstName.getText().toString().length() > 0) {
            if (secondName.getText().toString().length() > 0) {
                if (org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email.getText().toString())) {
                    if (password.getText().toString().length() < 24 && password.getText().toString().length() >= 6) {
                        if (password.getText().toString().equals(replyPassword.getText().toString())) {
                            Role role = getRole();
                            User user = new User(firstName.getText().toString(), secondName.getText().toString(), email.getText().toString(), role);
                            presenter.signUp(user, password.getText().toString());
                        } else {
                            showError(getResources().getString(R.string.error_reply_password));
                        }
                    } else {
                        showError(getResources().getString(R.string.error_password));
                    }
                } else {
                    showError(getResources().getString(R.string.email_error_message));
                }
            } else {
                showError(getResources().getString(R.string.error_secondName));
            }
        } else {
            showError(getResources().getString(R.string.error_firstName));
        }
    }

    /**
     * Метод получает выбранную с прошлого активити роль
     *
     * @return Выбранная роль.
     */
    private Role getRole() {
        int a = getIntent().getIntExtra("role", 0);
        switch (a) {
            case 1:
                return Role.TEACHER;
            case 2:
                return Role.LEARNER;
            case 3:
                return Role.PARENT;
            default:
                Intent intent = new Intent(this, SignUpChoiceActivity.class);
                startActivity(intent);
                return null;
        }
    }

    /**
     * Выводит сообщение-ошибку на экран
     *
     * @param message Сообщение ошибки
     */
    @Override
    public void showError(String message) {
        this.message.setText(message);
        this.message.setVisibility(View.VISIBLE);
    }

    /**
     * Выводит диалог об успешной регистрации
     */
    @Override
    public void showSuccessMessage() {
        DialogFragment dialog = new SuccessSignUpDialog();
        dialog.show(getSupportFragmentManager(), "dia");
    }

    /**
     * Считывание нажатия на кнопку регистрации
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonSignUp) {
            signUp();
        }
    }
}