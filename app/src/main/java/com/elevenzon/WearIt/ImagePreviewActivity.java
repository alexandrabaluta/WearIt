package com.elevenzon.WearIt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ImagePreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String title=bundle.getString("Tags");
        Bitmap bmp= (Bitmap) intent.getParcelableExtra("Bitmap");
        TextView myTags=(TextView) findViewById(R.id.textView);
           myTags.setText(title);
           ImageView myPrevImg= (ImageView) findViewById(R.id.previewImg);
         myPrevImg.setImageBitmap(bmp);
    }

  //  public void addElement(String tags, Bitmap imageView){
   //     TextView myTags=(TextView) findViewById(R.id.textView);
    //    myTags.setText(tags);
     //   ImageView myPrevImg= (ImageView) findViewById(R.id.previewImg);
       // myPrevImg.setImageBitmap(imageView);
    //}
}