package burlakov.learnthis.models.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.TeacherCourses;
import burlakov.learnthis.models.Course;
import burlakov.learnthis.views.CourseManagerTeacherActivity;

/**
 * Адаптер для отображения курсов
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private Context context;
    private List<Course> courses;
    private TeacherCourses.View view;

    public CourseAdapter(Context context, List<Course> courses, TeacherCourses.View view) {
        this.context = context;
        this.courses = courses;
        this.view = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        return new CourseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Course course = courses.get(position);

        holder.courseName.setText(course.getName());

        holder.itemView.setOnClickListener(l -> {
            Intent intent = new Intent(context, CourseManagerTeacherActivity.class);
            intent.putExtra("courseId", course.getId());
            context.startActivity(intent);
        });
        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, context.getResources().getString(R.string.edit));
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, context.getResources().getString(R.string.delete));
            Edit.setOnMenuItemClickListener(item -> {
                view.editDialog(course);
                return true;
            });
            Delete.setOnMenuItemClickListener(item -> {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Courses").child(course.getId());
                ref.removeValue();
                return true;
            });
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.courseName);
        }
    }
}
