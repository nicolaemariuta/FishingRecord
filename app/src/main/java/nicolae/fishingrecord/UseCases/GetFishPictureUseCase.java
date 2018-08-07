package nicolae.fishingrecord.UseCases;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.Model.DataService;
import rx.Observable;

public class GetFishPictureUseCase extends UseCase<FishPicture, Integer> {

    private final DataService dataService;

    @Inject
    public GetFishPictureUseCase(DataService dataService) {
        this.dataService = dataService;
    }


    @Override
    public Observable<FishPicture> buildUseCaseObservable(Integer fishPictureId) {
        return dataService.getFishPicture(fishPictureId);
    }


}
