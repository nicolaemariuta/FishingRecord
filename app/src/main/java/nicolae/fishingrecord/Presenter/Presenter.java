package nicolae.fishingrecord.Presenter;

public interface Presenter {


    void start();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) pause() method.
     */
    void pause();

    void stop();
}
