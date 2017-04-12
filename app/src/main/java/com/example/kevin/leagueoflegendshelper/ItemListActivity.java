package com.example.kevin.leagueoflegendshelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ItemListActivity extends AppCompatActivity
                        implements NavigationView.OnNavigationItemSelectedListener,
                            ItemRVFragment.ItemRVCardClickListener{

    private Toolbar toolBar;
    private TextView toolBarTitle;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolBarTitle = (TextView) findViewById(R.id.toolBar_Title);
        toolBarTitle.setText("Items");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_Layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_View);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new ItemRVFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        toolBarTitle.setText(("Items"));
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
                startActivity(new Intent(ItemListActivity.this, SummonerSearchActivity.class));
                break;
            case R.id.nav_itemList:
                break;
            case R.id.nav_champList:
                startActivity(new Intent(ItemListActivity.this, ChampionListActivity.class));
                break;
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void itemClicked(View v, int position) {
        Log.d("test", "Activity: " + ItemList.getItem(position).get(("name")) + " was clicked");

        toolBarTitle.setText(ItemList.getItem(position).get("name").toString());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, ItemDetailFragment.newInstance(position))
                .addToBackStack(null).commit();
    }
}
