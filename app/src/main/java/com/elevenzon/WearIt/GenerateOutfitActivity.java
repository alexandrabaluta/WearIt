package com.elevenzon.WearIt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class GenerateOutfitActivity extends AppCompatActivity {
    GridView photoGrid;
    DatabaseHelper databaseHelper = new DatabaseHelper(GenerateOutfitActivity.this);
    private Spinner spinnerColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_outfit);
        spinnerColor = (Spinner) findViewById(R.id.tagFilter);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this, R.array.color_tags, android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter to the spinner
        spinnerColor.setAdapter(colorAdapter);
       // spinnerColor.setOnItemSelectedListener(this);
        Button filterBy = findViewById(R.id.filter_by);

        filterBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenerateOutfitActivity.this, FilterByActivity.class);
                intent.putExtra("FilterColor", spinnerColor.getSelectedItem().toString());
                startActivity(intent);
            }
        });
        int i = 1;
        int j = 1;
        int k = 1;
        int l = 1;
        final List<Bitmap> bmpList = new ArrayList<>();


        while (i < 50) {
            Bitmap bmp_img = databaseHelper.getMatchType(i, "Top");
            if (bmp_img != null) {
                bmpList.add(bmp_img);
                break;
            }
            i++;

        }
        while (j < 50) {
            Bitmap bmp_img = databaseHelper.getMatchType(j, "Jeans");
            if (bmp_img != null) {
                bmpList.add(bmp_img);
                break;
            }
            j++;

        }

        while (k < 50) {
            Bitmap bmp_img = databaseHelper.getMatchType(k, "Shoes");
            if (bmp_img != null) {
                bmpList.add(bmp_img);
                break;
            }
            k++;

        }
        while (l < 50) {
            Bitmap bmp_img = databaseHelper.getMatchType(l, "Bag");
            if (bmp_img != null) {
                bmpList.add(bmp_img);
                break;
            }
            l++;
        }

     /*   final Dialog previewDialog = new Dialog(GenerateOutfitActivity.this, android.R.style.Theme_Translucent);
        previewDialog.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        previewDialog.setCancelable(true);
        previewDialog.setContentView(R.layout.dialog_layout);
        //previewDialog.setTitle();*/
/*
        final ImageView iv = (ImageView) previewDialog.findViewById(R.id.iv_preview_image);
*/

        photoGrid = (GridView) findViewById(R.id.PhotoGridView);
        photoGrid.setAdapter(new GenerateOutfitActivity.CustomGridAdapter(this, bmpList));
        photoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bitmap prevImg = bmpList.get(position);
                // iv.setImageBitmap(prevImg);
                //get id of photo, then get tags from the database and put them in previewDialog
                StringBuilder tagTitle = databaseHelper.getTagsAtID(position + 1);
                /**
                 * The title here represents the tags for the photo !!
                 */
                Intent intent1 = new Intent(getApplicationContext(), ImagePreviewActivity.class);
                intent1.putExtra("Tags", tagTitle.toString());
                intent1.putExtra("Bitmap", prevImg);
                startActivity(intent1);
                // previewDialog.setTitle(tagTitle);
                // previewDialog.show();

            }
        });
    }

    public class CustomGridAdapter extends BaseAdapter {

        private Activity mContext;
        //keep a list of bitmaps
        public List<Bitmap> mBitmapIds;

        public CustomGridAdapter(GenerateOutfitActivity outfitActivity, List<Bitmap> items) {
            this.mContext = outfitActivity;
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
