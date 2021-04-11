package burlakov.learnthis.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;

import burlakov.learnthis.R;

/**
 * Класс валидатор эмайл адреса
 */
public class EmailValidator extends METValidator {
    public EmailValidator(@NonNull String errorMessage) {
        super(errorMessage);
    }

    /**
     * Проводит валидацию эмайла
     *
     * @param text Эмайл
     * @param isEmpty Пустой ли текст
     * @return true если прошел валидацию, false если не прошел
     */
    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        if (!isEmpty) {
            return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(text.toString());
        } else return false;
    }
    static public void setValidateMaterialEditView(MaterialEditText email, Context context){
        EmailValidator validator = new EmailValidator(context.getResources().getString(R.string.email_error_message));
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
    }
}
