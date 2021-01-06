package com.elevenzon.WearIt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class FilterByActivity extends AppCompatActivity {
    GridView photoGrid;
    DatabaseHelper databaseHelper = new DatabaseHelper(FilterByActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by2);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String color=bundle.getString("FilterColor");
        int i=1;
        final List<Bitmap> bmpList = new ArrayList<>();
        while(i<50){
            Bitmap bmp_img = databaseHelper.getMatch(i,color );
            if(bmp_img != null){
                bmpList.add(bmp_img);
            }
            else break;
            i++;
        }

        photoGrid = (GridView)findViewById(R.id.PhotoGridView);
        photoGrid.setAdapter(new CustomGridAdapter(this, bmpList));
        photoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bitmap prevImg = bmpList.get(position);
            //iv.setImageBitmap(prevImg);
            //ImagePreviewActivity imagePreviewActivity= new ImagePreviewActivity();
            //get id of photo, then get tags from the database and put them in previewDialog
            StringBuilder tagTitle = databaseHelper.getTagsAtID(position+1);
            /**
             * The title here represents the tags for the photo !!
             */
            //  imagePreviewActivity.addElement(tagTitle.toString(), prevImg);
            //previewDialog.setTitle(tagTitle);
            //previewDialog.show();
            Intent intent1=new Intent(getApplicationContext(), ImagePreviewActivity.class);
            intent1.putExtra("Tags", tagTitle.toString());
            intent1.putExtra("Bitmap", prevImg);
            startActivity(intent1);



        }
    });
}

public class CustomGridAdapter extends BaseAdapter {

    private Activity mContext;
    //keep a list of bitmaps
    public List<Bitmap> mBitmapIds;

    public CustomGridAdapter(FilterByActivity filterActivity, List<Bitmap> items){
        this.mContext = filterActivity;
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