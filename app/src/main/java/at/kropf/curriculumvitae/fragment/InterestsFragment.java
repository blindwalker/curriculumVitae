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
 * Fragment which is displayed in viewpager
 */
public class InterestsFragment extends Fragment {

    private final String[] titles = {"Unity", "iOS", "Raspberry Pi"};
    private final int[] contents = {R.string.interest_unity, R.string.interest_ios, R.string.interest_raspberry};

    // newInstance constructor for creating fragment with arguments
    public static InterestsFragment newInstance() {
        return new InterestsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_android_skills, container, false);

        //Create recyclerView and add it to layout
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        SkillsAdapter ca = new SkillsAdapter(createList(titles.length));
        recList.setAdapter(ca);

        return v;
    }

    //create skill from array
    private List<Skill> createList(int size) {

        List<Skill> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {

            Skill skill = new Skill();
            skill.skillName = titles[i];
            skill.skillText = getResources().getString(contents[i]);

            result.add(skill);

        }

        return result;
    }
}
