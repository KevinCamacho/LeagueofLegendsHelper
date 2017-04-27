package com.example.kevin.leagueoflegendshelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 4/18/2017.
 */

public class ChampionList {
    public static List<Map<String, ?>> champList = new ArrayList<Map<String, ?>>();

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

    public static int findFirst(String name) {
        int index = -1;

        for (int x = 0; x < getSize(); x ++) {
            String currName = getItem(x).get("name").toString();
            if (currName.contains(name)) {
                index = x;
                return index;
            }
        }

        return index;
    }
}
