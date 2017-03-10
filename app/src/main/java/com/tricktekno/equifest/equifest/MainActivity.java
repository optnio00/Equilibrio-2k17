package com.tricktekno.equifest.equifest;

/**
 * Created by anura on 12/9/2016.
 */

import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Stack;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnMapReadyCallback{

    ImageView tf_logo;

    ArrayList<EventSummary> events = new ArrayList<EventSummary>();
    Stack<Fragment> fragStack = new Stack<Fragment>();

    HashMap<String, Integer[]> layout_desc = new HashMap<String, Integer[]>();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    SupportMapFragment mapFrag;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private int mActionBarColor = R.color.actionbar_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer(findViewById(R.id.navigation_drawer));

        tf_logo = new ImageView(this);

        Toast.makeText(getApplicationContext(),
                "Enable GPS and Data for Best UX", Toast.LENGTH_LONG).show();

        Picasso.with(this).load(R.drawable.tf_logo).fit().centerCrop().into((tf_logo));

        events = getIntent().getParcelableArrayListExtra("events");

        layout_desc.put("Alchemist", new Integer[]{R.layout.details_technocalypse, R.drawable.che, R.color.actionbar_competitions});
        layout_desc.put("Chem-e-car", new Integer[]{R.layout.details_codeblitz, R.drawable.alchemist, R.color.actionbar_competitions});
        layout_desc.put("Chem-o-Mania", new Integer[]{R.layout.details_vortex, R.drawable.chemo, R.color.actionbar_competitions});
        layout_desc.put("Pet-Bottle Rocket", new Integer[]{R.layout.details_mechatron, R.drawable.pbr, R.color.actionbar_competitions});

        layout_desc.put("Cybernet", new Integer[]{R.layout.details_robotron, R.drawable.cod1, R.color.actionbar_lectures});
        layout_desc.put("Code Sense", new Integer[]{R.layout.details_pixelate, R.drawable.codesense, R.color.actionbar_lectures});
        layout_desc.put("BrainWaves", new Integer[]{R.layout.details_hydranoid, R.drawable.brainwaves, R.color.actionbar_lectures});
        layout_desc.put("Webpulse", new Integer[]{R.layout.details_cantilivo, R.drawable.webpulse, R.color.actionbar_lectures});
        layout_desc.put("Codebid", new Integer[]{R.layout.details_technocrane, R.drawable.codebid, R.color.actionbar_lectures});

        layout_desc.put("Civil", new Integer[]{R.layout.details_international_challenge, R.drawable.civ, R.color.actionbar_exhibitions});
        layout_desc.put("Nirmaan", new Integer[]{R.layout.details_fermat15, R.drawable.nirmaan, R.color.actionbar_exhibitions});
        layout_desc.put("Hydraulic Crane", new Integer[]{R.layout.details_fermat1, R.drawable.crane, R.color.actionbar_exhibitions});
        layout_desc.put("Anti Earthquake Structure", new Integer[]{R.layout.details_fermat2, R.drawable.earth, R.color.actionbar_exhibitions});
        layout_desc.put("Setu Sanrachana", new Integer[]{R.layout.details_fermat3, R.drawable.setu, R.color.actionbar_exhibitions});
        //  layout_desc.put("Nirmaan", new Integer[]{R.layout.details_fermat, R.drawable.fermat, R.color.actionbar_competitions});

        layout_desc.put("Autonomous", new Integer[]{R.layout.details_dimensions, R.drawable.aut, R.color.actionbar_technoholix});
        layout_desc.put("Robo Terry", new Integer[]{R.layout.details_irc, R.drawable.terry, R.color.actionbar_technoholix});
        layout_desc.put("Snako Bot", new Integer[]{R.layout.details_ticc, R.drawable.snako, R.color.actionbar_technoholix});
        layout_desc.put("Need For Speed", new Integer[]{R.layout.details_tisc, R.drawable.nfs, R.color.actionbar_technoholix});
        layout_desc.put("Logic League", new Integer[]{R.layout.details_tisc1, R.drawable.nfs, R.color.actionbar_technoholix});

        layout_desc.put("Exposure", new Integer[]{R.layout.details_moneyball, R.drawable.mag, R.color.actionbar_initiatives});
        layout_desc.put("Karyaneeti", new Integer[]{R.layout.details_fermat4, R.drawable.karya, R.color.actionbar_initiatives});
        layout_desc.put("Startup Mania", new Integer[]{R.layout.details_fermat5, R.drawable.startup, R.color.actionbar_initiatives});
        layout_desc.put("Paper Presentation", new Integer[]{R.layout.details_fermat6, R.drawable.paper, R.color.actionbar_initiatives});
        layout_desc.put("Job Battle", new Integer[]{R.layout.details_fermat7, R.drawable.job, R.color.actionbar_initiatives});

        layout_desc.put("Quizzard", new Integer[]{R.layout.details_nextgen_tv, R.drawable.quiz, R.color.actionbar_ozone});
        layout_desc.put("Technical", new Integer[]{R.layout.details_magneto, R.drawable.techquiz, R.color.actionbar_ozone});
        layout_desc.put("General", new Integer[]{R.layout.details_scholastic, R.drawable.genquiz, R.color.actionbar_ozone});
        layout_desc.put("Bizwhiz", new Integer[]{R.layout.details_full_throttle, R.drawable.bizquiz, R.color.actionbar_ozone});

        layout_desc.put("Gaming Geeks", new Integer[]{R.layout.details_thinkernet, R.drawable.gam, R.color.actionbar_competitions});
        layout_desc.put("FIFA", new Integer[]{R.layout.details_fermat8, R.drawable.fifa, R.color.actionbar_competitions});

        layout_desc.put("Counter Strike", new Integer[]{R.layout.details_fermat9, R.drawable.cs, R.color.actionbar_competitions});
        layout_desc.put("Mini Militia", new Integer[]{R.layout.details_fermat10, R.drawable.mini, R.color.actionbar_competitions});

        layout_desc.put("Cultural", new Integer[]{R.layout.details_algorhythm, R.drawable.cul, R.color.actionbar_competitions});
        layout_desc.put("Carnival of Dance", new Integer[]{R.layout.details_fermat11, R.drawable.cod, R.color.actionbar_competitions});
        layout_desc.put("Light Camera Action", new Integer[]{R.layout.details_fermat12, R.drawable.lca, R.color.actionbar_competitions});
        layout_desc.put("Battle of Bands", new Integer[]{R.layout.details_fermat13, R.drawable.bands, R.color.actionbar_competitions});
        layout_desc.put("Fun Zone", new Integer[]{R.layout.details_fermat14, R.drawable.funzone, R.color.actionbar_competitions});

        layout_desc.put("Mechatronics", new Integer[]{R.layout.details_xtreme_machines, R.drawable.mec, R.color.actionbar_ideate});
        layout_desc.put("Robo War", new Integer[]{R.layout.details_combat_nautica, R.drawable.robowar, R.color.actionbar_ideate});
        layout_desc.put("Aquabotix", new Integer[]{R.layout.details_aviator_design, R.drawable.aquabotix, R.color.actionbar_ideate});
        layout_desc.put("Robo Soccer", new Integer[]{R.layout.details_aviator_flying, R.drawable.soccer, R.color.actionbar_ideate});
        layout_desc.put("Design Er", new Integer[]{R.layout.details_23_yard, R.drawable.designer, R.color.actionbar_ideate});
        layout_desc.put("F1 Unleash the Beast", new Integer[]{R.layout.details_striker, R.drawable.f1, R.color.actionbar_ideate});

        layout_desc.put("Sponsors", new Integer[]{R.layout.details_lectures, R.drawable.lecture, R.color.actionbar_lectures});
        layout_desc.put("Registration", new Integer[]{R.layout.details_exhibitions, R.drawable.exhibitions, R.color.actionbar_exhibitions});
/*
        // workshops
        layout_desc.put("Mechatronics Workshop", new Integer[]{R.layout.details_irc_workshop, R.drawable.mechwork, R.color.actionbar_workshops});
        layout_desc.put("Autonomous Workshop", new Integer[]{R.layout.details_augmented_reality, R.drawable.robot, R.color.actionbar_workshops});
        layout_desc.put("Cybernet Workshop", new Integer[]{R.layout.details_zero_energy_buildings, R.drawable.code, R.color.actionbar_workshops});
        layout_desc.put("Constructional", new Integer[]{R.layout.details_arduped, R.drawable.engineer, R.color.actionbar_workshops});
        layout_desc.put("Chemical", new Integer[]{R.layout.details_cloud_computing, R.drawable.chemistry, R.color.actionbar_workshops});
        layout_desc.put("Web Development", new Integer[]{R.layout.details_web_development, R.drawable.browser, R.color.actionbar_workshops});
        layout_desc.put("Android App Devpt", new Integer[]{R.layout.details_android_app_devpt, R.drawable.android, R.color.actionbar_workshops});
*/
        layout_desc.put("TALK", new Integer[]{R.layout.details_fermat, R.drawable.conference, R.color.actionbar_conference});
        addLayoutIDs();

        restoreActionBar();
    }

    @Override
    public Intent onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        mapFrag = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map_fragment);
        Fragment frag = null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        boolean show = false;


        position++;

        switch (position) {
            case 3:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP, getString(R.string.title_competitions), R.color.actionbar_competitions, R.layout.fragment_competitions, filterEvents(getString(R.string.title_competitions)));
                break;
            case 2:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP, getString(R.string.title_initiatives), R.color.actionbar_initiatives, R.layout.fragment_initiatives, filterEvents(getString(R.string.title_initiatives)));
                break;
            case 4:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP, getString(R.string.title_sponsors), R.color.actionbar_developers, R.layout.fragment_sponsors, null);
                break;
            case 5:
                frag = new registration();
                break;
            case 6:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP, getString(R.string.title_technoholix), R.color.actionbar_developers, R.layout.details_technoholix, null);
                break;
           
            case 7:
                frag = loadMapFragment();
        
            case 8:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP, getString(R.string.title_team), R.color.actionbar_developers, R.layout.fragments_team, null);
                break;
            case 9:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP, getString(R.string.title_developers), R.color.actionbar_developers, R.layout.fragment_developers, null);
                break;
            default:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP, getString(R.string.title_home), R.color.actionbar_home, R.layout.fragment_main, null);

                mapFrag.getMapAsync(this);

                show = true;
                break;
        }

        fragStack.push(frag);

        fragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .commit();

        if (show)
            fragmentManager.beginTransaction().show(mapFrag).commit();
        else if (mapFrag.isVisible())
            fragmentManager.beginTransaction().hide(mapFrag).commit();
        return null;
    }

    public ArrayList<EventSummary> filterEvents(String title) {
        ArrayList<EventSummary> temp = new ArrayList<EventSummary>();

        for (EventSummary es : events) {
            String[] categories = es.category.split("\\+");
            for (String cat : categories) {
                if (cat.equals(title)) {
                    temp.add(es);
                    break;
                }
            }
        }

        return temp;
    }

    public void loadDetails(View v) {
        EventDetailsFragment eventDetails = EventDetailsFragment.newInstance(events.get(v.getId()));

        fragStack.push(eventDetails);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, eventDetails)
                .commit();
    }

    public void loadList(View v) {
        String title = (String) v.getTag();

        EventListFragment eventList = EventListFragment.newInstance(
                EventListFragment.TYPE_LIST,
                title,
                layout_desc.get(title)[2],
                layout_desc.get(title)[0],
                filterEvents(title));

        fragStack.push(eventList);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, eventList)
                .commit();
    }

    public SupportMapFragment loadMapFragment() {
        SupportMapFragment mapFrag = SupportMapFragment.newInstance();

        mapFrag.getMapAsync(this);

        return mapFrag;
    }

    public void addLayoutIDs() {
        for (EventSummary es : events) {
            Integer[] temp_desc = layout_desc.get(es.title);
            if (temp_desc != null) {
                es.description_layout = temp_desc[0];
                es.image_id = temp_desc[1];
                es.actionbar_color = temp_desc[2];
            } else {
                es.description_layout = R.layout.details_robowars;
            }
        }
    }


    public void setReminder(View v) {
        EventSummary es = events.get((Integer) v.getTag());

        String beginTime = es.time.split("-")[0];
        int beginHours = Integer.valueOf(beginTime.split(":")[0]);
        int beginMinutes = Integer.valueOf(beginTime.split(":")[1]);

        String endTime;

        try {
            endTime = es.time.split("-")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            endTime = beginTime;
        }

        int endHours = Integer.valueOf(endTime.split(":")[0]);
        int endMinutes = Integer.valueOf(endTime.split(":")[1]);

        int year = Integer.valueOf(es.date.split("/")[2]);
        int month = Integer.valueOf(es.date.split("/")[1]) - 1;
        int day = Integer.valueOf(es.date.split("/")[0].split("\\+")[0]);


        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, beginHours, beginMinutes);

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", false);

        cal.set(year, month, day, endHours, endMinutes);

        intent.putExtra("endTime", cal.getTimeInMillis());
        intent.putExtra("title", es.title + " @ " + es.venue);
        intent.putExtra("description", es.description);
        startActivity(intent);
    }

    public String getLatLangString(String place) {
        if (place.equals("GROUND"))
            return "22.132085,82.142117";
        else if (place.equals("NIT"))
            return "22.131683,82.142100";
        else if (place.equals("CORIDOR NIT"))
            return "22.131679,82.141900";
        else if (place.equals("MDH AUDI"))
            return "22.132275,82.142233";
        else if (place.equals("F10 NIT"))
            return "22.132126,82.142075";
        else if (place.equals("F3 F4 F5"))
            return "22.128795,82.137555";
        else if (place.equals("AUDI"))
            return "22.132089,82.142340";
        else if (place.equals("NIT GROUND"))
            return "22.132295,82.142148";
        else if (place.equals("E CLASSROOM"))
            return "22.128948,82.137454";

        else
            return "22.1317629,82.1421549";
    }

    public LatLng getLatLng(String place) {
        String str = getLatLangString(place);

        return new LatLng(Float.valueOf(str.split(",")[0]), Float.valueOf(str.split(",")[1]));
    }

    public void getDirections(View v) {
        EventSummary es = events.get((Integer) v.getTag());

        String venue = es.venue;
        String latlang = getLatLangString(venue);

        Intent intentMap = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?mode=walking&daddr=" + latlang));
        startActivity(intentMap);
    }

    public void onSectionAttached(String title, int actionbar_color) {
        mTitle = title;
        mActionBarColor = actionbar_color;

        restoreActionBar();
    }

    public void restoreActionBar() {

        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) return;

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
     //   actionBar.setDrawerIndicatorEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setTitle(mTitle);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(mActionBarColor)));
    }

    @Override
    public void onBackPressed() {
        boolean showMap = false;

        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else {
            fragStack.pop();

            if (fragStack.empty())
                super.onBackPressed();
            else {
                Fragment currFrag = fragStack.peek();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, currFrag)
                        .commit();
                if (currFrag instanceof EventFragment) {
                    if (((EventFragment) currFrag).getTitle().equals("Home"))
                        showMap = true;
                    onSectionAttached(((EventFragment) currFrag).getTitle(), ((EventFragment) currFrag).getActionBarColor());
                } else if (currFrag instanceof SupportMapFragment) {
                    ((SupportMapFragment) currFrag).getMapAsync(this);

                    onSectionAttached("Map", R.color.actionbar_home);
                }

                if (showMap)
                    getSupportFragmentManager().beginTransaction().show(mapFrag).commit();
                else if (mapFrag.isVisible())
                    getSupportFragmentManager().beginTransaction().hide(mapFrag).commit();
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        for (EventSummary es : events) {

            map.addMarker(new MarkerOptions()
                    .position(getLatLng(es.venue))
                    .title(es.title)
                    .icon(BitmapDescriptorFactory.fromResource(getSuperIcon(es.actionbar_color))));
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Add Utility Icons Here

        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            } else {
                Toast.makeText(MainActivity.this, "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                }
            }

            if (map != null) {


                final GoogleMap finalMap = map;
                map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                    @Override
                    public void onMyLocationChange(Location arg0) {
                        // TODO Auto-generated method stub

                        finalMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.1317629,82.1421549),15));
                    }
                });

            }
        }

    }

    public int getSuperIcon(int abcolor){
        //28498a
        switch(abcolor){
            case R.color.actionbar_lectures:
                return R.drawable.lectures_map;
            case R.color.actionbar_exhibitions:
                return R.drawable.exhibition_map ;
            case R.color.actionbar_technoholix:
                return R.drawable.technoholix_map ;
            case R.color.actionbar_initiatives:
                return R.drawable.initiative_map ;
            case R.color.actionbar_conference:
                return R.drawable.conference_map ;
            case R.color.actionbar_competitions:
                return R.drawable.competitions_map;
            case R.color.actionbar_ozone:
                return R.drawable.ozone_map;
            case R.color.actionbar_workshops:
                return R.drawable.workshops_map ;
            case R.color.actionbar_ideate:
                return R.drawable.ideate_map;
            default:
                return R.drawable.icon_wo_bg_1;
        }
    }

    public void fblink(View v){
        String name = (String)v.getTag();

        String url = "fb://profile/";
        try{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url+name));
            startActivity(i);
        } catch (Exception e){
            url = "http://www.facebook.com/";

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url+name));
            startActivity(i);
        }
    }

    public void launchMarket(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id="+v.getTag()));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+v.getTag()));
            startActivity(intent);
        }
    }

}
