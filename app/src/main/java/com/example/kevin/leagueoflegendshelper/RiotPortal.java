package com.example.kevin.leagueoflegendshelper;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

/**
 * Created by Kevin on 4/11/2017.
 */

public class RiotPortal {

    private static String DDragonVer = "3.6.14";

    private static final String APIKey1 = "RGAPI-3dfd8f5c-e0da-4938-a357-be7e8fae77ee";
    private static final String APIKey2 = "RGAPI-3a3ee533-86cd-4a16-b7ad-820384e4f594";

    private static final String ItemURL = "https://global.api.riotgames.com/api/lol/static-data/NA/v1.2/item?itemListData=from,gold,image,into,sanitizedDescription&api_key=";
    public static String ItemImageURL1 = "http://ddragon.leagueoflegends.com/cdn/";
    public static String ItemImageURL2 = "/img/item/";

    public static final String ChampionURL = "https://global.api.riotgames.com/api/lol/static-data/NA/v1.2/champion?champData=image&dataById=true&api_key=";
    public static String ChampionImageURL1 = "http://ddragon.leagueoflegends.com/cdn/";
    public static String ChampionImageURL2 = "/img/champion/";

    public static final String InvididualChampionURL1 = "https://global.api.riotgames.com/api/lol/static-data/NA/v1.2/champion/";
    public static final String IndividualChampionURL2 = "?champData=image,lore,passive,spells&api_key=";

    public static final String AbilityURL1 = "http://ddragon.leagueoflegends.com/cdn/";
    public static final String AbilityURL2 = "/img/";

    public static final String LatestVersionURL = "https://global.api.riotgames.com/api/lol/static-data/NA/v1.2/versions?api_key=";


    public final static String getItemImageURL() {
        return ItemImageURL1 + DDragonVer + ItemImageURL2;
    }

    public final static String getChampImageURL() {
        return ChampionImageURL1 + DDragonVer + ChampionImageURL2;
    }

    public final static String getIndividualChampURL(String id) {
        return InvididualChampionURL1 + id + IndividualChampionURL2;
    }

    public final static String getAbilityImageURL() {
        return AbilityURL1 + DDragonVer + AbilityURL2;
    }

    public final static class DownloadAllItems extends AsyncTask<String, Void,  List<Map<String, ?>>> {

        private WeakReference<ItemRVAdapter> adapterReference;
        private WeakReference<List<Map<String, ?>>> itemListReference;

        public DownloadAllItems(ItemRVAdapter adapter, List<Map<String, ?>> itemList) {
            adapterReference = new WeakReference<ItemRVAdapter>(adapter);
            itemListReference = new WeakReference<List<Map<String, ?>>>(itemList);
        }

        @Override
        protected List<Map<String, ?>> doInBackground(String... params) {
            String versionJSON = MyUtility.downloadJSONusingHTTPGetRequest(LatestVersionURL+APIKey1);

            String returnJSON = MyUtility.downloadJSONusingHTTPGetRequest(ItemURL+APIKey1);

            List<Map<String, ?>> downloadedList = new ArrayList<Map<String, ?>>();

            try {
                JSONArray jsonVersion = new JSONArray(versionJSON);
                DDragonVer = jsonVersion.getString(0);


                JSONObject jsonObject = new JSONObject(returnJSON);

                JSONObject itemData = jsonObject.getJSONObject("data");

                for (int x = 0; x < itemData.names().length(); x++) {
                    HashMap itemHash = new HashMap();
                    if (itemData.names().getString(x).equals("3632")
                            || itemData.names().getString(x).equals("3637")
                            || itemData.names().getString(x).equals("3642")
                            || itemData.names().getString(x).equals("3648")
                            || itemData.names().getString(x).equals("3461")
                            || itemData.names().getString(x).equals("3671")
                            || itemData.names().getString(x).equals("3672")
                            || itemData.names().getString(x).equals("3673")
                            || itemData.names().getString(x).equals("3675")
                            || itemData.names().getString(x).equals("3422")
                            || itemData.names().getString(x).equals("3416")
                            || itemData.names().getString(x).equals("3633")) {
                        continue;
                    }
                    JSONObject currItem = itemData.getJSONObject(itemData.names().getString(x));

                    String id = currItem.getString("id");
                    //Log.d("test", id);
                    String name = currItem.getString("name");
                    String description = "";
                    ArrayList<String> fromArray = new ArrayList<String>();
                    ArrayList<String> intoArray = new ArrayList<String>();
                    String imageLink = "";
                    String combineGold = "";
                    String totalGold = "";


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


                    if (currItem.has("description")) {
                        description = currItem.getString("description");
                        description = description.replace("<br>", System.getProperty("line.separator"));
                        description = description.replaceAll("<.*?>", "");
                    }

                    if (currItem.has("image")) {
                        JSONObject imageObject = currItem.getJSONObject("image");
                        imageLink = imageObject.getString("full");

                        //image = MyUtility.downloadImageusingHTTPGetRequest(ItemImageURL+imageLink);

                    }



                    if (currItem.has("gold")) {
                        JSONObject goldObject = currItem.getJSONObject("gold");
                        totalGold = goldObject.getString("total");
                        combineGold = goldObject.getString("base");
                    }


                    itemHash.put("id", id);
                    itemHash.put("name", name);
                    itemHash.put("description", description);
                    itemHash.put("imageLink", imageLink);
                    //itemHash.put("image", image);
                    itemHash.put("totalGold", totalGold);
                    itemHash.put("combineGold", combineGold);
                    itemHash.put("from", fromArray);
                    itemHash.put("into", intoArray);

                    downloadedList.add(itemHash);

                    //List<Map<String, ?>> refList = itemListReference.get();
                    //refList.add((HashMap)itemHash.clone());
                    //adapterReference.get().notifyItemInserted(refList.size()-1);


                    Log.d("test", "ID: " + id);
                    //Log.d("test", "Name: " + name);
                    //Log.d("test", "Total Gold: " + totalGold);
                    //Log.d("test", "ImageLink: " + imageLink);


                }
                //Log.d("test", "Largest of \"from\" items " + Collections.max(test));
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

            Collections.sort(refList, new ItemComparator());

            adapterReference.get().notifyDataSetChanged();
        }
    }

    public final static class DownloadAllChampions extends AsyncTask<String, Void, List<Map<String, ?>>> {


        private WeakReference<ChampionRVAdapter> adapterReference;
        private WeakReference<List<Map<String, ?>>> champListReference;

        public DownloadAllChampions(ChampionRVAdapter adapter, List<Map<String, ?>> champList) {
            adapterReference = new WeakReference<ChampionRVAdapter>(adapter);
            champListReference = new WeakReference<List<Map<String, ?>>>(champList);
        }

        @Override
        protected List<Map<String, ?>> doInBackground(String... params) {
            String versionJSON = MyUtility.downloadJSONusingHTTPGetRequest(LatestVersionURL+APIKey1);


            String returnJSON = MyUtility.downloadJSONusingHTTPGetRequest(ChampionURL+APIKey1);

            List<Map<String, ?>> downloadedList = new ArrayList<Map<String, ?>>();

            try {
                Log.d("test", "Ver: " + DDragonVer);
                JSONArray jsonVersion = new JSONArray(versionJSON);
                DDragonVer = jsonVersion.getString(0);
                Log.d("test", "Ver: " + DDragonVer);


                JSONObject jsonObject = new JSONObject(returnJSON);

                JSONObject champData = jsonObject.getJSONObject("data");

                for (int x = 0; x < champData.names().length(); x++) {
                    HashMap champHash = new HashMap();

                    JSONObject currChamp = champData.getJSONObject(champData.names().getString(x));

                    String id = currChamp.getString("id");
                    String name = currChamp.getString("name");
                    String imageLink = "";

                    if (currChamp.has("image")) {
                        JSONObject imageObject = currChamp.getJSONObject("image");

                        imageLink = imageObject.getString("full");
                    }

                    champHash.put("id", id);
                    champHash.put("name", name);
                    champHash.put("imageLink", imageLink);

                    downloadedList.add(champHash);

                }

            }
            catch(Exception e) {
                Log.d("Exception", e.toString());
            }

            return downloadedList;
        }

        @Override
        protected void onPostExecute(List<Map<String, ?>> list) {
            List<Map<String, ?>> refList = champListReference.get();

            for (int x = 0; x < list.size(); x++) {
                HashMap item = (HashMap) ((HashMap) list.get(x)).clone();
                refList.add(item);
            }
            Collections.sort(refList, new ChampionComparator());

            adapterReference.get().notifyDataSetChanged();
        }
    }

    public final static class DownloadChampionInformation extends AsyncTask<String, Void, List<Map<String, ?>>> {

        WeakReference<List<Map<String, ?>>> champInfoRef;
        WeakReference<FragmentPagerAdapter> adapterRef;

        public DownloadChampionInformation(FragmentPagerAdapter adapter, List<Map<String, ?>> champInfo) {
            adapterRef = new WeakReference<FragmentPagerAdapter>(adapter);
            champInfoRef = new WeakReference<List<Map<String, ?>>>(champInfo);
        }

        @Override
        protected List<Map<String, ?>> doInBackground(String... champID) {
            List<Map<String, ?>> downloadedInfo = new ArrayList<Map<String, ?>>();

            String returnJSON = MyUtility.downloadJSONusingHTTPGetRequest(getIndividualChampURL(champID[0]) + APIKey1);

            try {
                HashMap champ = new HashMap<>();
                HashMap passive = new HashMap<>();
                HashMap abilQ = new HashMap<>();
                HashMap abilW = new HashMap<>();
                HashMap abilE = new HashMap<>();
                HashMap abilR = new HashMap<>();

                JSONObject jsonObject = new JSONObject(returnJSON);
                JSONObject imageJSON = jsonObject.getJSONObject("image");

                String description;

                champ.put("name", jsonObject.getString("name"));
                champ.put("title", jsonObject.getString("title"));
                champ.put("imageLink", imageJSON.getString("full"));
                description = jsonObject.getString("lore").toString();
                description = description.replace("<br>", System.getProperty("line.separator"));
                description = description.replaceAll("<.*?>", "");
                champ.put("lore", description);


                JSONObject passiveJSON = jsonObject.getJSONObject("passive");
                JSONObject passiveImageJSON = passiveJSON.getJSONObject("image");

                passive.put("name", passiveJSON.getString("name"));
                description = passiveJSON.getString("description").toString();
                description = description.replace("<br>", System.getProperty("line.separator"));
                description = description.replaceAll("<.*?>", "");
                passive.put("description", description);
                passive.put("imageLink", passiveImageJSON.getString("full"));


                JSONArray abilArray = jsonObject.getJSONArray("spells");

                JSONObject qJSON = abilArray.getJSONObject(0);
                JSONObject wJSON = abilArray.getJSONObject(1);
                JSONObject eJSON = abilArray.getJSONObject(2);
                JSONObject rJSON = abilArray.getJSONObject(3);


                JSONObject qImageJSON = qJSON.getJSONObject("image");

                abilQ.put("name", qJSON.getString("name"));
                description = qJSON.getString("description").toString();
                description = description.replace("<br>", System.getProperty("line.separator"));
                description = description.replaceAll("<.*?>", "");
                abilQ.put("description", description);
                abilQ.put("imageLink", qImageJSON.getString("full"));


                JSONObject wImageJSON = wJSON.getJSONObject("image");

                abilW.put("name", wJSON.getString("name"));
                description = wJSON.getString("description").toString();
                description = description.replace("<br>", System.getProperty("line.separator"));
                description = description.replaceAll("<.*?>", "");
                abilW.put("description", description);
                abilW.put("imageLink", wImageJSON.getString("full"));


                JSONObject eImageJSON = eJSON.getJSONObject("image");

                abilE.put("name", eJSON.getString("name"));
                description = eJSON.getString("description").toString();
                description = description.replace("<br>", System.getProperty("line.separator"));
                description = description.replaceAll("<.*?>", "");
                abilE.put("description", description);
                abilE.put("imageLink", eImageJSON.getString("full"));


                JSONObject rImageJSON = rJSON.getJSONObject("image");

                abilR.put("name", rJSON.getString("name"));
                description = rJSON.getString("description").toString();
                description = description.replace("<br>", System.getProperty("line.separator"));
                description = description.replaceAll("<.*?>", "");
                abilR.put("description", description);
                abilR.put("imageLink", rImageJSON.getString("full"));


                downloadedInfo.add(champ);
                downloadedInfo.add(passive);
                downloadedInfo.add(abilQ);
                downloadedInfo.add(abilW);
                downloadedInfo.add(abilE);
                downloadedInfo.add(abilR);

            }
            catch(Exception e) {
                Log.d("Exception", e.toString());
            }

            return downloadedInfo;
        }

        @Override
        protected void onPostExecute(List<Map<String, ?>> info) {
            List<Map<String, ?>> refList = champInfoRef.get();

            for (int x = 0; x < info.size(); x++) {
                HashMap item = (HashMap) ((HashMap) info.get(x)).clone();
                refList.add(item);
            }

            adapterRef.get().notifyDataSetChanged();
        }
    }

    private final static class ItemComparator implements Comparator<Map<String, ?>> {

        @Override
        public int compare(Map<String, ?> o1, Map<String, ?> o2) {
            String name1 = (String) o1.get("name");
            String name2 = (String) o2.get("name");

            return name1.compareTo(name2);
        }
    }

    private final static class ChampionComparator implements Comparator<Map<String, ?>> {
        @Override
        public int compare(Map<String, ?> o1, Map<String, ?> o2) {
            String name1 = (String) o1.get("name");
            String name2 = (String) o2.get("name");

            return name1.compareTo(name2);
        }
    }
    
}
