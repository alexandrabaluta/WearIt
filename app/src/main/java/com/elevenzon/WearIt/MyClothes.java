package com.elevenzon.WearIt;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MyClothes extends AppCompatActivity {

    GridView photoGrid;
    DatabaseHelper databaseHelper = new DatabaseHelper(MyClothes.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clothes);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "WIP", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int i=1;
        final List<Bitmap> bmpList = new ArrayList<>();
        while(i<50){
            Bitmap bmp_img = databaseHelper.getImage(i);
            if(bmp_img != null){
                bmpList.add(bmp_img);
            }
            else break;
            i++;
        }

        final Dialog previewDialog = new Dialog(MyClothes.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            previewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            previewDialog.setCancelable(true);
            previewDialog.setContentView(R.layout.dialog_layout);
            final ImageView iv = (ImageView) previewDialog.findViewById(R.id.iv_preview_image);

        photoGrid = (GridView)findViewById(R.id.PhotoGridView);
        photoGrid.setAdapter(new CustomGridAdapter(this, bmpList));
        photoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bitmap prevImg = bmpList.get(position);
                iv.setImageBitmap(prevImg);
                previewDialog.show();
                FloatingActionButton edit = findViewById(R.id.fab);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            // redirect to EditClothesActivity
                            Intent intent = new Intent(getApplicationContext(), EditClothesActivity.class);
                            startActivity(intent);
                        }
                });

            }
        });
    }

    public class CustomGridAdapter extends BaseAdapter{

        private Activity mContext;
        //keep a list of bitmaps
        public List<Bitmap> mBitmapIds;

        public CustomGridAdapter(MyClothes mainActivity, List<Bitmap> items){
            this.mContext = mainActivity;
            this.mBitmapIds = items;
        }

        @Override
        public int getCount() {
            return mBitmapIds.size();
        }

        @Override
        public Object getItem(int position) {
            return mBitmapIds.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageBitmap(mBitmapIds.get(position));
            //imageView.setImageResource(mBitmapIds.get(position));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;

        }
    }
}