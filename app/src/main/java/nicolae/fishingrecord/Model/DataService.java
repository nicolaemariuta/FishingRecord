package nicolae.fishingrecord.Model;

import java.util.List;

import nicolae.fishingrecord.Data.FishPicture;
import rx.Observable;

public interface DataService {

    Observable<FishPicture> addFishPicture(FishPicture fishPicture);

    Observable<Void> deleteFishPicture(FishPicture fishPicture);

    Observable<FishPicture> getFishPicture(Integer id);

    Observable<List<FishPicture>> getAllFishPictures();

    Observable<Void> updateFishPicture(FishPicture fishPicture);

}
