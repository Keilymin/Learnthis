package burlakov.learnthis.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import burlakov.learnthis.contracts.IView;
import burlakov.learnthis.models.Course;
import burlakov.learnthis.models.CourseElement;
import burlakov.learnthis.models.Lecture;
import burlakov.learnthis.models.Material;
import burlakov.learnthis.models.Task;

/**
 * Класс для удаление мусора из сторейжа
 */
public class StorageDeleter {
    /**
     * Удаляет все данные с курса
     *
     * @param course Курс
     * @param view   Изначально вью
     */
    public static void deleteDataByCourse(Course course, IView view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("Courses").child(course.getId()).child("CourseElements");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CourseElement> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.child("Lecture").getChildren()) {
                    Lecture lecture = dataSnapshot.getValue(Lecture.class);
                    assert lecture != null;
                    lecture.setId(dataSnapshot.getKey());
                    lecture.setCourseId(course.getId());
                    list.add(lecture);
                }
                for (DataSnapshot dataSnapshot : snapshot.child("Task").getChildren()) {
                    Task task = dataSnapshot.getValue(Task.class);
                    assert task != null;
                    task.setId(dataSnapshot.getKey());
                    task.setCourseId(course.getId());
                    list.add(task);
                }
                for (CourseElement element : list) {
                    StorageDeleter.deleteDataByCourseElement(element, view);
                }
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Courses").child(course.getId());
                ref.removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.showMessage(error.getMessage(), false);
            }
        });
    }

    /**
     * Удаляет данные с элемента курса
     *
     * @param element Элемент
     * @param view Изначальное вью
     */
    public static void deleteDataByCourseElement(CourseElement element, IView view) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        String className = ClassUtil.getObjectClassName(element);
        DatabaseReference r = FirebaseDatabase.getInstance().getReference("Courses").child(element.getCourseId())
                .child("CourseElements").child(className).child(element.getId()).child("Materials");
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> deletePaths = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Material material = dataSnapshot.getValue(Material.class);
                    deletePaths.add(material.getFileStorageUri());
                }
                for (String path : deletePaths) {
                    StorageReference storage = firebaseStorage.getReferenceFromUrl(path);
                    storage.delete();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.showMessage(error.getMessage(), false);
            }
        });
    }
}
