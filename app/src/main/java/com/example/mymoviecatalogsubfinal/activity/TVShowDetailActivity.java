package com.example.mymoviecatalogsubfinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymoviecatalogsubfinal.database.TvsHelper;
import com.example.mymoviecatalogsubfinal.myclass.TVShow;
import com.example.mymoviecatalogsubfinal.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TVShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView name, tvIsFavorite2, desc, runtime, releaseDate;
    ImageView myImageView, imgFavorit2;
    private ProgressBar progressBar;

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    public static final String EXTRA_POSITION = "extra_position";

    public static final int RESULT_ADD = 102;
    public static final int RESULT_DELETE = 302;

    private static final String EXTRA_STATE = "EXTRA_STATE";
    private TVShow tvshow;
    private int position;
    private TvsHelper tvshelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);

        name = findViewById(R.id.tvName_tvshow);
        tvIsFavorite2 = findViewById(R.id.tvIsFavorite2);
        desc = findViewById(R.id.tvDesc_tvshow);
        runtime = findViewById(R.id.tvPopularity_tvshow);
        releaseDate = findViewById(R.id.tvReleaseDate_tvshow);
        myImageView = findViewById(R.id.imgVIew_tvshow);
        progressBar = findViewById(R.id.progressBar);
        imgFavorit2 = findViewById(R.id.imgFavorite2);

        tvshelper = TvsHelper.getInstance(getApplicationContext());
        tvshelper.open();


        tvshow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        if (tvshow != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            tvshow = new TVShow();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.appbartitle_tvshow);
        }

        int x = tvshelper.checkIsFavorited(tvshow.getId());

        if (x == 0) {
            Glide.with(this)
                    .load(R.drawable.icon_unfavorite)
                    .apply(new RequestOptions().override(600, 600))
                    .into(imgFavorit2);
            tvIsFavorite2.setText(getString(R.string .setasfavoritetv));
        } else {
            Glide.with(this)
                    .load(R.drawable.icon_favorite)
                    .apply(new RequestOptions().override(600, 600))
                    .into(imgFavorit2);
            tvIsFavorite2.setText(getString(R.string .favoritetv));
        }
        name.setText((tvshow.getName_tvshow().toUpperCase()));
        desc.setText(tvshow.getDescription_tvshow());
        runtime.setText(getString(R.string.text_runtime, tvshow.getPopularity_tvshow()));
        releaseDate.setText(getString(R.string.text_releasedate, tvshow.getReleasedate_tvshow()));

        Glide.with(this)
                .load(tvshow.getPhoto_tvshow())
                .apply(new RequestOptions().override(600, 600))
                .into(myImageView);

        imgFavorit2.setOnClickListener(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imgFavorite2) {

            tvshow.setName_tvshow(tvshow.getName_tvshow());
            tvshow.setId(tvshow.getId());
            tvshow.setDescription_tvshow(tvshow.getDescription_tvshow());
            tvshow.setReleasedate_tvshow(tvshow.getReleasedate_tvshow());

            Intent intent = new Intent();
            intent.putExtra(EXTRA_TVSHOW, tvshow);
            intent.putExtra(EXTRA_POSITION, position);

            long result = tvshelper.insertFavTvshow(tvshow);

            if (result > 0) {
                tvshow.setId((int) result);
                setResult(RESULT_ADD, intent);
                Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show();
                Glide.with(this)
                        .load(R.drawable.icon_favorite)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorit2);
                tvIsFavorite2.setText("Favorite");

            } else {
                Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                Glide.with(this)
                        .load(R.drawable.icon_unfavorite)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorit2);
                tvIsFavorite2.setText("Set as Favorite");

                long resultx = tvshelper.deleteFavTvshow(tvshow.getId());
                if (resultx > 0) {
                    Intent intentx = new Intent();
                    intentx.putExtra(EXTRA_POSITION, position);
                    setResult(RESULT_DELETE, intentx);
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

















