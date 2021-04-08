package burlakov.learnthis.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import burlakov.learnthis.R;

public class LearnerMarksFragmentActivity extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_marks_learner, container, false);
        TextView textView = root.findViewById(R.id.textView);
        textView.setText(this.getClass().toString());
        return root;
    }
}
