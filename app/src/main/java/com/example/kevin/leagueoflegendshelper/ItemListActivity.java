package com.example.kevin.leagueoflegendshelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
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
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Stack;

public class ItemListActivity extends AppCompatActivity
                        implements NavigationView.OnNavigationItemSelectedListener,
                            ItemRVFragment.ItemRVCardClickListener,
                            ItemDetailFragment.ItemDetailFragClick {

    private Toolbar toolBar;
    private TextView toolBarTitle;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private Stack<String> titleStack = new Stack<>();
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

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

        ItemRVFragment itemRVFrag = new ItemRVFragment();
        //itemRVFrag.setEnterTransition(new Slide(Gravity.RIGHT));
        //itemRVFrag.setExitTransition(new Slide(Gravity.LEFT));
        getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, itemRVFrag).commit();
    }

    @Override
    public void onBackPressed() {
        //Log.d("test", "Title stack: " + titleStack.toString());
        if (titleStack.size() > 0) {
            toolBarTitle.setText(titleStack.pop());
        }
        else {
            toolBarTitle.setText("Items");
        }

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
    public void itemRVClicked(View v, int position) {
        Log.d("test", "Activity: " + ItemList.getItem(position).get(("id")) + " " + ItemList.getItem(position).get("name") + " was clicked");


        titleStack.push((String) toolBarTitle.getText());



        //Log.d("test", "Title stack: " + titleStack.toString());
        HashMap<String, ?> item = (HashMap) ItemList.getItem(position);
        String itemName = (String) item.get("name");
        toolBarTitle.setText(itemName);


        ItemDetailFragment itemFrag = ItemDetailFragment.newInstance(position, "itemDetailTrans" + item.get("id"));
        itemFrag.setSharedElementEnterTransition(new DetailsTransition());
        itemFrag.setEnterTransition(new Slide(Gravity.RIGHT));
        itemFrag.setExitTransition(new Slide(Gravity.LEFT));
        itemFrag.setSharedElementReturnTransition(new DetailsTransition());

        appBarLayout.setExpanded(true);

        getSupportFragmentManager().beginTransaction()
                .addSharedElement(v, "itemDetailTrans" + item.get("id"))
                .replace(R.id.frameLayout, itemFrag)
                .addToBackStack(null).commit();
    }

    @Override
    public void itemDetailFromClicked(View v, int position) {

        titleStack.push((String) toolBarTitle.getText());
        //Log.d("test", "Title stack: " + titleStack.toString());
        HashMap<String, ?> newItem = (HashMap) ItemList.getItem(position);
        String itemName = (String) newItem.get("name");
        toolBarTitle.setText(itemName);


        ItemDetailFragment itemFrag = ItemDetailFragment.newInstance(position, v.getTransitionName()+newItem.get("id"));
        itemFrag.setSharedElementEnterTransition(new DetailsTransition());
        itemFrag.setEnterTransition(new Slide(Gravity.RIGHT));
        itemFrag.setExitTransition(new Slide(Gravity.LEFT));
        itemFrag.setSharedElementReturnTransition(new DetailsTransition());

        getSupportFragmentManager().beginTransaction()
                .addSharedElement(v, v.getTransitionName()+newItem.get("id"))
                .replace(R.id.frameLayout, itemFrag)
                .addToBackStack(null).commit();
    }
}
