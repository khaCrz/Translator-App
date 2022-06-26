package com.example.translateapp.View;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translateapp.Appdatabase;
import com.example.translateapp.Model.textdata;
import com.example.translateapp.Model.user;
import com.example.translateapp.R;
import com.example.translateapp.databinding.FragmentHistoryBinding;
import com.example.translateapp.databinding.FragmentTranslateBinding;
import com.example.translateapp.textDao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private history_Adapter history_Adapter;
    private Appdatabase appDatabase;
    private ArrayList<textdata> ListText;
    private textDao textDao;
    public HistoryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHistoryBinding.inflate(getLayoutInflater());
        appDatabase = Appdatabase.getInstance(getContext());
        textDao = appDatabase.textDao();
        ListText = new ArrayList<>();
        history_Adapter = new history_Adapter(ListText);
        binding.rvHistory.setAdapter(history_Adapter);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<textdata> dog = textDao.getAlldata();
//
                for(textdata textdata : textDao.getAlldata())
                {
                    ListText.add(textdata);
                }

            System.out.println("Getdata");
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        binding.rvHistory.setLayoutManager(new GridLayoutManager(getContext(),1));
        history_Adapter.notifyDataSetChanged();

        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


    }
    private static final int REQ_CODE = 123; // MUST be 0-65535

}