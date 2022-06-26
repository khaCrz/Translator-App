package com.example.translateapp.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translateapp.Model.textdata;
import com.example.translateapp.Model.user;
import com.example.translateapp.R;

import java.util.ArrayList;

public class history_Adapter extends RecyclerView.Adapter<history_Adapter.ViewHolder> {
    private ArrayList<textdata> textdata_;
    public history_Adapter(ArrayList<textdata> dataSet){textdata_ = dataSet; }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Fromlanguage;
        private TextView Tolanguage;
        private ImageButton Fromcopy;
        private ImageButton Tocopy;
        private TextView Datetime;
        private TextView TextFrom;
        private TextView TextTo;


        public ViewHolder(View view) {
            super(view);
            Fromlanguage = (TextView) view.findViewById(R.id.idfromlanguage);
            Tolanguage = (TextView) view.findViewById(R.id.idtolanguage);
            Fromcopy = (ImageButton) view.findViewById(R.id.copyfrom);
            Tocopy = (ImageButton) view.findViewById(R.id.copyto);
            TextFrom = (TextView) view.findViewById(R.id.textfrom);
            TextTo =(TextView) view.findViewById(R.id.textto);
            Datetime = (TextView) view.findViewById(R.id.datetimehistory);

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
        holder.Datetime.setText(textdata_.get(position).getDatetime());
        holder.Fromlanguage.setText(textdata_.get(position).getFromlanguage());
        holder.Tolanguage.setText(textdata_.get(position).getTolanguage());


    }
    @Override
    public int getItemCount() {
        return textdata_.size();
    }
}
