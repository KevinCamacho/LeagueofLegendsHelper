package com.example.kevin.leagueoflegendshelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 4/24/2017.
 */

public class MatchList {

    private List<Map<String, ?>> matchList;

    public MatchList() {
        matchList = new ArrayList<Map<String, ?>>();
    }

    public List<Map<String, ?>> getList() {
        return matchList;
    }

    public int getSize() {
        return matchList.size();
    }

    public Map<String, ?> getItem(int x) {
        return matchList.get(x);
    }

    public void add(Map<String, ?> item) {
        matchList.add(item);
    }

    public int getIndexOf(String itemID) {
        int index = -1;

        for (int x = 0; x < matchList.size(); x++) {
            String id = (String) matchList.get(x).get("id");
            if (id.equals(itemID)) {
                index = x;
                break;
            }
        }
        return index;
    }
}
