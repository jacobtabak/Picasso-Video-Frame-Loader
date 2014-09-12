package me.tabak.mediastoreframetest;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends ArrayAdapter<Uri> {
    private final Picasso mPicasso;

    public ImageAdapter(Context context) {
        super(context, 0);
        mPicasso = new Picasso.Builder(getContext())
                .indicatorsEnabled(true)
                .build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view;
        if (convertView == null) {
            view = new ImageView(getContext());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    200, getContext().getResources().getDisplayMetrics());
            view.setLayoutParams(
                    new AbsListView.LayoutParams(
                            AbsListView.LayoutParams.MATCH_PARENT, height));
        } else {
            view = (ImageView) convertView;
        }

        mPicasso.load(getItem(position))
                .fit()
                .centerCrop()
                .into(view);
        return view;
    }
}
