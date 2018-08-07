package nicolae.fishingrecord.Presenter;

import com.trello.rxlifecycle.android.ActivityEvent;

import javax.inject.Inject;

import rx.subjects.BehaviorSubject;
import timber.log.Timber;

public abstract class BasePresenter implements Presenter {

    @Inject
    BehaviorSubject<ActivityEvent> lifecycleSubject;

    @Override
    public void start() {
        Timber.d("start");
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    public void resume() {
        Timber.d("resume");
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    public void pause() {
        Timber.d("pause");
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    public void stop() {
        Timber.d("stop");
        lifecycleSubject.onNext(ActivityEvent.STOP);
    }

}
