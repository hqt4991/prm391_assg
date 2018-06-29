package se04869.vn.edu.fpt.prm391_assg;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import se04869.vn.edu.fpt.prm391_assg.Model.Article;

import static se04869.vn.edu.fpt.prm391_assg.AppConfig.URLHEADLINES;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    ListView lsvHeadlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lsvHeadlines = findViewById(R.id.lsvHeadlines);

        lsvHeadlines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article entry = (Article) parent.getItemAtPosition(position);
                Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
                webIntent.putExtra("url", entry.getUrl());
                startActivity(webIntent);
            }
        });

        new MainActivity.getHeadlines().execute();
    }

    private class getHeadlines extends AsyncTask<Void, Void, ArrayList<Article>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Article> doInBackground(Void... voids) {

            HttpHandler handler = new HttpHandler();
            String jsonStr = handler.requestJson(URLHEADLINES);
            ArrayList<Article> toReturn = new ArrayList<>();
            Article ar;
            Log.i(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject in = new JSONObject(jsonStr);
                    JSONArray articlesJSONArray = in.getJSONArray("articles");

                    for (int i = 0; i < articlesJSONArray.length(); i++){

                        ar = new Article();
                        JSONObject articleJSONObject = articlesJSONArray.getJSONObject(i);
                        JSONObject sourceJSONObject = articleJSONObject.getJSONObject("source");

                        ar.setSource(sourceJSONObject.getString("name"));
                        ar.setAuthor(articleJSONObject.getString("author"));
                        ar.setDescription(articleJSONObject.getString("description"));
                        ar.setTitle(articleJSONObject.getString("title"));
                        ar.setUrl(articleJSONObject.getString("url"));
                        ar.setImageUrl(articleJSONObject.getString("urlToImage"));

                        toReturn.add(ar);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return toReturn;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articles) {
            super.onPostExecute(articles);

            ArrayList<HashMap<String, String>> articlesHashList = new ArrayList<>();
            HashMap<String, String> articleHash;

            for (Article ar : articles) {
                articleHash = new HashMap<>();
                articleHash.put("title", ar.getTitle());
                articleHash.put("source", ar.getSource());

                articlesHashList.add(articleHash);
            }

            MyAdapter adapter = new MyAdapter(MainActivity.this, articles);
            lsvHeadlines.setAdapter(adapter);

        }
    }

}
