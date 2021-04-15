package burlakov.learnthis.models.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.CourseManager;
import burlakov.learnthis.models.CourseElement;
import burlakov.learnthis.models.Lecture;
import burlakov.learnthis.models.Material;
import burlakov.learnthis.models.Task;
import burlakov.learnthis.presenters.CreateLecturePresenter;
import burlakov.learnthis.util.ClassUtil;
import burlakov.learnthis.util.StorageDeleter;
import burlakov.learnthis.views.CreateLectureActivity;
import burlakov.learnthis.views.CreateTaskActivity;
import burlakov.learnthis.views.LectureActivity;
import burlakov.learnthis.views.TaskActivity;

/**
 * Адаптер для элементов курса
 */
public class CourseElementAdapter extends RecyclerView.Adapter<CourseElementAdapter.ViewHolder> {
    private Context context;
    private List<CourseElement> elements;
    private CourseManager.View view;
    FirebaseStorage firebaseStorage;
    String name;

    public CourseElementAdapter(Context context, List<CourseElement> elements, CourseManager.View view, String name) {
        this.context = context;
        this.elements = elements;
        this.view = view;
        firebaseStorage = FirebaseStorage.getInstance();
        this.name = name;
    }

    @NonNull
    @Override
    public CourseElementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_element_item, parent, false);
        return new CourseElementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseElementAdapter.ViewHolder holder, int position) {
        final CourseElement element = elements.get(position);

        holder.courseElementName.setText(element.getTheme());

        holder.itemView.setOnClickListener(l -> {
            Intent intent = null;
            if (element instanceof Lecture) {
                intent = new Intent(context, LectureActivity.class);
            } else if (element instanceof Task) {
                intent = new Intent(context, TaskActivity.class);
            }
            intent.putExtra("courseElementId", element.getId());
            intent.putExtra("courseId", element.getCourseId());
            context.startActivity(intent);
        });
        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, context.getResources().getString(R.string.edit));
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, context.getResources().getString(R.string.delete));
            Edit.setOnMenuItemClickListener(item -> {
                Intent intent = null;
                if (element instanceof Lecture) {
                    intent = new Intent(context, CreateLectureActivity.class);
                } else if (element instanceof Task) {
                    intent = new Intent(context, CreateTaskActivity.class);
                }
                intent.putExtra("courseId", element.getCourseId());
                intent.putExtra("name", name);
                intent.putExtra("elementId", element.getId());
                context.startActivity(intent);
                return true;
            });
            Delete.setOnMenuItemClickListener(item -> {
                String className = ClassUtil.getObjectClassName(element);

                StorageDeleter.deleteDataByCourseElement(element,view);


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Courses").child(element.getCourseId())
                        .child("CourseElements").child(className).child(element.getId());
                ref.removeValue();

                return true;
            });
        });
    }


    @Override
    public int getItemCount() {
        return elements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseElementName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseElementName = itemView.findViewById(R.id.courseElementName);
        }
    }
}
