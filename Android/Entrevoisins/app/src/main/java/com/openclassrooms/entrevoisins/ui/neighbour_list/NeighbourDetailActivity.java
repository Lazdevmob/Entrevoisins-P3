package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter.CLICKED_NEIGHBOUR;

public class NeighbourDetailActivity extends AppCompatActivity {

    private Neighbour cloneNeighbour;
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
    FloatingActionButton mFabFavourite;

    private boolean isFavori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);
        ButterKnife.bind(this);

        setSupportActionBar(findViewById(R.id.toolbarDet));
        findViewById(R.id.appbar2).bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mApiService = DI.getNeighbourApiService();
        cloneNeighbour = (Neighbour) getIntent().getSerializableExtra(CLICKED_NEIGHBOUR);
        isFavori = cloneNeighbour.isFavorite();

/**afficher etoile favori ou non si neighbour est favori
         * */
        if (isFavori) {
            mFabFavourite.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
        else {
            mFabFavourite.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        }

        mShowProfil.setText(cloneNeighbour.getName());
        mShowName.setText(cloneNeighbour.getName());
        mShowLocation.setText(cloneNeighbour.getAddress());
        mShowPhone.setText(cloneNeighbour.getPhoneNumber());
        mShowContact.setText("www.facebook.fr /" + cloneNeighbour.getName().toLowerCase());
        mShowAboutMe.setText(cloneNeighbour.getAboutMe());
        Glide.with(this).load(cloneNeighbour.getAvatarUrl()).into(mShowAvatar);


/** au clic FAB changement etat etoile favori ou non si neighbour est favori
 *changement etat variable favori du neighbour
 * */
        mFabFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavori) {
                    isFavori = false;
                    mFabFavourite.setImageResource(R.drawable.ic_star_border_yellow_24dp);
                    mApiService.removeFavorite(cloneNeighbour);
                } else {
                    isFavori = true;
                    mFabFavourite.setImageResource(R.drawable.ic_star_yellow_24dp);
                    mApiService.addFavorite(cloneNeighbour);
                }
            }
        });
    }
}