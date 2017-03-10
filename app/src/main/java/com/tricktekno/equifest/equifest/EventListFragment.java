package com.tricktekno.equifest.equifest;

/**
 * Created by anura on 12/9/2016.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventListFragment extends EventFragment{
    public static final int TYPE_LIST=1,
                            TYPE_LIST_GROUP=2;

    public EventListFragment(){
    }

    public static final EventListFragment newInstance(int type, String title, int actionbar_color, int description_layout, ArrayList<EventSummary> event_list){
        EventListFragment el = new EventListFragment();
        Bundle args = new Bundle();

        args.putString("title", title);
        args.putInt("actionbar_color", actionbar_color);
        args.putInt("description_layout", description_layout);
        args.putParcelableArrayList("event_list", event_list);

        el.setArguments(args);
        return el;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args=getArguments();
        int layout=R.layout.template_event_list;

        View rootView = inflater.inflate(layout, container, false);
        ViewGroup content = (LinearLayout) rootView.findViewById(R.id.content);
        View descriptionLayout = inflater.inflate(args.getInt("description_layout"), content, false);

        ArrayList<EventSummary> event_list = args.getParcelableArrayList("event_list");

        content.addView(descriptionLayout);

        if(event_list !=null){
            for(EventSummary es: event_list){
                LinearLayout summary_container = (LinearLayout)inflater.inflate(R.layout.container_event_summary, null);

                Log.d("Picasso", es.title);
                Picasso.with(getActivity()).load(es.image_id).fit().into(((ImageView)summary_container.findViewById(R.id.event_image)));

                ((TextView)summary_container.findViewById(R.id.event_title)).setText(es.title);
                ((TextView)summary_container.findViewById(R.id.event_description)).setText(es.description);
                summary_container.setId(es.id);

                content.addView(summary_container);
            }
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getString("title"),
                getArguments().getInt("actionbar_color"));
    }

    @Override
    public String getTitle() {
        return getArguments().getString("title");
    }

    @Override
    public int getActionBarColor() {
        return getArguments().getInt("actionbar_color");
    }
}
