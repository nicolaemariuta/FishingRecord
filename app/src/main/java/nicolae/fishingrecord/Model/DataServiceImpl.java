package nicolae.fishingrecord.Model;


import java.util.List;

import nicolae.fishingrecord.Data.FishPicture;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

public class DataServiceImpl implements DataService {


    private final AppDbHandler appDbHandler;

    public DataServiceImpl(AppDbHandler appDbHandler) {
        this.appDbHandler = appDbHandler;
    }

    @Override
    public Observable<FishPicture> addFishPicture(final FishPicture fishPicture) {
        return Observable.create(new Observable.OnSubscribe<FishPicture>() {
            @Override
            public void call(Subscriber<? super FishPicture> subscriber) {
                try{
                    //I need to return the FishPicture because now it contains the id
                    // from the database which will be needed for updating the entry
                    subscriber.onNext(appDbHandler.addFishPicture(fishPicture));
                } catch (Exception e){
                    Timber.e(e, "Problem adding a new FishPicture entry into DataBase" + e.getMessage());
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }

            }
        });
    }

    @Override
    public Observable<Void> deleteFishPicture(final FishPicture fishPicture) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try{
                    appDbHandler.deleteFishPicture(fishPicture);
                    subscriber.onNext(null);
                } catch (Exception e){
                    Timber.e(e, "Problem adding a new FishPicture entry into DataBase" + e.getMessage());
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }

            }
        });
    }

    @Override
    public Observable<FishPicture> getFishPicture(final Integer id) {
        return Observable.create(new Observable.OnSubscribe<FishPicture>() {
            @Override
            public void call(Subscriber<? super FishPicture> subscriber) {
                try{
                    FishPicture fishPicture = appDbHandler.getFishPicture(id);
                    subscriber.onNext(fishPicture);
                } catch (Exception e){
                    Timber.e(e, "Problem fetching the FishPicture with id:" + id + e.getMessage());
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }

            }
        });
    }

    @Override
    public Observable<List<FishPicture>> getAllFishPictures() {
        return Observable.create(new Observable.OnSubscribe<List<FishPicture>>() {
            @Override
            public void call(Subscriber<? super List<FishPicture>> subscriber) {
                try{
                    List<FishPicture> fishPictures = appDbHandler.getAllFishPictures();
                    subscriber.onNext(fishPictures);
                } catch (Exception e){
                    Timber.e(e, "Problem fetching all the FishPictures" + e.getMessage());
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }

            }
        });
    }

    @Override
    public Observable<Void> updateFishPicture(final FishPicture fishPicture) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try{
                    appDbHandler.updateFishPicture(fishPicture);
                    subscriber.onNext(null);
                } catch (Exception e){
                    Timber.e(e, "Problem adding a new FishPicture entry into DataBase" + e.getMessage());
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }

            }
        });
    }
}
