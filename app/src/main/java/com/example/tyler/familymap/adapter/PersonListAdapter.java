package com.example.tyler.familymap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyler.familymap.R;
import com.example.tyler.familymap.model.Person;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 8/4/2016.
 */
public class PersonListAdapter extends ArrayAdapter<Person> {

    private int layout;
    private List<Person> mObjects;

    public PersonListAdapter(Context context, int resource, List<Person> objects) {
        super(context, resource, objects);
        layout = resource;
        mObjects = objects; //is full of the search results person objects
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder mainViewHolder = null;
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.search_result_icon_1);
            viewHolder.topText = (TextView) convertView.findViewById(R.id.search_result_top_text_1);
            convertView.setTag(viewHolder);
        }
        mainViewHolder = (ViewHolder) convertView.getTag();

        mainViewHolder.topText.setText(getItem(position).toString());
        if (mObjects.get(position).getGender().equals("m"))
        {
            mainViewHolder.thumbnail.setImageDrawable(new IconDrawable
                    (getContext(), Iconify.IconValue.fa_male).colorRes(R.color.male_icon));
        }
        else
        {
            mainViewHolder.thumbnail.setImageDrawable(new IconDrawable
                    (getContext(), Iconify.IconValue.fa_female).colorRes(R.color.female_icon));
        }

        return convertView;
    }



    public class ViewHolder{
        ImageView thumbnail;
        TextView topText;
    }

}
