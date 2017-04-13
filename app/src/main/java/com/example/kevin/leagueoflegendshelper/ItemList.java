package com.example.kevin.leagueoflegendshelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 4/12/2017.
 */

public class ItemList {
    public static List<Map<String, ?>> itemList = new ArrayList<Map<String, ?>>();

    public static List<Map<String, ?>> getList() {
        return itemList;
    }

    public static int getSize() {
        return itemList.size();
    }

    public static Map<String, ?> getItem(int x) {
        return itemList.get(x);
    }

    public static int getIndexOf(String itemID) {
        int index = -1;

        for (int x = 0; x < itemList.size(); x++) {
            String id = (String) itemList.get(x).get("id");
            if (id.equals(itemID)) {
                index = x;
                break;
            }
        }
        return index;
    }


}
