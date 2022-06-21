package com.example.translateapp.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.translateapp.R;
import com.example.translateapp.databinding.FragmentHomeBinding;
import com.example.translateapp.databinding.FragmentTranslateBinding;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TranslateFragment extends Fragment {
    private FragmentTranslateBinding binding;
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
        ArrayAdapter fromAdapter = new ArrayAdapter(this.getContext(), R.layout.spinner_item, fromLanguage);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fromLanguage.setAdapter(fromAdapter);
        ArrayAdapter toAdapter = new ArrayAdapter(this.getContext(), R.layout.spinner_item, toLanguage);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.toLanguage.setAdapter(toAdapter);
        binding.translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("213");
            }
        });
        binding.inputTv.setText("asdasdd");
        binding.inputTv.setHint("asd");
        return inflater.inflate(R.layout.fragment_translate, container, false);
    }


}