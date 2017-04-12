package com.example.kevin.leagueoflegendshelper;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

/**
 * Created by Kevin on 4/11/2017.
 */

public class RiotPortal {

    private static String APIKey1 = "RGAPI-8f158ea6-05a6-4765-8a5a-cfada6c5345e";
    private static String APIKey2 = "RGAPI-3a3ee533-86cd-4a16-b7ad-820384e4f594";

    private static String ItemURL = "https://global.api.riotgames.com/api/lol/static-data/NA/v1.2/item?itemListData=from,gold,image,into,sanitizedDescription&api_key=";

    private static String DDragonVer;


    public final static class DownloadAllItems extends AsyncTask<String, Void,  ArrayList<HashMap<String, ?>>> {

        private WeakReference<ItemRVAdapter> adapterReference;
        private WeakReference<ArrayList<HashMap<String, ?>>> itemListReference;

        public DownloadAllItems(ItemRVAdapter adapter, ArrayList<HashMap<String, ?>> itemList) {
            adapterReference = new WeakReference<ItemRVAdapter>(adapter);
            itemListReference = new WeakReference<ArrayList<HashMap<String, ?>>>(itemList);
        }

        @Override
        protected ArrayList<HashMap<String, ?>> doInBackground(String... params) {
            String returnJSON = MyUtility.downloadJSONusingHTTPGetRequest(ItemURL+APIKey1);

            ArrayList<HashMap<String, ?>> downloadedList = new ArrayList<HashMap<String, ?>>();

            try {
                JSONObject jsonObject = new JSONObject(returnJSON);

                JSONObject itemData = jsonObject.getJSONObject("data");


                for (int x = 0; x < itemData.names().length(); x++) {
                    HashMap itemHash = new HashMap();
                    if (itemData.names().getString(x).equals("3632")) {
                        continue;
                    }
                    JSONObject currItem = itemData.getJSONObject(itemData.names().getString(x));

                    String id = currItem.getString("id");
                    String name = currItem.getString("name");
                    String description = "";
                    ArrayList<String> fromArray = new ArrayList<String>();
                    ArrayList<String> intoArray = new ArrayList<String>();


                    if (currItem.has("from")) {
                        JSONArray from = currItem.getJSONArray("from");
                        for (int y = 0; y < from.length(); y++) {
                            fromArray.add(from.getString(y));
                        }
                    }




                    if (currItem.has("into")) {
                        JSONArray into = currItem.getJSONArray("into");
                        for (int y = 0; y < into.length(); y++) {
                            intoArray.add(into.getString(y));
                        }
                    }


                    if (currItem.has("sanitizedDescription")) {
                        description = currItem.getString("sanitizedDescription");
                    }




                    JSONObject goldObject = currItem.getJSONObject("gold");
                    String gold = goldObject.getString("total");


                    itemHash.put("id", id);
                    itemHash.put("name", name);
                    itemHash.put("description", description);
                    itemHash.put("gold", gold);
                    itemHash.put("from", fromArray);
                    itemHash.put("into", intoArray);

                    downloadedList.add(itemHash);

                    Log.d("test", "ID: " + id);
                    Log.d("test", "Name: " + name);
                    Log.d("test", "Gold: " + gold);


                }

            }
            catch(Exception e) {
                Log.d("Exception", e.toString());
            }
            return downloadedList;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, ?>> list) {
            ArrayList<HashMap<String, ?>> refList = itemListReference.get();

            refList = (ArrayList<HashMap<String, ?>>) list.clone();
        }
    }
    
}
