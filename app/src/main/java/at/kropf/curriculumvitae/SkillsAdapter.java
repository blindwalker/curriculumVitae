package at.kropf.curriculumvitae;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by martinkropf on 20.08.15.
 */
public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ContactViewHolder> {

    private List<Skill> contactList;

    public SkillsAdapter(List<Skill> contactList) {
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Skill skill = contactList.get(i);
        contactViewHolder.vContent.setText(skill.skillText);
        contactViewHolder.vTitle.setText(skill.skillName);
        if (skill.skillImage == null) {
            contactViewHolder.vImage.setVisibility(View.GONE);
        } else {
            contactViewHolder.vImage.setImageDrawable(skill.skillImage);

        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.include_cardview, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected TextView vContent;
        protected ImageView vImage;

        public ContactViewHolder(View v) {
            super(v);
            vContent = (TextView) v.findViewById(R.id.cardContent);
            vImage = (ImageView) v.findViewById(R.id.cardImage);
            vTitle = (TextView) v.findViewById(R.id.cardTitle);
        }
    }
    
}