package at.kropf.curriculumvitae;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SkillsActivity extends AppCompatActivity {

    private String[] titles = {"Android", "PHP", "HTML/JS", "Sketch", "Git", "Jenkins"};
    private String[] contents = {"Android", "PHP", "HTML/JS", "Sketch", "Git", "Jenkins"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        SkillsAdapter ca = new SkillsAdapter(createList(5));
        recList.setAdapter(ca);
    }

    private List<Skill> createList(int size) {

        List<Skill> result = new ArrayList<Skill>();
        for (int i = 1; i <= size; i++) {

            Skill skill = new Skill();
            skill.skillImage = getDrawable(R.drawable.profile_pic);
            skill.skillName = contents[i];
            skill.skillText = titles[i];

            result.add(skill);

        }

        return result;
    }
}