package nicolae.fishingrecord.UseCases;

import java.util.List;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.Model.DataService;
import rx.Observable;

public class UpdateFishPictureUseCase extends UseCase<Void, FishPicture> {

    private final DataService dataService;

    @Inject
    public UpdateFishPictureUseCase(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public Observable<Void> buildUseCaseObservable(FishPicture fishPicture) {
        return dataService.updateFishPicture(fishPicture);
    }

}