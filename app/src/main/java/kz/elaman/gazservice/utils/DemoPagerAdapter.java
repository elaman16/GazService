package kz.elaman.gazservice.utils;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


import java.util.ArrayList;
import java.util.List;

import kz.elaman.gazservice.R;
import kz.elaman.gazservice.network.NetworkManager;

public class DemoPagerAdapter extends PagerAdapter {

    private List<String> pictures = new ArrayList<>();
    private final SparseArray<NetworkImageView> mHolderArray = new SparseArray<>();
    private int mSize;
    ImageLoader imageLoader = NetworkManager.getInstance().getImageLoader();;

    public DemoPagerAdapter(List<String> pictures) {
        mSize = pictures.size();
        this.pictures = pictures;
    }

    public DemoPagerAdapter(int count) {
        mSize = count;
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(mHolderArray.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {


        if (imageLoader == null)
            imageLoader = NetworkManager.getInstance().getImageLoader();
        NetworkImageView thumbNail = new NetworkImageView(view.getContext());
        thumbNail.setDefaultImageResId(R.drawable.wait);
        thumbNail.setImageUrl(pictures.get(position), imageLoader);
        thumbNail.setScaleType(ImageView.ScaleType.FIT_CENTER);
        view.addView(thumbNail, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mHolderArray.put(position, thumbNail);
        return thumbNail;

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}