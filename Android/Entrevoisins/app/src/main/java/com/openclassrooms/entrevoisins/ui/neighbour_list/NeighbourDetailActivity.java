package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.button.MaterialButton;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout;

import static android.widget.Toast.LENGTH_SHORT;

public class NeighbourDetailActivity extends AppCompatActivity {

    private Neighbour mNeighbour;
    private NeighbourApiService mApiService;

    @BindView(R.id.seeAvatar)
    ImageView mShowAvatar;
    @BindView(R.id.seeProfil)
    TextView mShowProfil;
    @BindView(R.id.seeName)
    TextView mShowName;
    @BindView(R.id.seeLocation)
    TextView mShowLocation;
    @BindView(R.id.seePhone)
    TextView mShowPhone;
    @BindView(R.id.seeContact)
    TextView mShowContact;
    @BindView(R.id.titleAboutMe)
    TextView mShowTitleAboutMe;
    @BindView(R.id.seeAboutMe)
    TextView mShowAboutMe;

    @BindView(R.id.addFavouriteFBA)
    FloatingActionButton mAddFavourite;

    private boolean isFavori;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);
        ButterKnife.bind(this);

        setSupportActionBar(findViewById(R.id.toolbarDet));
        findViewById(R.id.appbar2).bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);


        //getSupportActionBar().setElevation(0);

        mApiService = DI.getNeighbourApiService();
        mNeighbour = (Neighbour) getIntent().getSerializableExtra("cardneighbour");
        isFavori = mNeighbour.isIsfavorite();

        if(isFavori) {
            mAddFavourite.setImageResource(R.drawable.ic_star_white_24dp);
            //mAddFavourite.setRippleColor(int);
            //Context context = getApplicationContext();
            //Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();
        }
        else{
            mAddFavourite.setImageResource(R.drawable.ic_star_border_white_24dp);
            // Context context = getApplicationContext();
            // Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
            // isFavori = false;
        }

        mShowProfil.setText(mNeighbour.getName());
        mShowName.setText(mNeighbour.getName());
        mShowLocation.setText(mNeighbour.getAddress());
        mShowPhone.setText(mNeighbour.getPhoneNumber());
        mShowContact.setText("www.facebook.fr /" + mNeighbour.getName().toLowerCase());
        mShowAboutMe.setText(mNeighbour.getAboutMe());
        Glide.with(this).load(mNeighbour.getAvatarUrl()).into(mShowAvatar);


        //public static Intent navigate(AppCompatActivity activity) {
        //     Intent intent = new Intent(activity, NeighbourDetailActivity.class);
        //    //put argument
        //     return intent;
        // }


        mAddFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavori) {
                    isFavori = false;
                    mAddFavourite.setImageResource(R.drawable.ic_star_border_white_24dp);
                    //Context context = getApplicationContext();
                    //Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();
                    mApiService.removeFavorite(mNeighbour);
                }
                else{
                    isFavori = true;
                    mAddFavourite.setImageResource(R.drawable.ic_star_white_24dp);
                    // Context context = getApplicationContext();
                    // Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
                    mApiService.addFavorite(mNeighbour);
                }

            }

        });

    }
}