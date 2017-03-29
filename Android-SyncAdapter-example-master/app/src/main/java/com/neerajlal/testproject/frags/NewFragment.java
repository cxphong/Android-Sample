package com.neerajlal.testproject.frags;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.neerajlal.testproject.R;
import com.neerajlal.testproject.contentproviders.ScoreProvider;

public class NewFragment extends Fragment {

    private EditText edt_name;
    private EditText edt_score;

    public NewFragment() {
        // Required empty public constructor
    }

    public static NewFragment newInstance() {
        Bundle args = new Bundle();
        NewFragment fragment = new NewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_score, container, false);

        edt_name = (EditText) view.findViewById(R.id.edt_name);
        edt_score = (EditText) view.findViewById(R.id.edt_score);

        view.findViewById(android.R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_name.getText().toString().isEmpty() || !edt_score.getText().toString().isEmpty()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", edt_name.getText().toString());
                    contentValues.put("score", edt_score.getText().toString());
                    contentValues.put("dirty", 0);
                    getActivity().getContentResolver().insert(ScoreProvider.CONTENT_URI, contentValues);

                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
        return view;
    }

}
