package nicolae.fishingrecord.UseCases;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.Model.DataService;
import rx.Observable;

public class AddFishPictureUseCase extends UseCase<FishPicture, FishPicture> {

    private final DataService dataService;

    @Inject
    public AddFishPictureUseCase(DataService dataService) {
        this.dataService = dataService;
    }


    @Override
    public Observable<FishPicture> buildUseCaseObservable(FishPicture fishPicture) {
        return dataService.addFishPicture(fishPicture);
    }
}
