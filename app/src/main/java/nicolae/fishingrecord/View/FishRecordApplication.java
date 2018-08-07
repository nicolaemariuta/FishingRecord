package nicolae.fishingrecord.View;

import android.app.Application;
import android.content.Context;

import nicolae.fishingrecord.Injection.ApplicationComponent;
import nicolae.fishingrecord.Injection.ApplicationModule;
import nicolae.fishingrecord.Injection.DaggerApplicationComponent;

public class FishRecordApplication extends Application {

    private static FishRecordApplication instance;
    private ApplicationComponent mComponent;



    public FishRecordApplication() {
        super();
        instance = this;
    }

    public static FishRecordApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getComponent().inject(this);
    }

    public static Context getAppContext() {
        return instance;
    }


    public static ApplicationComponent getComponent() {
        if (instance.mComponent == null) {
            instance.mComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(instance))
                    .build();
        }
        return instance.mComponent;
    }

    public static void setComponent(ApplicationComponent component) {
        instance.mComponent = component;
        getComponent().inject(instance);
    }

}
