package com.midterm.traslator_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.Object;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;




public class MainActivity extends AppCompatActivity {
    EditText text,fromLangCode,toLangCode;
    TextView translatedText;
    Button btnTranslate_text, btnTranslate_img;
    ImageView imgV;
    Uri image_url;

    private static final int PERMISSION_CODE = 111;
    private static final int IMAGE_CAPTURE_CODE = 112;
    private static final int IMAGE_PICK_CODE = 113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initUi();
        btnTranslate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translate_api translate = new translate_api();
                translate.setOnTranslationCompleteListener(new translate_api.OnTranslationCompleteListener() {
                    @Override
                    public void onStartTranslation() {
                        // here you can perform initial work before translated the text like displaying progress bar
                    }
                    @Override
                    public void onCompleted(String text) {
                        // "text" variable will give you the translated text
                        translatedText.setText(text);
                    }
                    @Override
                    public void onError(Exception e) {

                    }
                });
                translate.execute(text.getText().toString(),fromLangCode.getText().toString(),toLangCode.getText().toString());
            }
        });
        btnTranslate_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });


    }

    public void drawRect(ArrayList<RectF> arrRect, ImageView img) {
        BitmapDrawable bmpDraw = (BitmapDrawable) img.getDrawable();
        Bitmap bmp = bmpDraw.getBitmap().copy(Bitmap.Config.RGB_565, true);
        Canvas c = new Canvas(bmp);
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(4.0f);
        for (RectF rect: arrRect) {
            c.drawRect(rect,rectPaint);
        }
        img.setImageBitmap(bmp);
    }


    public void Detect(){
        //TODO 1. define TextRecognizer
        TextRecognizer textRecognizer = new TextRecognizer.Builder(MainActivity.this).build();

        //TODO 2. Get bitmap from imageview
        Bitmap bitmap = ((BitmapDrawable)imgV.getDrawable()).getBitmap();

        //TODO 3. get frame from bitmap
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        //TODO 4. get data from frame
        SparseArray<TextBlock> sparseArray = textRecognizer.detect(frame);
        //TODO 5. set data on textview
        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<RectF> arrRect = new ArrayList<>();
        //The Color of the Rectangle to Draw on top of Text
        //Loop through each `Block`
        for (int i = 0; i < sparseArray.size(); i++) {
            TextBlock tb = sparseArray.get(i);
            String str = tb.getValue();
            stringBuilder.append(str);
            stringBuilder.append(" ");
            //Loop through each `Word`
            for (Text line : tb.getComponents())
            {
                //System.out.println(line.getValue());
                arrRect.add(new RectF(line.getBoundingBox()));
                //Loop through each `Word`
//                for (Text currentword : line.getComponents())
//                {
//                    //Get the Rectangle/boundingBox of the word
//                    arrRect.add(new RectF(currentword.getBoundingBox()));
//                }
            }


        }


        translate_api translate = new translate_api();
        translate.setOnTranslationCompleteListener(new translate_api.OnTranslationCompleteListener() {
            @Override
            public void onStartTranslation() {
                // here you can perform initial work before translated the text like displaying progress bar
            }
            @Override
            public void onCompleted(String text) {
                // "text" variable will give you the translated text
                translatedText.setText(text);
            }
            @Override
            public void onError(Exception e) {

            }
        });
        translate.execute(stringBuilder.toString(),fromLangCode.getText().toString(),toLangCode.getText().toString());
        //translate.execute(stringBuilder.toString(),fromLangCode.getText().toString(),toLangCode.getText().toString());
        //translatedText.setText(stringBuilder);

        drawRect(arrRect, imgV);

    }

    private void dispatchTakePictureIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else{
                selectImage();
            }
        }else{
            selectImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        TextView title = new TextView(this);
        title.setText("Photos");
        title.setBackgroundColor(Color.BLACK);
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
                    image_url = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_url);
                    startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
                } else if (items[item].equals("Choose from Library")) {
                    Intent cameraIntent = new Intent(Intent.ACTION_PICK);
                    cameraIntent.setType("image/*");
                    startActivityForResult(cameraIntent, IMAGE_PICK_CODE);
                } else if (items[item].equals("Cancel")) {
                    Log.d("this", "CLOSE ");
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE_CODE){
            System.out.println(image_url);
            imgV.setImageURI(image_url);
            Detect();
        }
        else if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            System.out.println(data.getData());
            imgV.setImageURI(data.getData());
            Detect();
        }
    }

    private void initUi() {
        text=findViewById(R.id.text);
        fromLangCode=findViewById(R.id.from_lang);
        toLangCode=findViewById(R.id.to_lang);
        translatedText=findViewById(R.id.translated_text);
        btnTranslate_text=findViewById(R.id.btnTranslateText);
        btnTranslate_img=findViewById(R.id.btnTranslateImgV);
        imgV = findViewById(R.id.imgView);
    }
}