package burlakov.learnthis.presenters.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import burlakov.learnthis.R;
import burlakov.learnthis.views.CreateLectureActivity;
import burlakov.learnthis.views.CreateTaskActivity;

/**
 * Диалог для выбора создаваемого элемента курса
 */
public class ChoiceCourseElementDialog extends DialogFragment implements View.OnClickListener {
    Button buttonLecture;
    Button buttonTask;
    String courseId;
    String name;

    public ChoiceCourseElementDialog(String courseId, String name) {
        this.courseId = courseId;
        this.name = name;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final ViewGroup nullParent = null;
        builder.setView(inflater.inflate(R.layout.dialog_choice_course_element, nullParent))
                .setNegativeButton(R.string.back, (dialog, id) -> ChoiceCourseElementDialog.this.getDialog().cancel());
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        buttonLecture = getDialog().findViewById(R.id.buttonLecture);
        buttonTask = getDialog().findViewById(R.id.buttonTask);

        buttonLecture.setOnClickListener(this);
        buttonTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int id = v.getId();
        if (id == R.id.buttonLecture) {
            intent = new Intent(getContext(), CreateLectureActivity.class);
        } else if (id == R.id.buttonTask) {
            intent = new Intent(getContext(), CreateTaskActivity.class);
        }
        intent.putExtra("courseId", courseId);
        intent.putExtra("name", name);
        getContext().startActivity(intent);
    }
}
