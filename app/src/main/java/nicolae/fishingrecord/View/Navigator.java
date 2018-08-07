package nicolae.fishingrecord.View;

import android.app.Activity;
import android.content.Intent;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static nicolae.fishingrecord.View.ViewFishPictureActivity.FISH_PHOTO_TO_DISPLAY;
import static nicolae.fishingrecord.View.ViewFishPictureActivity.NEW_FISH_PHOTO;

public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    public void navigateToMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    public void navigateToViewFishPhotoActivity(Activity activity, FishPicture fishPicture, Boolean isNewPicture) {
        Intent intent = new Intent(activity, ViewFishPictureActivity.class);
        intent.putExtra(FISH_PHOTO_TO_DISPLAY, fishPicture);
        intent.putExtra(NEW_FISH_PHOTO, isNewPicture);
        activity.startActivity(intent);
    }


}
