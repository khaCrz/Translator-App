package com.example.translateapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translateapp.Model.textdata;
import com.example.translateapp.R;

import java.util.ArrayList;

public class history_Adapter extends RecyclerView.Adapter<history_Adapter.ViewHolder> {
    private ArrayList<textdata> textdata_;
    public history_Adapter(ArrayList<textdata> dataSet){textdata_ = dataSet; }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView TextFrom;
        private TextView TextTo;


        public ViewHolder(View view) {
            super(view);
            TextFrom = (TextView) view.findViewById(R.id.textfrom);
            TextTo =(TextView) view.findViewById(R.id.textto);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_translate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.TextFrom.setText(textdata_.get(position).getTextfrom());
        holder.TextTo.setText(textdata_.get(position).getTextto());

    }
    @Override
    public int getItemCount() {
        return textdata_.size();
    }
}
