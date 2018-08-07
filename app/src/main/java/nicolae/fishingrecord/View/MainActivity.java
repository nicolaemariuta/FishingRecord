package nicolae.fishingrecord.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.Presenter.MainActivityPresenter;
import nicolae.fishingrecord.R;
import nicolae.fishingrecord.Utils.DividerItemDecorator;

public class MainActivity extends FishinRecordActivity implements MainActivityPresenter.MainActivityListener, FishPhotosListAdapter.FishPhotosListListener {


    private RecyclerView mRecyclerView;
    private FishPhotosListAdapter adapter;

    private FloatingActionButton mTakePicture;
    private FloatingActionButton mUploadPicture;

    private static int RESULT_IMPORT_IMAGE = 1;
    private static int RESULT_CAMERA_REQUEST = 2;

    @Inject
    Navigator navigator;

    @Inject
    MainActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter.init(this);

        mTakePicture = (FloatingActionButton) findViewById(R.id.fabTakePicture);
        mUploadPicture = (FloatingActionButton) findViewById(R.id.fabUploadPicture);

        mTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onTakeFishPictureClicked();
            }
        });

        mUploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onImportFishPictureClicked();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.photosSequenceView);
        mRecyclerView.addItemDecoration(new DividerItemDecorator(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Check and ask for permissions in version Android API 23 and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
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
    public void updateFishingPhotosList(List<FishPicture> fishPictures) {

        if(adapter == null)
            adapter = new FishPhotosListAdapter(this, fishPictures, this);
        else
            adapter.setFishPictures(fishPictures);

        if (mRecyclerView != null)
            mRecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void importFishPicture() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_IMPORT_IMAGE);
    }

    @Override
    public void takePictureUsingCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, RESULT_CAMERA_REQUEST);
    }

    @Override
    public void showDisplayPictureScreen(FishPicture fishPicture) {
        navigator.navigateToViewFishPhotoActivity(this, fishPicture, true);
    }

    @Override
    public void deletePictureCompleted(FishPicture fishPicture) {
        adapter.deletePicture(fishPicture);
    }

    @Override
    public void clickedFishPicture(FishPicture fishPicture) {
        navigator.navigateToViewFishPhotoActivity(this, fishPicture, false);
    }

    @Override
    public void deleteFishPicture(FishPicture fishPicture) {
        presenter.onDeletePicture(fishPicture);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_IMPORT_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            presenter.onNewPictureImported(new FishPicture(getRealPathFromImageUri(this, selectedImage)));
        }

        if (requestCode == RESULT_CAMERA_REQUEST && resultCode == RESULT_OK && null != data) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String imageUrl = MediaStore.Images.Media.insertImage(getContentResolver(), photo, "FishingRecord" ,"FishingRecord" );
            Uri savedImageURI = Uri.parse(imageUrl);
            presenter.onNewPictureImported(new FishPicture(getRealPathFromImageUri(this, savedImageURI)));
        }
    }

    private static String getRealPathFromImageUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private void checkPermissions(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA

                    },
                    1052);
        }

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1052: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    //TODO: Handle permissions given

                } else {
                    //TODO: Handle permissions not given
                }
                return;
            }
        }
    }

}
