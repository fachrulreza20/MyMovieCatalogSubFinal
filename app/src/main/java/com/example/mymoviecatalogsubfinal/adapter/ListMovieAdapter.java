package com.example.mymoviecatalogsubfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mymoviecatalogsubfinal.activity.MovieDetailActivity;
import com.example.mymoviecatalogsubfinal.myclass.Movie;
import com.example.mymoviecatalogsubfinal.R;

import java.util.ArrayList;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.CategoryViewHolder> {


    private ArrayList<Movie> mData = new ArrayList<>();

    public void setListMovie(ArrayList<Movie> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getListMovies() {
        return mData;
    }
    private Context context;
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListMovieAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new CategoryViewHolder(itemRow);


    }

    @Override
    public void onBindViewHolder(@NonNull final ListMovieAdapter.CategoryViewHolder holder, int position) {
        holder.bind(mData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Movie movie = mData.get(holder.getAdapterPosition());
                Intent moveWithObjectIntent = new Intent(getContext(), MovieDetailActivity.class);
                moveWithObjectIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                context.startActivity(moveWithObjectIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDescription;
        ImageView imgPhoto;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_name);
            tvDescription = itemView.findViewById(R.id.txt_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }


        void bind(Movie movieItems) {
            tvName.setText(movieItems.getName());
            tvDescription.setText(movieItems.getDescription());
//            tvId.setText(movieItems.getId());

            Glide.with(context)
                    .load(movieItems.getPhoto())
                    .into(imgPhoto);

        }

    }
}
