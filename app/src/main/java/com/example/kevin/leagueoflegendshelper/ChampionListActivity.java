package com.example.kevin.leagueoflegendshelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.transition.Fade;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class ChampionListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                                ChampionRVFragment.ChampRVCardClickedListener {

    private Toolbar toolBar;
    private TextView toolBarTitle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_champion_list);

        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolBarTitle = (TextView) findViewById(R.id.toolBar_Title);
        toolBarTitle.setText("Champions");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_Layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_View);
        navigationView.setNavigationItemSelectedListener(this);

        ChampionRVFragment champRVFrag = new ChampionRVFragment();
        champRVFrag.setEnterTransition(new Slide(Gravity.RIGHT));
        champRVFrag.setExitTransition(new Slide(Gravity.LEFT));
        //champRVFrag.setEnterTransition(new Slide(Gravity.LEFT));
        //champRVFrag.setExitTransition(new Slide(Gravity.RIGHT));
        //champRVFrag.setEnterTransition(new Fade(Fade.IN));
        //champRVFrag.setExitTransition(new Fade(Fade.OUT));
        getSupportFragmentManager().beginTransaction()//.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frameLayout, champRVFrag).commit();
    }

    @Override
    public void onBackPressed() {
        toolBarTitle.setText("Champions");
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_sumSearch:
                startActivity(new Intent(ChampionListActivity.this, SummonerSearchActivity.class));
                break;
            case R.id.nav_itemList:
                startActivity(new Intent(ChampionListActivity.this, ItemListActivity.class));
                break;
            case R.id.nav_champList:
                break;
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void champRVClicked(View v, int position) {

        String name = (String) ((HashMap) ChampionList.getItem(position)).get("name");

        toolBarTitle.setText(name);


        ChampionDetailFragment champFrag = ChampionDetailFragment.newInstance(position);
        //champFrag.setEnterTransition(new Slide(Gravity.RIGHT));
        //champFrag.setExitTransition(new Slide(Gravity.LEFT));
        //champFrag.setEnterTransition(new Fade(Fade.IN));
        //champFrag.setExitTransition(new Fade(Fade.OUT));

        Log.d("test", "Activity: " + name + " was clicked.");
        getSupportFragmentManager().beginTransaction()//.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frameLayout, champFrag)
                .addToBackStack(null).commit();
    }
}
