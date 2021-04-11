package burlakov.learnthis.presenters;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import burlakov.learnthis.contracts.SignUp;
import burlakov.learnthis.models.User;

/**
 * Класс-презентер для регистрации
 */
public class SignUpPresenter implements SignUp.Presenter {

    SignUp.View view;
    FirebaseAuth auth;
    Context context;

    public SignUpPresenter(SignUp.View view, Context context) {
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
        this.context = context;
    }

    /**
     * Выполняет попытку регистрации в системе.
     * При успехе высылает сообщение для подтверждения на эмайл.
     * При ошибке выводит ошибку.
     *
     * @param user     Юзер который регистрируется
     * @param password его пароль
     */
    @Override
    public void signUp(User user, String password) {
        auth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef;
                        switch (user.getRole()) {
                            case TEACHER:
                                myRef = database.getReference("Users").child("Teachers").child(auth.getUid());
                                break;
                            case LEARNER:
                                myRef = database.getReference("Users").child("Learners").child(auth.getUid());
                                break;
                            case PARENT:
                                myRef = database.getReference("Users").child("Parents").child(auth.getUid());
                                break;
                            default:
                                myRef = null;
                                break;
                        }
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", auth.getUid());
                        hashMap.put("firstName", user.getFirstName());
                        hashMap.put("secondName", user.getSecondName());
                        hashMap.put("image", "def");
                        myRef.setValue(hashMap);
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(user.getSecondName() + " " + user.getFirstName() + " " + user.getRole().toString())
                                .build();
                        firebaseUser.updateProfile(profileUpdate);
                        firebaseUser.sendEmailVerification().addOnCompleteListener(task1 -> {
                            auth.signOut();
                            view.showSuccessMessage();
                        });
                    } else {
                        view.showError(task.getException().getMessage());
                    }
                });
    }
}