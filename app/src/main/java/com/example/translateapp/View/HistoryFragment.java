package com.example.translateapp.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.translateapp.Adapter.history_Adapter;
import com.example.translateapp.Model.Appdatabase;
import com.example.translateapp.Model.textDao;
import com.example.translateapp.Model.textdata;
import com.example.translateapp.databinding.FragmentHistoryBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

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
        ListText = new ArrayList<>();
        appDatabase = Appdatabase.getInstance(getContext());
        textDao = appDatabase.textDao();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for(textdata textdata : textDao.getAlldata())
                {
                    ListText.add(textdata);
                }

                //System.out.println("Getdata");
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHistoryBinding.inflate(inflater,container,false);

        history_Adapter = new history_Adapter(ListText);
        binding.rvHistory.setAdapter(history_Adapter);
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        history_Adapter.notifyDataSetChanged();

        return binding.getRoot();
    }

    private static final int REQ_CODE = 123; // MUST be 0-65535

}