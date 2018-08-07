package nicolae.fishingrecord.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.Presenter.ViewFishPhotoPresenter;
import nicolae.fishingrecord.R;

public class ViewFishPhotoActivity extends FishinRecordActivity implements ViewFishPhotoPresenter.ViewFishPhotoListener, UpdateFishPhotoDataDialog.UpdateFishPhotoDialogListener {


    public static final String FISH_PHOTO_TO_DISPLAY = "FishPhotoToDisplay";
    public static final String NEW_FISH_PHOTO = "NewFishPhoto";



    private FloatingActionButton mEditFishPictureData;

    @Inject
    ViewFishPhotoPresenter presenter;

    private PhotoView mFishPhoto;
    private FloatingActionButton mEditPhotoData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fish_photo);
        getComponent().inject(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



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
    public void displayPicture(FishPicture fishPicture) {

        String urlStr = Uri.parse(fishPicture.getImagePath()).buildUpon().build().toString();
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
        UpdateFishPhotoDataDialog dialog = UpdateFishPhotoDataDialog.newInstance(currentFishPicture);
        dialog.show(getSupportFragmentManager(), UpdateFishPhotoDataDialog.TAG);
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
