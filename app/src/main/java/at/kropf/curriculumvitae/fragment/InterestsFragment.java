package at.kropf.curriculumvitae.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import at.kropf.curriculumvitae.R;
import at.kropf.curriculumvitae.Skill;
import at.kropf.curriculumvitae.SkillsAdapter;

/**
 * Created by martinkropf on 28.08.15.
 */
public class InterestsFragment extends Fragment {


    private int[] images = {0, 0, 0};
    private String[] titles = {"Unity", "iOS", "Raspberry Pi"};
    private int[] contents = {R.string.interest_unity, R.string.interest_ios, R.string.interest_raspberry};

    // newInstance constructor for creating fragment with arguments
    public static InterestsFragment newInstance() {
        return new InterestsFragment();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_android_skills, container, false);

        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        SkillsAdapter ca = new SkillsAdapter(createList(images.length));
        recList.setAdapter(ca);

        return v;
    }

    private List<Skill> createList(int size) {

        List<Skill> result = new ArrayList<Skill>();
        for (int i = 0; i < size; i++) {

            Skill skill = new Skill();
            skill.skillImage = null;
            skill.skillName = titles[i];
            skill.skillText = getResources().getString(contents[i]);

            result.add(skill);

        }

        return result;
    }
}
