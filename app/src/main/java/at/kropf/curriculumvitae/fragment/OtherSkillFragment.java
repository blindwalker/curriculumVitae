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
public class OtherSkillFragment extends Fragment {


    private int[] images = {R.drawable.php_header, R.drawable.sketch_header, R.drawable.git_header};
    private String[] titles = {"PHP", "Sketch", "Git"};
    private int[] contents = {R.string.skill_php, R.string.skill_sketch, R.string.skill_git};

    // newInstance constructor for creating fragment with arguments
    public static OtherSkillFragment newInstance() {
        return new OtherSkillFragment();
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
            skill.skillImage = getResources().getDrawable(images[i]);
            skill.skillName = titles[i];
            skill.skillText = getResources().getString(contents[i]);

            result.add(skill);

        }

        return result;
    }
}
