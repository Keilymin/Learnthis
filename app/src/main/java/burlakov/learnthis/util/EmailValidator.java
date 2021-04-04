package burlakov.learnthis.util;

import androidx.annotation.NonNull;

import com.rengwuxian.materialedittext.validation.METValidator;

public class EmailValidator extends METValidator {
    public EmailValidator(@NonNull String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        if(!isEmpty) {
            return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(text.toString());
        }
        else return false;
    }
}
