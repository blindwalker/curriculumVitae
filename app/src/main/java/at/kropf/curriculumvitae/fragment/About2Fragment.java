package at.kropf.curriculumvitae.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.kropf.curriculumvitae.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class About2Fragment extends Fragment {


    public About2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about2
                , container, false);
    }


}
