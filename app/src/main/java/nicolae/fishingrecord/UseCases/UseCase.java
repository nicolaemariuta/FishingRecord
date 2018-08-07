package nicolae.fishingrecord.UseCases;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

public abstract class UseCase<T, Params> {

    @Inject
    BehaviorSubject<ActivityEvent> lifecycleSubject;

    private Subscription subscription;

    public void execute(Params params, final Observer<T> observer) {
        execute(params, observer, ActivityEvent.PAUSE);
    }

    public void execute(Params params, final Observer<T> observer, final ActivityEvent bindUntilEvent) {

        // https://stackoverflow.com/questions/35951942/single-observable-with-multiple-subscribers/35952390#35952390
        ConnectableObservable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .share()
                .replay();


        // We create multiple subscribers in order for the observer of the presenter to stop receiving events when
        // the fragment goes on pause
        subscription = observable
                .compose((LifecycleTransformer<T>) RxLifecycle.bindUntilEvent(lifecycleSubject, bindUntilEvent))
                .subscribe(observer);

        // We create the second subscriber to be sure that we handle any errors that occur after the fragment has paused.
        // This subscriber is only concerned about error handling.
        observable
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e(throwable, "Problems in observable.");

                        if (hasClientStoppedSubscribing(bindUntilEvent, lifecycleSubject.getValue()))
                            logAndSendError(throwable);

                    }
                })
                .onErrorResumeNext(Observable.<T>empty())
                .subscribe();

        observable.connect();
    }

    private boolean hasClientStoppedSubscribing(ActivityEvent bindUntilEvent, ActivityEvent stateOfLifecycle) {
        if (bindUntilEvent == ActivityEvent.PAUSE && (stateOfLifecycle == ActivityEvent.PAUSE || stateOfLifecycle == ActivityEvent.STOP || stateOfLifecycle == ActivityEvent.DESTROY)) {
            return true;
        }
        if (bindUntilEvent == ActivityEvent.STOP && (stateOfLifecycle == ActivityEvent.STOP || stateOfLifecycle == ActivityEvent.DESTROY)) {
            return true;
        }
        if (bindUntilEvent == ActivityEvent.DESTROY && stateOfLifecycle == ActivityEvent.DESTROY) {
            return true;
        }
        return false;
    }

    private void logAndSendError(Throwable throwable) {
        Timber.d("An error occurred after the subscriber has unsubscribed: " + throwable.getMessage());
    }


    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    public abstract Observable<T> buildUseCaseObservable(Params params);

}
