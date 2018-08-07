package nicolae.fishingrecord.Injection;

import android.app.Activity;

import com.trello.rxlifecycle.android.ActivityEvent;

import dagger.Module;
import dagger.Provides;
import rx.subjects.BehaviorSubject;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity activity() {
        return this.activity;
    }


    @Provides
    @PerActivity
    public BehaviorSubject<ActivityEvent> provideLifecycleSubject() {
        return BehaviorSubject.create();
    }

}
