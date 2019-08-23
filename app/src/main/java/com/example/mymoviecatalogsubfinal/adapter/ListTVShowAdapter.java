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
import com.example.mymoviecatalogsubfinal.activity.TVShowDetailActivity;
import com.example.mymoviecatalogsubfinal.myclass.TVShow;
import com.example.mymoviecatalogsubfinal.R;

import java.util.ArrayList;

public class ListTVShowAdapter extends RecyclerView.Adapter<ListTVShowAdapter.CategoryViewHolder> {

    private ArrayList<TVShow> mDataTVShow = new ArrayList<>();

    public void setListTVShow(ArrayList<TVShow> items){

        mDataTVShow.clear();
        mDataTVShow.addAll(items);
        notifyDataSetChanged();
    }

    private Context context;

    public Context getContext() {
        return context;
    }

    public ArrayList<TVShow> getListTvshow() {
        return mDataTVShow;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListTVShowAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new CategoryViewHolder(itemRow);


    }

    @Override
    public void onBindViewHolder(@NonNull ListTVShowAdapter.CategoryViewHolder holder, final int position) {
        holder.bind(mDataTVShow.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TVShow tvshow = mDataTVShow.get(position);
                Intent moveWithObjectIntent = new Intent(getContext(), TVShowDetailActivity.class);
                moveWithObjectIntent.putExtra(TVShowDetailActivity.EXTRA_TVSHOW, tvshow);
                context.startActivity(moveWithObjectIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataTVShow.size();
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

        void bind(TVShow tvshowItems) {
            tvName.setText(tvshowItems.getName_tvshow());
            tvDescription.setText(tvshowItems.getDescription_tvshow());

            Glide.with(context)
                    .load(tvshowItems.getPhoto_tvshow())
                    .into(imgPhoto);

        }
    }
}
