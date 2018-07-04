package se04869.vn.edu.fpt.prm391_assg.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import se04869.vn.edu.fpt.prm391_assg.Helper.HeadlineGetter;
import se04869.vn.edu.fpt.prm391_assg.Helper.MyAdapter;
import se04869.vn.edu.fpt.prm391_assg.Model.Article;
import se04869.vn.edu.fpt.prm391_assg.R;

import static se04869.vn.edu.fpt.prm391_assg.Helper.AppConfig.URLHEADLINES;

public class MainActivity extends Activity {

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

        new HeadlineGetter(new HeadlineGetter.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<Article> output) {
                MyAdapter adapter = new MyAdapter(MainActivity.this, output);
                lsvHeadlines.setAdapter(adapter);
            }
        }, MainActivity.class.getSimpleName()).execute(URLHEADLINES);

    }


}
