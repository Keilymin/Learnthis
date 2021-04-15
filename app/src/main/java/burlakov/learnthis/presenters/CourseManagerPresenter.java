package burlakov.learnthis.presenters;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import burlakov.learnthis.contracts.CourseManager;
import burlakov.learnthis.models.CourseElement;
import burlakov.learnthis.models.Lecture;
import burlakov.learnthis.models.Task;

/**
 * Презентер для вкладки курсов
 */
public class CourseManagerPresenter implements CourseManager.Presenter {
    CourseManager.View view;
    FirebaseDatabase firebaseDatabase;
    ArrayList<CourseElement> elements;
    String courseId;

    public CourseManagerPresenter(CourseManager.View view, String courseId) {
        this.view = view;
        this.courseId = courseId;
        firebaseDatabase = FirebaseDatabase.getInstance();
        elements = new ArrayList<>();
    }

    @Override
    public void startDBCourseElementListen() {
        DatabaseReference ref = firebaseDatabase.getReference("Courses").child(courseId).child("CourseElements");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CourseElement> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.child("Lecture").getChildren()) {
                    Lecture lecture = dataSnapshot.getValue(Lecture.class);
                    assert lecture != null;
                    lecture.setId(dataSnapshot.getKey());
                    lecture.setCourseId(courseId);
                    list.add(lecture);
                }
                for (DataSnapshot dataSnapshot : snapshot.child("Task").getChildren()) {
                    Task task = dataSnapshot.getValue(Task.class);
                    assert task != null;
                    task.setId(dataSnapshot.getKey());
                    task.setCourseId(courseId);
                    list.add(task);
                }
                // ************** Возможно сортировка
                if (elements.size() == 0) {
                    elements.addAll(list);
                    view.setIntoAdapter(list);
                } else {
                    if (!elements.equals(list)) {
                        elements.clear();
                        elements.addAll(list);
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
