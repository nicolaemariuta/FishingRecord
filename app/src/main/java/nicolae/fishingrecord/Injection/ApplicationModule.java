package nicolae.fishingrecord.Injection;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nicolae.fishingrecord.Model.AppDbHandler;
import nicolae.fishingrecord.Model.DataService;
import nicolae.fishingrecord.Model.DataServiceImpl;

/** In order to provide an instance of one object, it is need to declare it here with the annotation
        *
        * @Provide and @Singleton if it should keep the same instance across the application.
        * <p>
        * If it is needed to provide the instance to the ActivityModule, then it needs to be declared in
         * ApplicationComponent.
         */
@Module
public class ApplicationModule {

    protected Application mApp;

    public ApplicationModule(Application app) {
        mApp = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApp.getApplicationContext();
    }



    @Provides
    @Singleton
    AppDbHandler provideDBHelper() {
        return new AppDbHandler(mApp.getApplicationContext());
    }

    @Provides
    @Singleton
    DataService provideDataService(AppDbHandler appDbHandler) {
        return new DataServiceImpl(appDbHandler);
    }


}
