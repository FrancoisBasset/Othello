package com.groupe17.othello;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

public class DiskAdapter extends BaseAdapter {
    private Context context;
    private ImageView white_imageView;
    private ImageView black_imageView;

    public DiskAdapter(Context context) {
        this.context = context;

        white_imageView = new ImageButton(context);
        white_imageView.setImageURI(Uri.parse("drawable/white_disk.png"));

        black_imageView = new ImageButton(context);
        black_imageView.setImageURI(Uri.parse("drawable/black_disk.png"));
    }

    @Override
    public int getCount() {
        return 64;
    }

    @Override
    public Object getItem(int position) {
        return white_imageView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(8, 16, 8, 16);

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(R.drawable.black_disk);
        return imageView;

    }
}
