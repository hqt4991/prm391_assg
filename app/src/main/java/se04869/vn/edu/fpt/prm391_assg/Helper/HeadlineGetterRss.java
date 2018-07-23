package se04869.vn.edu.fpt.prm391_assg.Helper;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import se04869.vn.edu.fpt.prm391_assg.Model.Article;

public class HeadlineGetterRss extends AsyncTask<String, Void, ArrayList<Article>> {

    public HeadlineGetter.AsyncResponse delegate;
    private String callingActivity;

    public HeadlineGetterRss(HeadlineGetter.AsyncResponse delegate, String callingActivity) {
        this.delegate = delegate;
        this.callingActivity = callingActivity;
    }

    @Override
    protected ArrayList<Article> doInBackground(String... urls) {

        ArrayList<Article> toReturn = new ArrayList<>();
        Article ar;
        InputStream in = new HttpHandler().requestRss(urls[0]);
        String title = null;
        String url = null;
        String source;
        boolean isItem = false;

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(in, null);
            xmlPullParser.nextTag();

            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {

                String result = "";

                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();

                if(name == null)
                    continue;

                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d(callingActivity, "Parsing name ==> " + name);

                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    Log.d(callingActivity, "Parsing result ==> " + result);
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    url = result;
                }

                if (title != null && url != null) {
                    if(isItem) {
                        ar = new Article();
                        ar.setTitle(title);
                        ar.setUrl(url);
                        ar.setSource("VnExpress");
                        toReturn.add(ar);
                    }

                    title = null;
                    url = null;
                    isItem = false;
                }

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toReturn;

    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        super.onPostExecute(articles);
        delegate.processFinish(articles);
    }


}
