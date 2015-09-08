package at.kropf.curriculumvitae.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.kropf.curriculumvitae.AboutActivity;
import at.kropf.curriculumvitae.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public static AboutFragment newInstance(int pageNumber) {
        AboutFragment myFragment = new AboutFragment();

        Bundle args = new Bundle();
        args.putInt("pageNumber", pageNumber);
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        switch (getArguments().getInt("pageNumber", 0)){
            case 1:
                return inflater.inflate(R.layout.fragment_about1, container, false);
            case 2:
                return inflater.inflate(R.layout.fragment_about2, container, false);
            case 3:
                return inflater.inflate(R.layout.fragment_about3, container, false);
            case 4:
                return inflater.inflate(R.layout.fragment_about4, container, false);
            case 5:
                return inflater.inflate(R.layout.fragment_about5, container, false);
            case 6:
                return inflater.inflate(R.layout.fragment_about6, container, false);
            default:
                return inflater.inflate(R.layout.fragment_about1, container, false);
        }
    }
}
