package com.example.tyler.familymap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyler.familymap.R;
import com.example.tyler.familymap.model.ModelData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tyler on 8/5/2016.
 */
public class FilterEventListAdapter extends ArrayAdapter<String> {

    private int layout;
    private List<String> mObjects;

    public FilterEventListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
        mObjects = objects;
        ModelData.getInstance().eventTypes = objects;
        if (ModelData.getInstance().eventTypesSwitchMap.isEmpty())
        {
            for (String s : objects)
            {
                ModelData.getInstance().eventTypesSwitchMap.put(s, true);
            }
        }


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
            viewHolder.filterSwitch = (Switch) convertView.findViewById(R.id.filter_event_switch);
            viewHolder.topText = (TextView) convertView.findViewById(R.id.filter_event_top_text);
            viewHolder.bottomText = (TextView) convertView.findViewById(R.id.filter_event_bottom_text);
            convertView.setTag(viewHolder);
        }
        mainViewHolder = (ViewHolder) convertView.getTag();

        final String description = getItem(position);
        mainViewHolder.topText.setText(description + " events");
        mainViewHolder.bottomText.setText("Filter by " + description + " events");

        //Listener for switch
        boolean bool = ModelData.getInstance().eventTypesSwitchMap.get(description);
        mainViewHolder.filterSwitch.isChecked();

        mainViewHolder.filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(getContext(), "Switch " + description + " on", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().eventTypesSwitchMap.put(description, compoundButton.isChecked());
                }
                else
                {
                    Toast.makeText(getContext(), "Switch " + description + " off", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().eventTypesSwitchMap.put(description, compoundButton.isChecked());
                }
            }
        });
        mainViewHolder.filterSwitch.setChecked(bool);
        return convertView;
    }




    public class ViewHolder {
        Switch filterSwitch;
        TextView topText;
        TextView bottomText;
    }
}
