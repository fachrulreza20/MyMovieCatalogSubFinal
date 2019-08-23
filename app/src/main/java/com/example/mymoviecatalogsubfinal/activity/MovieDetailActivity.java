package com.example.mymoviecatalogsubfinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymoviecatalogsubfinal.database.MovieHelper;
import com.example.mymoviecatalogsubfinal.myclass.Movie;
import com.example.mymoviecatalogsubfinal.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity  implements View.OnClickListener {

    TextView name, tvIsFavorite, desc, runtime, releaseDate;
    ImageView myImageView, imgFavorit;
    private ProgressBar progressBar;

    public static final String EXTRA_MOVIE = "extra movie";
    public static final String EXTRA_POSITION = "extra_position";

    public static final int RESULT_ADD = 101;
    public static final int RESULT_DELETE = 301;

    private static final String EXTRA_STATE = "EXTRA_STATE";
    private Movie movie;
    private int position;
    private MovieHelper moviehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        name = findViewById(R.id.tvName);
        tvIsFavorite = findViewById(R.id.tvIsFavorite);
        desc = findViewById(R.id.tvDesc);
        runtime = findViewById(R.id.tvPopularity);
        releaseDate = findViewById(R.id.tvReleaseDate);
        myImageView = findViewById(R.id.imgVIew);
        progressBar = findViewById(R.id.progressBar);
        imgFavorit = findViewById(R.id.imgFavorite);

        moviehelper = MovieHelper.getInstance(getApplicationContext());
        moviehelper.open();
        showLoading(true);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            movie = new Movie();
        }

        Log.d("eeeeeeeeeee  ", "onCreate: movieeeeeeid "+movie.getId());

        int x = moviehelper.checkIsFavorited(movie.getId());

        if(x == 0){
            Glide.with(this)
                    .load(R.drawable.icon_unfavorite)
                    .apply(new RequestOptions().override(36, 36))
                    .into(imgFavorit);
            tvIsFavorite.setText(getString(R.string .setasfavorite));
        }
        else{
            Glide.with(this)
                    .load(R.drawable.icon_favorite)
                    .apply(new RequestOptions().override(36, 36))
                    .into(imgFavorit);
            tvIsFavorite.setText(getString(R.string .favorite));
        }



        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.appbartitle_movie);
        }

        name.setText((movie.getName().toUpperCase()));
        desc.setText(movie.getDescription());
        releaseDate.setText(getString(R.string.text_releasedate, movie.getReleasedate()));
        runtime.setText(getString(R.string.text_runtime, movie.getPopularity()));

        Glide.with(this)
                .load(movie.getPhoto())
                .apply(new RequestOptions().override(600,600))
                .into(myImageView);

        showLoading(false);

        imgFavorit.setOnClickListener(this);

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imgFavorite) {

            movie.setName(name.getText().toString());
            movie.setId(movie.getId());
            movie.setDescription(desc.getText().toString());
            movie.setReleasedate(movie.getReleasedate());

            Intent intent = new Intent();
            intent.putExtra(EXTRA_MOVIE, movie);
            intent.putExtra(EXTRA_POSITION, position);

            long result = moviehelper.insertFavMovie(movie);

            if (result > 0) {
                movie.setId((int) result);
                setResult(RESULT_ADD, intent);
                Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show();
                Glide.with(this)
                        .load(R.drawable.icon_favorite)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorit);
                tvIsFavorite.setText("Favorite");

            } else {
                Glide.with(this)
                        .load(R.drawable.icon_unfavorite)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorit);
                tvIsFavorite.setText("Set as Favorite");

                long resultx = moviehelper.deleteFavMovie(movie.getId());
                if (resultx > 0) {
                    Intent intentx = new Intent();
                    intentx.putExtra(EXTRA_POSITION, position);
                    setResult(RESULT_DELETE, intentx);
                    Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                }

            }

//            sintax delete all ubah >= id = 0
//            long resultx = moviehelper.deleteFavMovie(1);
//            if (resultx > 0) {
//                Intent intentx = new Intent();
//                intentx.putExtra(EXTRA_POSITION, position);
//                setResult(RESULT_DELETE, intentx);
//            }


        }

    }
    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListNotes());
    }
}
