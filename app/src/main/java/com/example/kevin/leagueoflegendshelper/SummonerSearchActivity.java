package com.example.kevin.leagueoflegendshelper;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class SummonerSearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                         SearchView.OnQueryTextListener,
                                                            RiotPortal.GetSummonerID.GetSummonerIDListener {

    private Toolbar toolBar;
    private TextView toolBarTitle;
    private DrawerLayout drawerLayout;

    private FrameLayout frameLayout;

    private TextView searchLabel;

    private MenuItem searchMenuItem;
    private SearchView searchView;

    private String searchedForSum;
    private String searchedForSumID;
    private String searchedForSumProfileIconID;
    private String searchedForSumLevel;

    private SummonerSearchedFragment sumFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_search);

        RiotPortal.UpdateVersion dl = new RiotPortal.UpdateVersion();
        dl.execute("");

        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        searchLabel = (TextView) findViewById(R.id.searchLabel);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        toolBarTitle = (TextView) findViewById(R.id.toolBar_Title);
        toolBarTitle.setText("Summoner Search");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_Layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar,
                                                R.string.navigation_drawer_open,
                                                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_View);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        if (searchMenuItem != null) {
            searchMenuItem.collapseActionView();
        }
        /*if (sumFrag != null) {
            getSupportFragmentManager().beginTransaction().remove(sumFrag).commit();
        }
        searchLabel.setText("Search for a summoner to begin");
        toolBarTitle.setText("Summoner Search");*/
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_button_menu, menu);

        searchMenuItem = menu.findItem(R.id.action_Search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setOnQueryTextListener(this);

        /*final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_Search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/

//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_Search));
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.d("CLICK", "Search Action collapsed");
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d("CLICK", "Search Action expanded");
                return true;
            }
        };

        //MenuItemCompat.setOnActionExpandListener(searchMenuItem, expandListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Search:

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

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
                break;
            case R.id.nav_itemList:
                startActivity(new Intent(SummonerSearchActivity.this, ItemListActivity.class));
                break;
            case R.id.nav_champList:
                startActivity(new Intent(SummonerSearchActivity.this, ChampionListActivity.class));
                break;
            default:
                break;
        }


        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchedForSum = query;
        RiotPortal.GetSummonerID downloader = new RiotPortal.GetSummonerID(this);
        downloader.execute(query);

        View v = this.getWindow().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void searchFinished(String id, String profileID, String level) {
        searchedForSumID = id;
        searchedForSumProfileIconID = profileID;
        searchedForSumLevel = level;

        Log.d("test", "Summoner ID for " + searchedForSum + " = " + searchedForSumID);

        sumFrag = SummonerSearchedFragment
                .newInstance(searchedForSum, searchedForSumID, searchedForSumProfileIconID, searchedForSumLevel);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, sumFrag).commit();
        searchLabel.setText("");
        searchMenuItem.collapseActionView();
        toolBarTitle.setText(searchedForSum);
    }
}
