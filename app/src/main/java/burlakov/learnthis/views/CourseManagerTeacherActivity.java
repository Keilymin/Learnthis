package burlakov.learnthis.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.CourseManager;
import burlakov.learnthis.models.CourseElement;
import burlakov.learnthis.models.adapters.CourseAdapter;
import burlakov.learnthis.models.adapters.CourseElementAdapter;
import burlakov.learnthis.presenters.CourseManagerPresenter;
import burlakov.learnthis.presenters.dialogs.ChoiceCourseElementDialog;
import burlakov.learnthis.presenters.dialogs.MessageDialog;
import burlakov.learnthis.views.fragments.TeacherCoursesFragmentActivity;

/**
 * Активити для управления Элементами курса
 */
public class CourseManagerTeacherActivity extends AppCompatActivity implements CourseManager.View, View.OnClickListener {
    RecyclerView list;
    Button addButton;
    Button learnersButton;
    String courseId;
    CourseElementAdapter courseElementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manager_teacher);

        Toolbar toolbar = findViewById(R.id.tool);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });


        list = findViewById(R.id.list);
        list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(linearLayoutManager);

        addButton = findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(this);
        learnersButton = findViewById(R.id.buttonLearners);
        learnersButton.setOnClickListener(this);

        courseId = getIntent().getStringExtra("courseId");

        CourseManagerPresenter presenter = new CourseManagerPresenter(this, courseId);
        presenter.startDBCourseElementListen();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonAdd) {
            ChoiceCourseElementDialog dialog = new ChoiceCourseElementDialog(courseId, getIntent().getStringExtra("name"));
            dialog.show(getSupportFragmentManager(), "dia");
        } else if (id == R.id.buttonLearners) {
//TODO
        }
    }

    @Override
    public void setIntoAdapter(ArrayList<CourseElement> elements) {
        courseElementAdapter = new CourseElementAdapter(this, elements, this, getIntent().getStringExtra("name"));
        this.list.setAdapter(courseElementAdapter);
    }

    @Override
    public void showMessage(String message, Boolean isPositive) {
        MessageDialog dialog = new MessageDialog(message, this, isPositive);
        dialog.show(getSupportFragmentManager(), "dia");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
