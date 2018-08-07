package nicolae.fishingrecord.Presenter;

import java.util.List;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.UseCases.DeleteFishPictureUseCase;
import nicolae.fishingrecord.UseCases.GetAllFishPicturesUseCase;
import rx.Observer;
import rx.Subscriber;

public class MainActivityPresenter extends BasePresenter {

    private MainActivityListener listener;

    private final GetAllFishPicturesUseCase getAllFishPicturesUseCase;
    private final DeleteFishPictureUseCase deleteFishPictureUseCase;

    @Inject
    public MainActivityPresenter(GetAllFishPicturesUseCase getAllFishPicturesUseCase, DeleteFishPictureUseCase deleteFishPictureUseCase) {
        this.getAllFishPicturesUseCase = getAllFishPicturesUseCase;
        this.deleteFishPictureUseCase = deleteFishPictureUseCase;
    }

    public void init(final MainActivityListener listener){
        this.listener = listener;
    }

    @Override
    public void resume() {
        super.resume();
        updateFishPicturesList();
    }


    private void updateFishPicturesList(){
        getAllFishPicturesUseCase.execute(null, new Observer<List<FishPicture>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.showError("Error retrieving the list of FishPictures");
            }

            @Override
            public void onNext(List<FishPicture> fishPictures) {
                listener.updateFishingPhotosList(fishPictures);
            }
        });
    }




    public void onDeletePicture(final FishPicture picture) {
        deleteFishPictureUseCase.execute(picture, new Subscriber<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.showError("Error deleting the fish picture");
            }

            @Override
            public void onNext(Void aVoid) {
                listener.deletePictureCompleted(picture);
            }
        });
    }

    public void onImportFishPictureClicked() {
        listener.importFishPicture();
    }


    public void onTakeFishPictureClicked() {
        listener.takePictureUsingCamera();
    }

    public void onNewPictureImported(FishPicture fishPicture) {
        listener.showDisplayPictureScreen(fishPicture);
    }



    public interface MainActivityListener {

        void updateFishingPhotosList(List<FishPicture> fishPictures);

        void showError(String error);

        void importFishPicture();

        void takePictureUsingCamera();

        void showDisplayPictureScreen(FishPicture fishPicture);

        void deletePictureCompleted(FishPicture fishPicture);

    }


}
