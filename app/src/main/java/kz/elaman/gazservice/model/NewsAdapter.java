package kz.elaman.gazservice.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.HashMap;

import kz.elaman.gazservice.R;
import kz.elaman.gazservice.network.NetworkManager;
import kz.elaman.gazservice.fragment.NewsFragment;


public class NewsAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<HashMap<String,String>> data;
    private static LayoutInflater inflater=null;
    ImageLoader imageLoader;

    public NewsAdapter(Activity a, ArrayList<HashMap<String,String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
       if(convertView==null)
            vi = inflater.inflate(R.layout.item_news, null);

        imageLoader = NetworkManager.getInstance(activity).getImageLoader();

        TextView title_news = (TextView)vi.findViewById(R.id.title_news);
        TextView des = (TextView)vi.findViewById(R.id.des);
        TextView time = (TextView)vi.findViewById(R.id.time_news);
        NetworkImageView networkImageView = (NetworkImageView) vi.findViewById(R.id.news_image);

        HashMap<String,String> news = new HashMap<String,String>();
        news = data.get(position);

        Log.d("click","url: "+news.get(NewsFragment.IMG_URL));
        Log.d("click", "time: "+ news.get(NewsFragment.TIME));
        title_news.setText(news.get(NewsFragment.TITLE));
        des.setText(news.get(NewsFragment.DESCRIPTION));
        time.setText(news.get(NewsFragment.TIME));

        networkImageView.setDefaultImageResId(R.drawable.wait);
        networkImageView.setImageUrl(news.get(NewsFragment.IMG_URL), imageLoader);

        return vi;
    }
}
