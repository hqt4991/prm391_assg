package se04869.vn.edu.fpt.prm391_assg.Helper;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import se04869.vn.edu.fpt.prm391_assg.Model.Article;
import se04869.vn.edu.fpt.prm391_assg.R;

public class MyAdapter extends ArrayAdapter<Article> {

    private Context context;
    private ArrayList<Article> articles;

    public MyAdapter(Context context, ArrayList<Article> articles) {
        super(context, R.layout.listview_headlines, articles);
        this.context = context;
        this.articles = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get headLineView from inflater
        View headLineView = inflater.inflate(R.layout.listview_headlines, parent, false);

        // 3. Get the two text view from the headLineView
        TextView txvTitle = headLineView.findViewById(R.id.txvTitle);
        TextView txvSource = headLineView.findViewById(R.id.txvSource);

        // 4. Set the text for textView
        txvTitle.setText(articles.get(position).getTitle());
        txvSource.setText(articles.get(position).getSource());

        // 5. return headLineView
        return headLineView;
    }

}
