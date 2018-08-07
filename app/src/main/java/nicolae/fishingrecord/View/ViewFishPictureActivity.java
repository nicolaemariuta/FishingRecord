package nicolae.fishingrecord.View;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.Presenter.ViewFishPicturePresenter;
import nicolae.fishingrecord.R;

public class ViewFishPictureActivity extends FishinRecordActivity implements ViewFishPicturePresenter.ViewFishPhotoListener, UpdateFishPictureDataDialog.UpdateFishPhotoDialogListener {


    public static final String FISH_PHOTO_TO_DISPLAY = "FishPhotoToDisplay";
    public static final String NEW_FISH_PHOTO = "NewFishPhoto";



    private FloatingActionButton mEditFishPictureData;

    @Inject
    ViewFishPicturePresenter presenter;

    private PhotoView mFishPhoto;
    private FloatingActionButton mEditPhotoData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fish_photo);
        getComponent().inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mFishPhoto = (PhotoView) findViewById(R.id.fishPicture);
        mEditPhotoData = (FloatingActionButton) findViewById(R.id.edit_fishPicture);

        mEditPhotoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.editPhotoClicked();
            }
        });


        FishPicture fishPicture = (FishPicture) getIntent().getSerializableExtra(FISH_PHOTO_TO_DISPLAY);
        Boolean isNewPicture = getIntent().getBooleanExtra(NEW_FISH_PHOTO, false);

        presenter.init(this, fishPicture, isNewPicture);

    }

    @Override
    protected void onPause() {
        presenter.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        presenter.stop();
        super.onStop();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void displayPicture(FishPicture fishPicture) {

        String urlStr = Uri.parse(fishPicture.getImageUrl()).buildUpon().build().toString();
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        urlStr = "file:" + urlStr;
        builder.build().load(urlStr).into(mFishPhoto);


    }



    @Override
    public void showEditPhotoDataDialog(FishPicture currentFishPicture) {
        UpdateFishPictureDataDialog dialog = UpdateFishPictureDataDialog.newInstance(currentFishPicture);
        dialog.show(getSupportFragmentManager(), UpdateFishPictureDataDialog.TAG);
    }

    @Override
    public void showPictureDataSavedSuccessful() {
        Toast.makeText(getApplicationContext(), getString(R.string.view_picture_data_saved), Toast.LENGTH_LONG).show();
    }


    @Override
    public void fishDataSaved(FishPicture fishPicture) {

        presenter.fishPhotoDataChanged(fishPicture);

    }
}
