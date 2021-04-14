package burlakov.learnthis.presenters.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.TeacherCourses;

/**
 * Диалог для создания курса
 */
public class CourseCreateDialog extends DialogFragment implements TeacherCourses.CreateDialog {
    TeacherCourses.View view;
    EditText course;

    public CourseCreateDialog(TeacherCourses.View view) {
        this.view = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final ViewGroup nullParent = null;
        builder.setView(inflater.inflate(R.layout.dialog_create_course, nullParent))
                .setPositiveButton(R.string.create_course, (dialog, id) -> createCourse())
                .setNegativeButton(R.string.back, (dialog, id) -> CourseCreateDialog.this.getDialog().cancel());
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        course = getDialog().findViewById(R.id.courseName);
    }

    @Override
    public void createCourse() {
        String name = course.getText().toString();
        if (name.length() > 0) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", name);
            hashMap.put("creatorId", FirebaseAuth.getInstance().getUid());
            FirebaseDatabase.getInstance().getReference("Courses").push().setValue(hashMap);
            view.showMessage(getResources().getString(R.string.success_course_created), true);
        } else {
            view.showMessage(getResources().getString(R.string.error_course_name), false);
        }
    }
}
