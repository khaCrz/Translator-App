package com.example.translateapp.View;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;
import com.example.translateapp.R;
import com.example.translateapp.Service.FloatingViewService;
import com.example.translateapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    public WindowManager windowManager;
    String[] fromLanguage = {"From","Abkhazian", "Chinese", "Croatian", "Czech", "Danish", "Divehi, Dhivehi, Maldivian","Dutch","Dzongkha","English","Esperanto","Estonian","Fijian","Finnish","French","Fula, Fulah, Pulaar, Pular","Galician","Gaelic (Scottish)","Gaelic (Manx)","German","Hindi","Hungarian","Icelandic","Indonesian","Italian","Japanese","Javanese","Khmer","Korean","Latin","Latvian (Lettish)","Malay","Malayalam","Polish","Portuguese","Punjabi (Eastern)","Romanian","Russian","Sami","Samoan","Somali","Southern Ndebele","Spanish","Tajik",
            "Tamil","Thai","Ukrainian", "Vietnamese"};
    private FragmentHomeBinding binding;
    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("save",getActivity().MODE_PRIVATE);
        binding.switchbtn.setChecked(sharedPreferences.getBoolean("value",false));
        ArrayAdapter fromAdapter = new ArrayAdapter(this.getContext(), R.layout.spinner_item, fromLanguage);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinerBtn.setAdapter(fromAdapter);
        binding.switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                {
                    SharedPreferences.Editor editor= getActivity().getSharedPreferences("save",getActivity().MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    getActivity().stopService(new Intent(getContext(), FloatingViewService.class));
                }
                else
                {
                    SharedPreferences.Editor editor= getActivity().getSharedPreferences("save",getActivity().MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    checkOverlayPermission();
                    getActivity().startService(new Intent(getContext(), FloatingViewService.class));
                    getActivity().finish();
                }

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    public void checkOverlayPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getContext())) {
                // send user to the device settings
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivity(intent);
            }
        }
    }

}


