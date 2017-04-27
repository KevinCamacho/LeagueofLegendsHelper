package com.example.kevin.leagueoflegendshelper;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kevin on 4/18/2017.
 */

public class ChampionList {
    public static List<Map<String, ?>> champList = new ArrayList<Map<String, ?>>();

    private static List<Map<String, ?>> copyList = new ArrayList<Map<String, ?>>();

    public static List<Map<String, ?>> getList() {
        return champList;
    }

    public static int getSize() {
        return champList.size();
    }

    public static Map<String, ?> getItem(int x) {
        return champList.get(x);
    }

    public static int getIndexOf(String itemID) {
        int index = -1;

        for (int x = 0; x < champList.size(); x++) {
            String id = (String) champList.get(x).get("id");
            if (id.equals(itemID)) {
                index = x;
                break;
            }
        }
        return index;
    }

    public static int findMatching(String name) {
        int index = -1;

        name.toLowerCase();


        //Log.d("test", "Size of Champlist " + getSize());

        for (int x = 0; x < getSize(); x ++) {


            HashMap curr = (HashMap) getItem(x);
            copyList.add((HashMap) curr.clone());

            String currName = getItem(x).get("name").toString();
            currName = currName.toLowerCase();
            currName = currName.replaceAll("'", "");

            //Log.d("test", "Copmaring " + currName + " with " + name);

            String pat = "(.*)" + name + "(.*)";
            Pattern pattern = Pattern.compile(pat);

            Matcher matcher = pattern.matcher(currName);

            //Log.d("test", matcher.matches() + "\t" + currName);

            if (matcher.matches()) {
                Log.d("test", "Keeping " + currName);
            }
            else {
                //Log.d("test", "Removing" + currName);
                getList().remove(x);
                //Log.d("test", "Size of ItemList " + getSize());

                x--;
                index++;
            }
        }


        //Log.d("test", "Size of copyList " + copyList.size() + "\n" + " size of itemlist " + getSize());

        return index;
    }

    public static void restoreList() {
        //Log.d("test", "Before restore: Champlist size " + getSize() + " copylist size " + copyList.size());

        if (copyList.isEmpty())
            return;


        getList().clear();

        for (Map<String, ?> item : copyList) {
            getList().add(item);
        }

        copyList.clear();
        //Log.d("test", "After restore: Champlist size " + getSize() + " copylist size " + copyList.size());

    }
}
