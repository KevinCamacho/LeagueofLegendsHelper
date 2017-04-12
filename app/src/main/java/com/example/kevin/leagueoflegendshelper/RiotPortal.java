package com.example.kevin.leagueoflegendshelper;

import android.graphics.Bitmap;
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

    private static final String APIKey1 = "RGAPI-3dfd8f5c-e0da-4938-a357-be7e8fae77ee";
    private static final String APIKey2 = "RGAPI-3a3ee533-86cd-4a16-b7ad-820384e4f594";

    private static final String ItemURL = "https://global.api.riotgames.com/api/lol/static-data/NA/v1.2/item?itemListData=from,gold,image,into,sanitizedDescription&api_key=";
    private static final String ItemImageURL = "http://ddragon.leagueoflegends.com/cdn/7.6.1/img/item/";

    private static String DDragonVer;


    public final static class DownloadAllItems extends AsyncTask<String, Void,  List<Map<String, ?>>> {

        private WeakReference<ItemRVAdapter> adapterReference;
        private WeakReference<List<Map<String, ?>>> itemListReference;

        public DownloadAllItems(ItemRVAdapter adapter, List<Map<String, ?>> itemList) {
            adapterReference = new WeakReference<ItemRVAdapter>(adapter);
            itemListReference = new WeakReference<List<Map<String, ?>>>(itemList);
        }

        @Override
        protected List<Map<String, ?>> doInBackground(String... params) {
            String returnJSON = MyUtility.downloadJSONusingHTTPGetRequest(ItemURL+APIKey1);

            List<Map<String, ?>> downloadedList = new ArrayList<Map<String, ?>>();

            try {
                JSONObject jsonObject = new JSONObject(returnJSON);

                JSONObject itemData = jsonObject.getJSONObject("data");


                for (int x = 0; x < itemData.names().length(); x++) {
                    HashMap itemHash = new HashMap();
                    if (itemData.names().getString(x).equals("3632")
                            || itemData.names().getString(x).equals("3640")
                            || itemData.names().getString(x).equals("3630")
                            || itemData.names().getString(x).equals("3633")
                            || itemData.names().getString(x).equals("3637")
                            || itemData.names().getString(x).equals("3634")
                            || itemData.names().getString(x).equals("3635")
                            || itemData.names().getString(x).equals("3631")
                            || itemData.names().getString(x).equals("3642")
                            || itemData.names().getString(x).equals("3648")
                            || itemData.names().getString(x).equals("3643")
                            || itemData.names().getString(x).equals("3680")
                            || itemData.names().getString(x).equals("3681")
                            || itemData.names().getString(x).equals("3682")
                            || itemData.names().getString(x).equals("3683")
                            || itemData.names().getString(x).equals("3461")
                            || itemData.names().getString(x).equals("3460")
                            || itemData.names().getString(x).equals("3902")
                            || itemData.names().getString(x).equals("3901")
                            || itemData.names().getString(x).equals("3647")) {
                        continue;
                    }
                    JSONObject currItem = itemData.getJSONObject(itemData.names().getString(x));

                    String id = currItem.getString("id");
                    String name = currItem.getString("name");
                    String description = "";
                    ArrayList<String> fromArray = new ArrayList<String>();
                    ArrayList<String> intoArray = new ArrayList<String>();
                    String imageLink = "";
                    String gold = "";
                    Bitmap image = null;


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

                    if (currItem.has("image")) {
                        JSONObject imageObject = currItem.getJSONObject("image");
                        imageLink = imageObject.getString("full");

                        image = MyUtility.downloadImageusingHTTPGetRequest(ItemImageURL+imageLink);

                    }



                    if (currItem.has("gold")) {
                        JSONObject goldObject = currItem.getJSONObject("gold");
                        gold = goldObject.getString("total");
                    }


                    itemHash.put("id", id);
                    itemHash.put("name", name);
                    itemHash.put("description", description);
                    itemHash.put("imageLink", imageLink);
                    itemHash.put("image", image);
                    itemHash.put("gold", gold);
                    itemHash.put("from", fromArray);
                    itemHash.put("into", intoArray);

                    downloadedList.add(itemHash);

                    Log.d("test", "ID: " + id);
                    Log.d("test", "Name: " + name);
                    Log.d("test", "Gold: " + gold);
                    Log.d("test", "ImageLink: " + imageLink);


                }

            }
            catch(Exception e) {
                Log.d("Exception", e.toString());
            }
            return downloadedList;
        }

        @Override
        protected void onPostExecute(List<Map<String, ?>> list) {
            List<Map<String, ?>> refList = itemListReference.get();

            for (int x = 0; x < list.size(); x++) {
                HashMap item = (HashMap) ((HashMap) list.get(x)).clone();
                refList.add(item);
            }

            adapterReference.get().notifyDataSetChanged();
        }
    }
    
}