package se04869.vn.edu.fpt.prm391_assg.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import se04869.vn.edu.fpt.prm391_assg.Helper.HeadlineGetter;
import se04869.vn.edu.fpt.prm391_assg.Helper.MyAdapter;
import se04869.vn.edu.fpt.prm391_assg.Model.Article;
import se04869.vn.edu.fpt.prm391_assg.R;

import static se04869.vn.edu.fpt.prm391_assg.Helper.AppConfig.URLBUSINESS;
import static se04869.vn.edu.fpt.prm391_assg.Helper.AppConfig.URLENTERTAINMENT;
import static se04869.vn.edu.fpt.prm391_assg.Helper.AppConfig.URLHEADLINES;
import static se04869.vn.edu.fpt.prm391_assg.Helper.AppConfig.URLSPORTS;
import static se04869.vn.edu.fpt.prm391_assg.Helper.AppConfig.URLTECH;

public class MainActivity extends AppCompatActivity {

    ListView lsvHeadlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrimaryDrawerItem item0 = new PrimaryDrawerItem().withName("Headlines").withIdentifier(0);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Business");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("Sports");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("Technology");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName("Entertainment");

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        item0,
                        item1,
                        item2,
                        item3,
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        String url = null;

                        switch (position){
                            case 0:
                                url = URLHEADLINES;
                                break;
                            case 1:
                                url = URLBUSINESS;
                                break;
                            case 2:
                                url = URLSPORTS;
                                break;
                            case 3:
                                url = URLTECH;
                                break;
                            case 4:
                                url = URLENTERTAINMENT;
                                break;
                        }

                        new HeadlineGetter(new HeadlineGetter.AsyncResponse() {
                            @Override
                            public void processFinish(ArrayList<Article> output) {
                                MyAdapter adapter = new MyAdapter(MainActivity.this, output);
                                lsvHeadlines.setAdapter(adapter);
                            }
                        }, MainActivity.class.getSimpleName()).execute(url);

                        return true;
                    }
                })
                .build();

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

        result.setSelection(0, true);
    }


}
