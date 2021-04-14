package burlakov.learnthis.presenters;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import burlakov.learnthis.contracts.TeacherCourses;
import burlakov.learnthis.models.Course;

/**
 * Презентер для вкладки курсы у учителя
 */
public class TeacherCoursesPresenter implements TeacherCourses.Presenter {
    TeacherCourses.View view;
    FirebaseDatabase firebaseDatabase;
    ArrayList<Course> courses;

    public TeacherCoursesPresenter(TeacherCourses.View view) {
        this.view = view;
        firebaseDatabase = FirebaseDatabase.getInstance();
        courses = new ArrayList<>();
    }

    @Override
    public void startDBCourseListen() {
        DatabaseReference ref = firebaseDatabase.getReference("Courses");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Course> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Course course = dataSnapshot.getValue(Course.class);
                    course.setId(dataSnapshot.getKey());
                    if (course.getCreatorId().equals(FirebaseAuth.getInstance().getUid())) {
                        list.add(course);
                    }
                }
                // ************** Возможно сортировка
                if (courses.size() == 0) {
                    courses.addAll(list);
                    view.setIntoAdapter(list);
                } else {
                    if (!courses.equals(list)) {
                        courses.clear();
                        courses.addAll(list);
                        view.setIntoAdapter(list);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.showMessage(error.getMessage(), false);
            }
        });

    }
}
