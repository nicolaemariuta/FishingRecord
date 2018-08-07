package nicolae.fishingrecord.Injection;

import android.app.Activity;

import dagger.Component;
import nicolae.fishingrecord.View.MainActivity;
import nicolae.fishingrecord.View.ViewFishPhotoActivity;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 * <p>
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link com.fernandocejas.android10.sample.presentation.internal.di.PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity activity();

    void inject(MainActivity mainActivity);

    void inject(ViewFishPhotoActivity viewFishPhotoActivity);


}
