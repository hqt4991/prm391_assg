package se04869.vn.edu.fpt.prm391_assg.Helper;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import se04869.vn.edu.fpt.prm391_assg.Model.Article;

public class HeadlineGetter extends AsyncTask<String, Void, ArrayList<Article>> {

    public AsyncResponse delegate;
    private String callingActivity;

    public interface AsyncResponse {
        void processFinish(ArrayList<Article> output);
    }

    public HeadlineGetter(AsyncResponse delegate, String callingActivity){
        this.delegate = delegate;
        this.callingActivity = callingActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Article> doInBackground(String... urls) {

        HttpHandler handler = new HttpHandler();
        String jsonStr = handler.requestJson(urls[0]);
        ArrayList<Article> toReturn = new ArrayList<>();
        Article ar;
        Log.i(callingActivity, "Response: " + jsonStr);

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
        delegate.processFinish(articles);
    }

}
