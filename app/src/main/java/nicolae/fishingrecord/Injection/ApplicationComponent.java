package nicolae.fishingrecord.Injection;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import nicolae.fishingrecord.Model.AppDbHandler;
import nicolae.fishingrecord.Model.DataService;
import nicolae.fishingrecord.View.FishRecordApplication;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    DataService dataService();

    AppDbHandler appDbHandler();

    void inject(FishRecordApplication application);

}
