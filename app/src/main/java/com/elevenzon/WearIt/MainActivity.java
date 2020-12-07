package com.elevenzon.WearIt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button add_clothes;
    Button your_clothes;
    Button inspiration;
    Intent i;
    final static int CameraData = 0;
    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_clothes = (Button) findViewById(R.id.button_addclothes);
        add_clothes.setOnClickListener(this);
        your_clothes  = (Button) findViewById(R.id.button_yourclothes);
        your_clothes.setOnClickListener(this);
        inspiration  = (Button) findViewById(R.id.button_inspiration);
        inspiration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_addclothes) {
            i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, CameraData);
        }
        else if(v.getId() == R.id.button_yourclothes){
            Intent intent = new Intent(getApplicationContext(), MyClothes.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.button_inspiration){
            Intent intent = new Intent(getApplicationContext(), InspirationActivity.class);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bmp = (Bitmap) data.getExtras().get("data");
            //iv.setImageBitmap(bmp);
            //ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            //byte[] byteArray = stream.toByteArray();

//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
//            File pictureFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "IMG_" + timeStamp + ".jpg");
//            FileOutputStream fos = null;
//            try {
//                fos = new FileOutputStream(pictureFile);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            bmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);
//            try {
//                fos.flush();
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            String imagePath = pictureFile.getAbsolutePath();
//            databaseHelper.addImage(imagePath);

            databaseHelper.addImage(bmp);
        }
    }
}