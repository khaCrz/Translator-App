package com.example.translateapp.View;

import static android.app.Activity.RESULT_OK;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.speech.RecognizerIntent;

import com.example.translateapp.Api.translate_api;
import com.example.translateapp.Appdatabase;
import com.example.translateapp.Model.textdata;
import com.example.translateapp.R;
import com.example.translateapp.databinding.FragmentHomeBinding;
import com.example.translateapp.databinding.FragmentTranslateBinding;
import com.example.translateapp.textDao;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class TranslateFragment extends Fragment {
    private FragmentTranslateBinding binding;
    private String FROM_LANG = "From";
    private String TO_LANG = "To";
    private TextToSpeech tts;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Appdatabase appDatabase;
    private com.example.translateapp.textDao textDao;
    Locale language = Locale.ENGLISH;
    String[] fromLanguage = {"From","Abkhazian", "Chinese", "Croatian", "Czech", "Danish", "Divehi, Dhivehi, Maldivian","Dutch","Dzongkha","English","Esperanto","Estonian","Fijian","Finnish","French","Fula, Fulah, Pulaar, Pular","Galician","Gaelic (Scottish)","Gaelic (Manx)","German","Hindi","Hungarian","Icelandic","Indonesian","Italian","Japanese","Javanese","Khmer","Korean","Latin","Latvian (Lettish)","Malay","Malayalam","Polish","Portuguese","Punjabi (Eastern)","Romanian","Russian","Sami","Samoan","Somali","Southern Ndebele","Spanish","Tajik",
            "Tamil","Thai","Ukrainian", "Vietnamese"};
    String[] toLanguage = {"To", "Abkhazian", "Chinese", "Croatian", "Czech", "Danish", "Divehi, Dhivehi, Maldivian","Dutch","Dzongkha","English","Esperanto","Estonian","Fijian","Finnish","French","Fula, Fulah, Pulaar, Pular","Galician","Gaelic (Scottish)","Gaelic (Manx)","German","Hindi","Hungarian","Icelandic","Indonesian","Italian","Japanese","Javanese","Khmer","Korean","Latin","Latvian (Lettish)","Malay","Malayalam","Polish","Portuguese","Punjabi (Eastern)","Romanian","Russian","Sami","Samoan","Somali","Southern Ndebele","Spanish","Tajik",
            "Tamil","Thai","Ukrainian", "Vietnamese"};

    String[] fromLanguage_code = {"From","ab","zh","hr","cs","da","dv","nl","dz","en","eo","et","fj","fi","fr","ff","gl","gd","gv","de","hi","hu","is","id","it","ja","jv","km","ko","la","lv","ms","ml","pl","pt","pa","ro","ru",
            "se","sm","so","nr","es","tg","ta","th","uk","vi"};
    String[] toLanguage_code = {"To", "ab","zh","hr","cs","da","dv","nl","dz","en","eo","et","fj","fi","fr","ff","gl","gd","gv","de","hi","hu","is","id","it","ja","jv","km","ko","la","lv","ms","ml","pl","pt","pa","ro","ru",
            "se","sm","so","nr","es","tg","ta","th","uk","vi"};

    public TranslateFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTranslateBinding.inflate(inflater,container,false);
        appDatabase = Appdatabase.getInstance(getContext());
        textDao = appDatabase.textDao();
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

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });
        tts.setLanguage(Locale.US);


        binding.transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int FromID = binding.fromLanguage.getSelectedItemPosition();
                int ToID = binding.toLanguage.getSelectedItemPosition();
                binding.toLanguage.setSelection(FromID);
                binding.fromLanguage.setSelection(ToID);

            }
        });



        binding.translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FROM_LANG.equals("From") && !TO_LANG.equals("To")){
                    if(!binding.inputTv.getText().equals("")){
                        translate_api translate = new translate_api();
                        translate.setOnTranslationCompleteListener(new translate_api.OnTranslationCompleteListener() {
                            @Override
                            public void onStartTranslation() {
                                // here you can perform initial work before translated the text like displaying progress bar
                            }
                            @Override
                            public void onCompleted(String text) {
                                // "text" variable will give you the translated text
                                binding.outputTv.setText(text);
                            }
                            @Override
                            public void onError(Exception e) {

                            }
                        });
                        translate.execute(binding.inputTv.getText().toString(),FROM_LANG,TO_LANG);
                    }else{
                        Toast.makeText(getContext(), "Enter something.", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "Choose the from language and to language.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        binding.ttsIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.inputTv.getText().equals("")){
                    tts.speak(binding.inputTv.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }else{
                    Toast.makeText(getContext(), "Enter something.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.ttsOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.outputTv.getText().equals("")){
                    tts.speak(binding.outputTv.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }else{
                    Toast.makeText(getContext(), "Enter something.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.micBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.inputTv.setText("");
            }
        });

        binding.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", binding.outputTv.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        binding.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromlanguage = binding.fromLanguage.getSelectedItem().toString();

                String tolanguage = binding.toLanguage.getSelectedItem().toString();

                String textfrom = binding.inputTv.getText().toString();

                String textto = binding.outputTv.getText().toString();
                System.out.println(textfrom);
                System.out.println(textto);
                String datetime = Calendar.getInstance().getTime().toString();
                System.out.println(datetime);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        textdata contact6 = new textdata(fromlanguage, tolanguage, textfrom, textto, datetime);
                        textDao.insert(contact6);
                        System.out.println("Uploaded");
                    }
                });
            }
        });

        return binding.getRoot();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            getActivity().startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void onActivityRS(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    binding.inputTv.setText(result.get(0));
                }
                break;
            }

        }
    }

}
