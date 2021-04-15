package burlakov.learnthis.presenters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.CreateMaterialManager;
import burlakov.learnthis.models.CourseElement;
import burlakov.learnthis.models.Lecture;
import burlakov.learnthis.models.Material;
import burlakov.learnthis.util.StorageDeleter;
import burlakov.learnthis.views.CourseManagerTeacherActivity;

/**
 * Презентер для создания и редактирование лекций
 */
public class CreateLecturePresenter implements CreateMaterialManager.Presenter {
    CreateMaterialManager.View view;
    Context context;
    private StorageTask<UploadTask.TaskSnapshot> task;
    String name;
    FirebaseDatabase database;

    public CreateLecturePresenter(CreateMaterialManager.View view, Context context, String name) {
        this.view = view;
        this.context = context;
        this.name = name;
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void saveCourseElement(CourseElement element, String elementId) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(context.getResources().getString(R.string.upload_on));
        pd.show();
        DatabaseReference myRef;
        if (elementId == null) {
            myRef = database.getReference("Courses").child(element.getCourseId()).child("CourseElements").child("Lecture").push();
        } else {
            myRef = database.getReference("Courses").child(element.getCourseId()).child("CourseElements").child("Lecture").child(elementId);
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("theme", element.getTheme());
        hashMap.put("courseId", element.getCourseId());
        if (elementId == null) {
            element.setId(myRef.getKey());
            myRef.setValue(hashMap);
        } else {
            element.setId(elementId);
            StorageDeleter.deleteDataByCourseElement(element, view);
            myRef.updateChildren(hashMap);
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();

        for (Material material : element.getMaterials()) {
            String pnm = System.currentTimeMillis() + "" + material.getFileName();
            StorageReference storageReference = storage.getReference().child("Materials").child(element.getCourseId())
                    .child(element.getId()).child(pnm);

            task = storageReference.putFile(material.getFileUri());
            task.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                if (!task.isSuccessful()) {
                    view.showMessage(material.getFileName() + " " + context.getResources().getString(R.string.error), false);
                    element.getMaterials().remove(material);
                }
                return storageReference.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();

                    DatabaseReference ref = database.getReference("Courses").child(element.getCourseId()).child("CourseElements").child("Lecture").child(element.getId()).child("Materials").push();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("fileStorageUri", mUri);
                    map.put("fileName", material.getFileName());
                    map.put("filePath", "Materials/" + element.getCourseId() + "/" + element.getId() + "/" + pnm);
                    ref.updateChildren(map);


                    pd.dismiss();
                } else {
                    view.showMessage(task.getException().getMessage(), false);
                    pd.dismiss();
                }
            }).addOnFailureListener((OnFailureListener) e -> {
                view.showMessage(task.getException().getMessage(), false);
                pd.dismiss();
            });
        }
        Intent intent = new Intent(context, CourseManagerTeacherActivity.class);
        intent.putExtra("courseId", element.getCourseId());
        intent.putExtra("name", name);
        context.startActivity(intent);


    }

    @Override
    public void loadData(String courseId, String elementId) {
        DatabaseReference ref = database.getReference("Courses").child(courseId).child("CourseElements").child("Lecture").child(elementId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Material> list = new ArrayList<>();
                Lecture lecture = snapshot.getValue(Lecture.class);
                lecture.setId(snapshot.getKey());
                view.setTheme(lecture.getTheme());
                for (DataSnapshot dataSnapshot : snapshot.child("Materials").getChildren()) {
                    Material material = dataSnapshot.getValue(Material.class);
                    list.add(material);
                }
                view.setIntoAdapter(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.showMessage(error.getMessage(), false);
            }
        });
    }
}
