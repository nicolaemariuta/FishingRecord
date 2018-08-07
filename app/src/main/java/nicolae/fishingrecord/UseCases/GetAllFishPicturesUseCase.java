package nicolae.fishingrecord.UseCases;

import java.util.List;

import javax.inject.Inject;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.Model.DataService;
import rx.Observable;

public class GetAllFishPicturesUseCase extends UseCase< List<FishPicture>, Void> {

    private final DataService dataService;

    @Inject
    public GetAllFishPicturesUseCase(DataService dataService) {
        this.dataService = dataService;
    }


    @Override
    public Observable<List<FishPicture>> buildUseCaseObservable(Void v) {
        return dataService.getAllFishPictures();
    }

}
