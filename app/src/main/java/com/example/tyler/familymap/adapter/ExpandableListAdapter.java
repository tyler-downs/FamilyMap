package com.example.tyler.familymap.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyler.familymap.R;
import com.example.tyler.familymap.model.PersonIDtoEvents;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tyler on 8/2/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    ArrayList<String> listHeaders = new ArrayList<>();
    HashMap<String, ArrayList<ArrayList<String>>> listChildData = new HashMap<>();

    public ExpandableListAdapter(Context context, ArrayList<String> listDataHeaders,
                                 HashMap<String, ArrayList<ArrayList<String>>> listChildData) {
        this.context = context;
        this.listHeaders = listDataHeaders;
        this.listChildData = listChildData;
    }

    @Override
    public int getGroupCount() {
        return listHeaders.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listChildData.get(listHeaders.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listHeaders.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listChildData.get(listHeaders.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) view
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final ArrayList<String> childTexts = (ArrayList<String>) getChild(i, i1);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) view
                .findViewById(R.id.top_details_person);

        TextView botTextChild = (TextView) view.findViewById(R.id.bottom_details_person);
        txtListChild.setText(childTexts.get(0));
        botTextChild.setText(childTexts.get(1));
        ImageView iconView = (ImageView) view.findViewById(R.id.icon_person);
        iconView.setImageDrawable(new IconDrawable(context, Iconify.IconValue.fa_chevron_right));
        if (botTextChild.getText().equals("Father") || botTextChild.getText().equals("Son"))
        {
            iconView.setImageDrawable(new IconDrawable(context, Iconify.IconValue.fa_male).color(R.color.male_icon));
        }
        if (botTextChild.getText().equals("Mother") || botTextChild.getText().equals("Daughter"))
        {
            iconView.setImageDrawable(new IconDrawable(context, Iconify.IconValue.fa_female).color(R.color.female_icon));
        }
        if (botTextChild.getText().equals("Spouse") && childTexts.get(2).equals("m"))
        {
            iconView.setImageDrawable(new IconDrawable(context, Iconify.IconValue.fa_male).color(R.color.male_icon));
        }
        if (botTextChild.getText().equals("Spouse") && childTexts.get(2).equals("f"))
        {
            iconView.setImageDrawable(new IconDrawable(context, Iconify.IconValue.fa_female).color(R.color.female_icon));
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
