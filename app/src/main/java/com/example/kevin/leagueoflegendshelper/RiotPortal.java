package com.example.kevin.leagueoflegendshelper;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

    public static final String GetSummonerID1 = "https://na1.api.riotgames.com/lol/summoner/v3/summoners/by-name/";
    public static final String GetSummonerID2 = "?api_key=";

    public static final String LatestVersionURL = "https://global.api.riotgames.com/api/lol/static-data/NA/v1.2/versions?api_key=";

    public static final String GetRecentMatches1 = "https://na.api.riotgames.com/api/lol/NA/v1.3/game/by-summoner/";
    public static final String GetRecentMatches2 = "/recent?api_key=";

    public static final String GetChampNameByID1 = "https://global.api.riotgames.com/api/lol/static-data/NA/v1.2/champion/";
    public static final String GetChampNameByID2 = "?champData=image&api_key=";

    public static final String GetProfileIcon1 = "http://ddragon.leagueoflegends.com/cdn/";
    public static final String GetProfileIcon2 =  "/img/profileicon/";

    public static final String GetLeagues1 = "https://na.api.riotgames.com/api/lol/NA/v2.5/league/by-summoner/";
    public static final String GetLeagues2 = "?api_key=";


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

    public final static String getSummonerIDURL(String name) {
        return GetSummonerID1 + name + GetSummonerID2;
    }

    public final static String getRecentMatches(String id) {
        return GetRecentMatches1 + id + GetRecentMatches2;
    }

    public final static String getProfileIconURL(String id) {
        return GetProfileIcon1 + DDragonVer + GetProfileIcon2 + id + ".png";
    }

    public final static String getLeaguesURL(String id) {
        return GetLeagues1 + id + GetLeagues2;
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


                    //Log.d("test", "ID: " + id);
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

    public final static class GetSummonerID extends AsyncTask<String, Void, HashMap<String, String>> {

        final GetSummonerIDListener getSummonerIDListener;

        public interface GetSummonerIDListener {
            public void searchFinished(String id, String profileIcon, String level);
        }

        public GetSummonerID(GetSummonerIDListener listener) {
            this.getSummonerIDListener = listener;
        }

        @Override
        protected HashMap<String, String> doInBackground(String... name) {
            String returnJSON = MyUtility.downloadJSONusingHTTPGetRequest(getSummonerIDURL(name[0]) + APIKey1);

            HashMap hash = new HashMap();

            String id = "";
            String profileIconID = "";
            String level = "";

            try {
                JSONObject jsonObject = new JSONObject(returnJSON);
                id = jsonObject.getString("id");
                profileIconID = jsonObject.getString("profileIconId");
                level = jsonObject.getString("summonerLevel");

                hash.put("accountID", id);
                hash.put("profileIconID", profileIconID);
                hash.put("summonerLevel", level);
            }
            catch(Exception e) {
                Log.d("Exception", e.toString());
            }

            return hash;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> hash) {
            if (getSummonerIDListener != null) {
                getSummonerIDListener.searchFinished(hash.get("accountID"), hash.get("profileIconID"), hash.get("summonerLevel"));
            }
        }
    }

    public final static class GetRecentMatches extends AsyncTask<String, HashMap, MatchList> {

        private WeakReference<MatchList> matchListRef;
        private WeakReference<RecyclerView.Adapter> adapterRef;

        private test t;

        public interface test {
            public void doneDownloading();
        }

        public GetRecentMatches(MatchList mL, RecyclerView.Adapter adapter, test t) {
            matchListRef = new WeakReference<MatchList>(mL);
            adapterRef = new WeakReference<RecyclerView.Adapter>(adapter);
            this.t = t;
        }

        @Override
        protected MatchList doInBackground(String... id) {
            String versionJSON = MyUtility.downloadJSONusingHTTPGetRequest(LatestVersionURL+APIKey1);

            MatchList matchList = new MatchList();

            String returnJSON = MyUtility.downloadJSONusingHTTPGetRequest(getRecentMatches(id[0]) + APIKey1);

            try {
                JSONArray jsonVersion = new JSONArray(versionJSON);
                DDragonVer = jsonVersion.getString(0);

                JSONObject jsonObject = new JSONObject(returnJSON);

                JSONArray gamesArray = jsonObject.getJSONArray("games");

                for (int x = 0; x < gamesArray.length(); x++) {
                    JSONObject gameObject = gamesArray.getJSONObject(x);

                    HashMap currGame = new HashMap();

                    JSONObject gameStatsObject;

                    String win = "-1";
                    String championID = "";
                    String imageLink = "";
                    String item1ID = "-1";
                    String item2ID = "-1";
                    String item3ID = "-1";
                    String item4ID = "-1";
                    String item5ID = "-1";
                    String item6ID = "-1";
                    String trinketID = "-1";
                    String numKills = "0";
                    String numDeaths = "0";
                    String numAssists = "0";

                    //Log.d("test", gameObject.names().toString());

                    gameStatsObject = gameObject.getJSONObject("stats");
                    win = gameStatsObject.getString("win");

                    if (gameStatsObject.has("item0")) {
                        item1ID = gameStatsObject.getString("item0");
                    }
                    if (gameStatsObject.has("item1")) {
                        item2ID = gameStatsObject.getString("item1");
                    }
                    if (gameStatsObject.has("item2")) {
                        item3ID = gameStatsObject.getString("item2");
                    }
                    if (gameStatsObject.has("item3")) {
                        item4ID = gameStatsObject.getString("item3");
                    }
                    if (gameStatsObject.has("item4")) {
                        item5ID = gameStatsObject.getString("item4");
                    }
                    if (gameStatsObject.has("item5")) {
                        item6ID = gameStatsObject.getString("item5");
                    }
                    if (gameStatsObject.has("item6")) {
                        trinketID = gameStatsObject.getString("item6");
                    }

                    if (gameStatsObject.has("championsKilled")) {
                        numKills = gameStatsObject.getString("championsKilled");
                    }

                    if (gameStatsObject.has("numDeaths")) {
                        numDeaths = gameStatsObject.getString("numDeaths");
                    }

                    if (gameStatsObject.has("assists")) {
                        numAssists = gameStatsObject.getString("assists");
                    }

                    championID = gameObject.getString("championId");

                    String champJSON = MyUtility.downloadJSONusingHTTPGetRequest(GetChampNameByID1 + championID + GetChampNameByID2 + APIKey1);
                    JSONObject champObject = new JSONObject(champJSON);
                    JSONObject imageObject = champObject.getJSONObject("image");

                    imageLink = imageObject.getString("full");

                    currGame.put("win", win);
                    currGame.put("championID", championID);
                    currGame.put("imageLink", imageLink);
                    currGame.put("kills", numKills);
                    currGame.put("deaths", numDeaths);
                    currGame.put("assists", numAssists);
                    currGame.put("item1ID", item1ID);
                    currGame.put("item2ID", item2ID);
                    currGame.put("item3ID", item3ID);
                    currGame.put("item4ID", item4ID);
                    currGame.put("item5ID", item5ID);
                    currGame.put("item6ID", item6ID);
                    currGame.put("trinketID", trinketID);

                    //matchList.getList().add(currGame);
                    publishProgress(currGame);

                }


            }
            catch (Exception e) {
                Log.d("Exception", e.toString());
            }

            return matchList;
        }

        @Override
        protected void onProgressUpdate(HashMap... values) {
            HashMap item = values[0];
            matchListRef.get().add(item);
            adapterRef.get().notifyItemInserted(matchListRef.get().getSize()-1);
        }

        @Override
        protected void onPostExecute(MatchList list) {

            t.doneDownloading();

            /*MatchList mL = matchListRef.get();


            for (int x = 0; x < list.getSize(); x++) {
                HashMap item = (HashMap) ((HashMap) list.getItem(x)).clone();
                mL.add(item);
            }*/
            //t.test();
            //adapterRef.get().notifyDataSetChanged();

        }
    }

    public final static class GetLeaguesInfo extends AsyncTask<String, Void, String> {
        WeakReference<TextView> rankRef;
        WeakReference<ImageView> rankImageRef;

        public GetLeaguesInfo(TextView rank, ImageView rankImage) {
            rankRef = new WeakReference<TextView>(rank);
            rankImageRef = new WeakReference<ImageView>(rankImage);
        }


        @Override
        protected String doInBackground(String... id) {

            String returnJSON = MyUtility.downloadJSONusingHTTPGetRequest(getLeaguesURL(id[0]) + APIKey1);
            String league = "";
            try {
                JSONObject jsonObject = new JSONObject(returnJSON);

                JSONArray mainArray = jsonObject.getJSONArray(id[0]);

                JSONObject leagueObj = mainArray.getJSONObject(0);

                league = leagueObj.getString("tier");

                JSONArray entriesObj = leagueObj.getJSONArray("entries");

                for (int x = 0; x < entriesObj.length(); x++) {
                    JSONObject obj = entriesObj.getJSONObject(x);

                    if (obj.getString("playerOrTeamId").equals(id[0])) {
                        league += " " + obj.getString("division");
                    }
                }

            }
            catch(Exception e) {
                Log.d("Exception", e.toString());
                league = "UNRANKED";
            }

            return league;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.contains("BRONZE"))
                rankImageRef.get().setImageResource(R.drawable.bronze);
            else if (s.contains("SILVER"))
                rankImageRef.get().setImageResource(R.drawable.silver);
            else if (s.contains("GOLD"))
                rankImageRef.get().setImageResource(R.drawable.gold);
            else if (s.contains("PLATINUM"))
                rankImageRef.get().setImageResource(R.drawable.platinum);
            else if (s.contains("DIAMOND"))
                rankImageRef.get().setImageResource(R.drawable.platinum);
            else if (s.contains("MASTER"))
                rankImageRef.get().setImageResource(R.drawable.master);
            else if (s.contains("CHALLENGER"))
                rankImageRef.get().setImageResource(R.drawable.challenger);
            else
                rankImageRef.get().setImageResource(R.drawable.provisional);
            rankRef.get().setText(s);
            super.onPostExecute(s);
        }
    }

    public final static class UpdateVersion extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String versionJSON = MyUtility.downloadJSONusingHTTPGetRequest(LatestVersionURL+APIKey1);

            try {
                JSONArray jsonVersion = new JSONArray(versionJSON);
                return jsonVersion.getString(0);
            }
            catch(Exception e) {
                Log.d("Exception", e.toString());
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            DDragonVer = s;
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
