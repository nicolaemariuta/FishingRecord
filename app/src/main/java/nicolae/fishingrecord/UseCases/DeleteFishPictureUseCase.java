package nicolae.fishingrecord.UseCases;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.Model.DataService;
import rx.Observable;

public class DeleteFishPictureUseCase extends UseCase<Void, FishPicture> {

    private final DataService dataService;

    @Inject
    public DeleteFishPictureUseCase(DataService dataService) {
        this.dataService = dataService;
    }


    @Override
    public Observable<Void> buildUseCaseObservable(FishPicture fishPicture) {
        return dataService.deleteFishPicture(fishPicture);
    }




}
