package com.akshaytech.turbi;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class SecondActivity extends AppCompatActivity {
    Button button1,button2,button3,button4;
    ImageView imageView1,imageView2,imageView3;
    String pathtofile;
    private  Bitmap bmp;

    TextView textView1;
    private int[][] rgbValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imageView1 = (ImageView) findViewById(R.id.iv1);
        imageView2 = (ImageView) findViewById(R.id.iv2);
        imageView3 = (ImageView) findViewById(R.id.iv3);
        textView1 = (TextView) findViewById(R.id.tv111);

        button1 = (Button) findViewById(R.id.btn_1);
        button2 = (Button) findViewById(R.id.btn_2);
        button3 = (Button) findViewById(R.id.btn_3);
        button4 = (Button) findViewById(R.id.btn_result);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, Results.class);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchPictureTakeAction1();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchPictureTakeAction2();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchPictureTakeAction3();
            }
        });

        imageView1.setOnTouchListener(new ImageView.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int x=0;
                int y=0;
//                textView1.setText("Touch coordinates : " +
//                        String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
                ImageView imageView = ((ImageView)v);
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                int pixel = bitmap.getPixel(x,y);
                float redValue = Color.red(pixel);
                float blueValue = Color.blue(pixel);
                float greenValue = Color.green(pixel);

                float Grayscale = (float) ((0.3*redValue)+(0.59*greenValue)+(0.11*blueValue));
                if(Grayscale>0&&Grayscale<101.25){
                    textView1.setText("Low Turbidity");
                }
                else if(Grayscale>101.25&&Grayscale<155.95){
                    textView1.setText("Moderate Turbidity");
                }
                else{
                    textView1.setText("High Turbidity");
                }
        /*if(redValue == 255){
            if(blueValue == 0)
                if(greenValue==0)
               textViewCol.setText("It is Red");
            }*/
                return true;    }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathtofile);
                imageView1.setImageBitmap(bitmap);
            }
            if(requestCode==2){
                Bitmap bitmap = BitmapFactory.decodeFile(pathtofile);
                imageView2.setImageBitmap(bitmap);
            }
            if(requestCode==3){
                Bitmap bitmap = BitmapFactory.decodeFile(pathtofile);
                imageView3.setImageBitmap(bitmap);
            }
        }
    }

    private void dispatchPictureTakeAction1() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            photoFile = createPhotoFile();
            if(photoFile!=null) {
                pathtofile = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(SecondActivity.this,"com.akshaytech.turbi.FileProvider",photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePic,1);
            }
        }
    }
    private void dispatchPictureTakeAction2() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            photoFile = createPhotoFile();
            if(photoFile!=null) {
                pathtofile = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(SecondActivity.this,"com.akshaytech.turbi.FileProvider",photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePic,2);
            }
        }
    }
    private void dispatchPictureTakeAction3() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            photoFile = createPhotoFile();
            if(photoFile!=null) {
                pathtofile = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(SecondActivity.this,"com.akshaytech.turbi.FileProvider",photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePic,3);
            }
        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name,".jpg",storageDir);
        } catch (IOException e) {
            Log.d("mylog","Exp."+e.toString());
        }
        return image;
    }


}
