package at.kropf.curriculumvitae.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.kropf.curriculumvitae.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class About6Fragment extends Fragment {


    public About6Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_about6
                , container, false);

        currentView.findViewById(R.id.githubLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/blindwalker/curriculumVitae"));
                startActivity(i);
            }
        });
        // Inflate the layout for this fragment
        return currentView;
    }


}
