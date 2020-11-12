package com.example.bootcamp.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bootcamp.MovieDetail;
import com.example.bootcamp.R;
import com.example.bootcamp.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private String TAG = "MovieAdapter";
    private ArrayList<com.example.bootcamp.model.Movie> dataList;

    public MovieAdapter(ArrayList<Movie> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new MovieAdapter.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.txtJudul.setText(dataList.get(position).getJudul());
        holder.txtRating.setText(dataList.get(position).getRating());
        holder.txtSinopsis.setText(dataList.get(position).getSinopsis());
        holder.imagePoster.setImageBitmap(convertToBitmap(dataList.get(position).getImage()));
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long movieId = dataList.get(position).getId();
                Intent i = new Intent(v.getContext(), MovieDetail.class);
                i.putExtra("movieId", movieId);
                v.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul, txtRating, txtSinopsis;
        private ImageView imagePoster;
        private Button btnDetail;

        public MovieViewHolder(View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtRating = itemView.findViewById(R.id.txt_rating);
            txtSinopsis = itemView.findViewById(R.id.txt_sinopsis);
            imagePoster = itemView.findViewById(R.id.image_poster);
            btnDetail = itemView.findViewById(R.id.buttonDetail);
        }
    }

    private Bitmap convertToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}
