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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.TeacherCourses;
import burlakov.learnthis.models.Course;

/**
 * Диалог для изменения названия курса
 */
public class CourseNameDialog extends DialogFragment {
    TeacherCourses.View view;
    EditText courseName;
    Course course;

    public CourseNameDialog(TeacherCourses.View view, Course course) {
        this.view = view;
        this.course = course;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final ViewGroup nullParent = null;
        builder.setView(inflater.inflate(R.layout.dialog_create_course, nullParent))
                .setPositiveButton(R.string.edit, (dialog, id) -> editCourse())
                .setNegativeButton(R.string.back, (dialog, id) -> CourseNameDialog.this.getDialog().cancel());
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        courseName = getDialog().findViewById(R.id.courseName);
        courseName.setText(course.getName());
    }

    /**
     * Изменяет название курса в бд
     */
    private void editCourse() {
        String name = courseName.getText().toString();
        if (name.length() > 0) {
            if(!name.equals(course.getName())) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", name);
                FirebaseDatabase.getInstance().getReference("Courses").child(course.getId()).updateChildren(hashMap);
                view.showMessage(getResources().getString(R.string.success), true);
            }
        } else {
            view.showMessage(getResources().getString(R.string.error_course_name), false);
        }
    }
}
