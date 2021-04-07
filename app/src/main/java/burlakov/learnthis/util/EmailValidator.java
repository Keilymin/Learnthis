package burlakov.learnthis.util;

import androidx.annotation.NonNull;

import com.rengwuxian.materialedittext.validation.METValidator;

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
}
