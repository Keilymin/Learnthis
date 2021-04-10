package burlakov.learnthis.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.LogIn;
import burlakov.learnthis.presenters.LogInPresenter;
import burlakov.learnthis.util.EmailValidator;
import burlakov.learnthis.views.dialogs.ForgotPasswordDialog;
import burlakov.learnthis.views.dialogs.SuccessSignUpDialog;

/**
 * Класс View для входа в систему
 */
public class LogInActivity extends AppCompatActivity implements LogIn.View, View.OnClickListener {
    MaterialEditText email;
    MaterialEditText password;
    LogInPresenter presenter;
    TextView message;
    TextView passwordForgot;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        message = findViewById(R.id.message);
        logInButton = findViewById(R.id.buttonLogIn);
        passwordForgot = findViewById(R.id.pass_forgot);

        presenter = new LogInPresenter(this, this);

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
        passwordForgot.setOnClickListener(this);
        logInButton.setOnClickListener(this);

        message.setTextColor(Color.RED);
    }

    /**
     * Показ ошибки
     *
     * @param message Сообщение ошибки
     */
    @Override
    public void showError(String message) {
        this.message.setText(message);
        this.message.setVisibility(View.VISIBLE);
    }

    /**
     * Вход в систему
     */
    void logIn() {
        if (org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email.getText().toString())) {
            if (password.getText().toString().length() < 24 && password.getText().toString().length() >= 6) {
                presenter.logIn(email.getText().toString(), password.getText().toString());
            } else {
                showError(getResources().getString(R.string.error_password));
            }
        } else {
            showError(getResources().getString(R.string.email_error_message));
        }
    }

    /**
     * Обработка клика на вход
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonLogIn) {
            logIn();
        } else if (id == R.id.pass_forgot) {
            ForgotPasswordDialog dialog = new ForgotPasswordDialog(this,this);
            dialog.show(getSupportFragmentManager(), "dia");
        }
    }
}