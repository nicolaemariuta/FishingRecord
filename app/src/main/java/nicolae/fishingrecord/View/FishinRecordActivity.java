package nicolae.fishingrecord.View;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import nicolae.fishingrecord.Injection.ActivityComponent;
import nicolae.fishingrecord.Injection.ActivityModule;
import nicolae.fishingrecord.Injection.DaggerActivityComponent;
import nicolae.fishingrecord.Injection.HasComponent;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

public abstract class FishinRecordActivity extends AppCompatActivity implements LifecycleProvider<ActivityEvent>, HasComponent<ActivityComponent> {


    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    protected ActivityComponent activityComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        Timber.tag("Activity__onCreate").d(this.getClass().getSimpleName());
        super.onCreate(savedInstanceState, persistentState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    @Override
    protected void onStart() {
        Timber.tag("Activity__onStart").d(this.getClass().getSimpleName());
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onStop() {
        Timber.tag("Activity__onStop").d(this.getClass().getSimpleName());
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onPause() {
        Timber.tag("Activity__onPause").d(this.getClass().getSimpleName());
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onResume() {
        Timber.tag("Activity__onResume").d(this.getClass().getSimpleName());
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onDestroy() {
        Timber.tag("Activity__onDestroy").d(this.getClass().getSimpleName());
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }


    @Override
    public ActivityComponent getComponent() {
        if (activityComponent == null) {
            this.activityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(FishRecordApplication.getComponent())
                    .activityModule(new ActivityModule(this)).build();
        }
        return activityComponent;
    }




}
