package com.example.translateapp.View;

import android.Manifest;
import static android.app.Activity.RESULT_OK;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.translateapp.Api.translate_api;
import com.example.translateapp.Model.Sentence;
import com.example.translateapp.R;
import com.example.translateapp.Service.FloatingViewService;
import com.example.translateapp.databinding.FragmentHomeBinding;
import com.example.translateapp.databinding.FragmentTranslateBinding;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {

    public WindowManager windowManager;
    ImageView imgV;
    Uri image_url;
    private static final int PERMISSION_CODE = 111;
    private static final int IMAGE_CAPTURE_CODE = 112;
    private static final int IMAGE_PICK_CODE = 113;
    private FragmentHomeBinding binding;
    private String FROM_LANG = "From";
    private String TO_LANG = "To";
    String[] fromLanguage = {"From","Abkhazian", "Chinese", "Croatian", "Czech", "Danish", "Divehi, Dhivehi, Maldivian","Dutch","Dzongkha","English","Esperanto","Estonian","Fijian","Finnish","French","Fula, Fulah, Pulaar, Pular","Galician","Gaelic (Scottish)","Gaelic (Manx)","German","Hindi","Hungarian","Icelandic","Indonesian","Italian","Japanese","Javanese","Khmer","Korean","Latin","Latvian (Lettish)","Malay","Malayalam","Polish","Portuguese","Punjabi (Eastern)","Romanian","Russian","Sami","Samoan","Somali","Southern Ndebele","Spanish","Tajik",
            "Tamil","Thai","Ukrainian", "Vietnamese"};
    String[] toLanguage = {"To", "Abkhazian", "Chinese", "Croatian", "Czech", "Danish", "Divehi, Dhivehi, Maldivian","Dutch","Dzongkha","English","Esperanto","Estonian","Fijian","Finnish","French","Fula, Fulah, Pulaar, Pular","Galician","Gaelic (Scottish)","Gaelic (Manx)","German","Hindi","Hungarian","Icelandic","Indonesian","Italian","Japanese","Javanese","Khmer","Korean","Latin","Latvian (Lettish)","Malay","Malayalam","Polish","Portuguese","Punjabi (Eastern)","Romanian","Russian","Sami","Samoan","Somali","Southern Ndebele","Spanish","Tajik",
            "Tamil","Thai","Ukrainian", "Vietnamese"};

    String[] fromLanguage_code = {"From","ab","zh","hr","cs","da","dv","nl","dz","en","eo","et","fj","fi","fr","ff","gl","gd","gv","de","hi","hu","is","id","it","ja","jv","km","ko","la","lv","ms","ml","pl","pt","pa","ro","ru",
            "se","sm","so","nr","es","tg","ta","th","uk","vi"};
    String[] toLanguage_code = {"To", "ab","zh","hr","cs","da","dv","nl","dz","en","eo","et","fj","fi","fr","ff","gl","gd","gv","de","hi","hu","is","id","it","ja","jv","km","ko","la","lv","ms","ml","pl","pt","pa","ro","ru",
            "se","sm","so","nr","es","tg","ta","th","uk","vi"};



    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("save",getActivity().MODE_PRIVATE);
//        binding.switchbtn.setChecked(sharedPreferences.getBoolean("value",false));
//        ArrayAdapter fromAdapter = new ArrayAdapter(this.getContext(), R.layout.spinner_item, fromLanguage);
//        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.spinerBtn.setAdapter(fromAdapter);
//        binding.switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(!b)
//                {
//                    SharedPreferences.Editor editor= getActivity().getSharedPreferences("save",getActivity().MODE_PRIVATE).edit();
//                    editor.putBoolean("value",false);
//                    editor.apply();
//                    getActivity().stopService(new Intent(getContext(), FloatingViewService.class));
//                }
//                else
//                {
//                    SharedPreferences.Editor editor= getActivity().getSharedPreferences("save",getActivity().MODE_PRIVATE).edit();
//                    editor.putBoolean("value",true);
//                    editor.apply();
//                    checkOverlayPermission();
//                    getActivity().startService(new Intent(getContext(), FloatingViewService.class));
//                    getActivity().finish();
//                }
//
//            }
//        });
        ArrayAdapter fromAdapter = new ArrayAdapter(this.getContext(), R.layout.spinner_item, fromLanguage);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fromLanguage.setAdapter(fromAdapter);
        binding.fromLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FROM_LANG = fromLanguage_code[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imgV = binding.TVImgIn;
        ArrayAdapter toAdapter = new ArrayAdapter(this.getContext(), R.layout.spinner_item, toLanguage);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.toLanguage.setAdapter(toAdapter);
        binding.toLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TO_LANG = toLanguage_code[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

//    public void checkOverlayPermission(){
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(getContext())) {
//                // send user to the device settings
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getActivity().getPackageName()));
//                startActivity(intent);
//            }
//        }
//    }

    public void drawRect(ArrayList<RectF> arrRect, ImageView img) {
        BitmapDrawable bmpDraw = (BitmapDrawable) img.getDrawable();
        Bitmap bmp = bmpDraw.getBitmap().copy(Bitmap.Config.RGB_565, true);
        Canvas c = new Canvas(bmp);
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(4.0f);
        for (RectF rect: arrRect) {
            System.out.println(rect);
            c.drawRect(rect,rectPaint);
        }
        img.setImageBitmap(bmp);
    }

    public void Detect() {
        //TODO 1. define TextRecognizer
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getContext()).build();

        //TODO 2. Get bitmap from imageview
        Bitmap bitmap = ((BitmapDrawable) imgV.getDrawable()).getBitmap();

        //TODO 3. get frame from bitmap
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        //TODO 4. get data from frame
        SparseArray<TextBlock> sparseArray = textRecognizer.detect(frame);
        //The Color of the Rectangle to Draw on top of Text
        //Loop through each `Block`
        ArrayList<Text> Data = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            TextBlock tb = sparseArray.get(i);
            for (Text line : tb.getComponents()) {
                Data.add(line);
            }
        }
        Xuly(Data);
//        String txt = "";
//        for (String line : arr_sentense) {
//            txt += line;
//            System.out.println(line);
//            txt += "\n";
//        }
//        translatedText.setText(txt);
        //drawRect(arrRect, imgV);
    }


    public void Xuly(ArrayList<Text> Data){
        if(!FROM_LANG.equals("From") && !TO_LANG.equals("To")){
            String str = "";
            System.out.println(("Xu ly"));
            ArrayList<String> arr_sentense = new ArrayList<>();
            ArrayList<Sentence> sentenceArrayList = new ArrayList<>();
            ArrayList<RectF> arrRect = new ArrayList<>();
            float x1 = 0, y1 = 0, x2 = 0, y2 = 0;
            int id = 0;
            int count_sentence = 1;
            for (Text line : Data){
                if(line.getValue().contains(".")){
                    sentenceArrayList.add(new Sentence(count_sentence,""));
                    count_sentence++;
                }
            }

            //System.out.println(sentenceArrayList);
            for (Text line : Data) {
                RectF rect = new RectF(line.getBoundingBox());
                x1 = rect.left;
                y1 = rect.top;
                if (line.getValue().contains(".")) {
                    for (Text currentword : line.getComponents()) {
                        if (currentword.getValue().contains(".")) {
                            str += currentword.getValue();
                            arr_sentense.add(str);
                            System.out.println("3123123");

                            //sentenceArrayList.add(new Sentence(str, ))
                            str = "";
                            x2 = currentword.getBoundingBox().right;
                            y2 = currentword.getBoundingBox().bottom;
                            //add vao
                            arrRect.add(new RectF(x1,y1,x2,y2));
                        } else {
                            if(str.equals("")){
                                x1 = currentword.getBoundingBox().left;
                                y1 = currentword.getBoundingBox().top;
                            }
                            str += currentword.getValue();
                            str += " ";
                            x2 = currentword.getBoundingBox().right;
                            y2 = currentword.getBoundingBox().bottom;
                        }
                    }
                    arrRect.add(new RectF(x1,y1,x2,y2));
                } else {
                    str += line.getValue();
                    str += " ";
                    x1 = rect.left;
                    y1 = rect.top;
                    x2 = x1 + rect.width();
                    y2 = y1 + rect.height();
                    arrRect.add(new RectF(x1,y1,x2,y2));
                }
            }
            drawRect(arrRect, imgV);
            String txt = "";
            for (String i : arr_sentense) {
                txt += i + "---\n\n";
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
                    binding.tvRS.setText(text);

                }
                @Override
                public void onError(Exception e) {

                }
            });
            translate.execute(txt,FROM_LANG,TO_LANG);
        }else{
            Toast.makeText(getContext(), "Choose the from language and to language.", Toast.LENGTH_SHORT).show();
        }

        binding.transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int FromID = binding.fromLanguage.getSelectedItemPosition();
                int ToID = binding.toLanguage.getSelectedItemPosition();
                binding.toLanguage.setSelection(FromID);
                binding.fromLanguage.setSelection(ToID);

            }
        });
        
//        System.out.println(sentenceArrayList);
    }

    private void dispatchTakePictureIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else{
                selectImage();
            }
        }else{
            selectImage();
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        TextView title = new TextView(getContext());
        title.setText("Photos");
        title.setBackgroundColor(Color.BLACK);
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCustomTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
                    image_url = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_url);
                    getActivity().startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
                } else if (items[item].equals("Choose from Library")) {
                    Intent cameraIntent = new Intent(Intent.ACTION_PICK);
                    cameraIntent.setType("image/*");
                    getActivity().startActivityForResult(cameraIntent, IMAGE_PICK_CODE);
                } else if (items[item].equals("Cancel")) {
                    Log.d("this", "CLOSE ");
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public void onActivityRS(int requestCode, int resultCode, Intent data) {
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
}


