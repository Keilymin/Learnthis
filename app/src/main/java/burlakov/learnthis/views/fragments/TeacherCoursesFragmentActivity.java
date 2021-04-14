package burlakov.learnthis.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.TeacherCourses;
import burlakov.learnthis.models.Course;
import burlakov.learnthis.models.adapters.CourseAdapter;
import burlakov.learnthis.presenters.TeacherCoursesPresenter;
import burlakov.learnthis.presenters.dialogs.CourseCreateDialog;
import burlakov.learnthis.presenters.dialogs.CourseNameDialog;
import burlakov.learnthis.presenters.dialogs.MessageDialog;

/**
 * Активити для вкладки курсов у учителя
 */
public class TeacherCoursesFragmentActivity extends Fragment implements TeacherCourses.View, View.OnClickListener {
    RecyclerView courses;
    Button createCourseButton;
    View root;
    CourseAdapter courseAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_courses_teacher, container, false);

        courses = root.findViewById(R.id.courses);
        courses.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setStackFromEnd(true);
        courses.setLayoutManager(linearLayoutManager);

        createCourseButton = root.findViewById(R.id.createCourseButton);
        createCourseButton.setOnClickListener(this);

        TeacherCoursesPresenter presenter = new TeacherCoursesPresenter(this);
        presenter.startDBCourseListen();
        return root;
    }

    @Override
    public void setIntoAdapter(ArrayList<Course> courses) {
        courseAdapter = new CourseAdapter(root.getContext(), courses, this);
        this.courses.setAdapter(courseAdapter);
    }

    @Override
    public void showMessage(String message, Boolean isPositive) {
        MessageDialog dialog = new MessageDialog(message, root.getContext(), isPositive);
        dialog.show(getFragmentManager(), "dia");
    }

    @Override
    public void editDialog(Course course) {
        CourseNameDialog dialog = new CourseNameDialog(this, course);
        dialog.show(getFragmentManager(), "dia");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.createCourseButton) {
            CourseCreateDialog dialog = new CourseCreateDialog(this);
            dialog.show(getFragmentManager(), "dia");
        }
    }
}