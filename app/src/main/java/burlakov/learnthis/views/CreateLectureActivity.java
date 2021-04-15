package burlakov.learnthis.views;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.CreateMaterialManager;
import burlakov.learnthis.models.Lecture;
import burlakov.learnthis.models.Material;
import burlakov.learnthis.models.adapters.MaterialAdapter;
import burlakov.learnthis.presenters.CreateLecturePresenter;
import burlakov.learnthis.presenters.dialogs.MessageDialog;

/**
 * Активити для создания лекции
 */
public class CreateLectureActivity extends AppCompatActivity implements CreateMaterialManager.View {
    public static final int PICKFILE_RESULT_CODE = 1;
    RecyclerView list;
    Button addMaterial;
    Button buttonCreate;
    EditText theme;
    MaterialAdapter adapter;
    private Uri fileUri;
    private String filePath;
    List<Material> mater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lecture);

        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        mater = new ArrayList<>();
        theme = findViewById(R.id.theme);

        ConstraintLayout layout = findViewById(R.id.lay);
        list = layout.findViewById(R.id.list);
        list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(linearLayoutManager);

        addMaterial = findViewById(R.id.addMaterial);

        addMaterial.setOnClickListener(l -> {
                    Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                    chooseFile.setType("*/*");
                    chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                    startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
                }
        );
        CreateLecturePresenter presenter = new CreateLecturePresenter(this, this, getIntent().getStringExtra("name"));
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(l -> {
            if (mater.size() > 0) {
                if (theme.getText().toString().length() > 0) {
                    Lecture lecture = new Lecture(theme.getText().toString(), mater, getIntent().getStringExtra("courseId"));
                    presenter.saveCourseElement(lecture, getIntent().getStringExtra("elementId"));
                } else showMessage(getResources().getString(R.string.error_theme), false);
            } else showMessage(getResources().getString(R.string.prev_load_material), false);
        });
        String id = getIntent().getStringExtra("elementId");
        if (id != null) {
            presenter.loadData(getIntent().getStringExtra("courseId"), id);
        }
    }

    @Override
    public void showMessage(String message, Boolean isPositive) {
        MessageDialog dialog = new MessageDialog(message, this, isPositive);
        dialog.show(getSupportFragmentManager(), "dia");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE) {
            if (resultCode == -1) {
                fileUri = data.getData();
                filePath = fileUri.getPath();
                mater.add(new Material(fileUri));
                setIntoAdapter(mater);
            }
        }
    }

    @Override
    public void setIntoAdapter(List<Material> materials) {
        adapter = new MaterialAdapter(this, materials, this);
        this.list.setAdapter(adapter);
    }

    @Override
    public void setTheme(String theme) {
        this.theme.setText(theme);
    }
}
