package nicolae.fishingrecord.Presenter;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.UseCases.AddFishPictureUseCase;
import nicolae.fishingrecord.UseCases.UpdateFishPictureUseCase;
import rx.Subscriber;

public class ViewFishPicturePresenter extends BasePresenter{

    private ViewFishPhotoListener listener;
    private FishPicture currentFishPicture;
    private boolean isNewPicture;

    private final AddFishPictureUseCase addFishPictureUseCase;
    private final UpdateFishPictureUseCase updateFishPictureUseCase;

    @Inject
    public ViewFishPicturePresenter(AddFishPictureUseCase addFishPictureUseCase, UpdateFishPictureUseCase updateFishPictureUseCase) {
        this.addFishPictureUseCase = addFishPictureUseCase;
        this.updateFishPictureUseCase = updateFishPictureUseCase;
    }


    public void init(ViewFishPhotoListener listener, FishPicture fishPicture, Boolean isNewPicture) {
        this.listener = listener;
        this.currentFishPicture = fishPicture;
        this.isNewPicture = isNewPicture;

        listener.displayPicture(fishPicture);

        if(isNewPicture)
            listener.showEditPhotoDataDialog(currentFishPicture);
    }

    public void editPhotoClicked(){
        listener.showEditPhotoDataDialog(currentFishPicture);
    }


    public void fishPhotoDataChanged(FishPicture fishPicture) {

        if(isNewPicture){
            addFishPictureUseCase.execute(fishPicture, new Subscriber<FishPicture>() {
                @Override
                public void onCompleted() {
                    isNewPicture = false;
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(FishPicture fishPicture) {
                    ViewFishPicturePresenter.this.currentFishPicture = fishPicture;
                    listener.showPictureDataSavedSuccessful();
                }
            });

        } else {
            updateFishPictureUseCase.execute(fishPicture, new Subscriber<Void>() {
                @Override
                public void onCompleted() {
                    isNewPicture = false;
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(Void aVoid) {
                    listener.showPictureDataSavedSuccessful();
                }
            });
        }
    }

    public interface ViewFishPhotoListener {

        void displayPicture(FishPicture fishPicture);

        void showEditPhotoDataDialog(FishPicture currentFishPicture);

        void showPictureDataSavedSuccessful();
    }



}
