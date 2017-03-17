package com.jan.mvp;

import android.app.Activity;
import android.os.Bundle;
import java.util.HashMap;
import java.util.Set;

/**
 * Handles common operations for organizing an MVP activity with multiple presenters
 */
public abstract class BaseMVPActivity extends Activity {
    private static final String TAG = BaseMVPActivity.class.getCanonicalName();

    // The key is the canonical name of the BasePresenter subclass
    private HashMap<String, BasePresenter> presenters;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            presenters = getPresenterMapInstance();
        } else {
            presenters = PresenterManager.getInstance().restorePresenter(savedInstanceState);
            onPresentersRestored(presenters);
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenters != null) {
            PresenterManager.getInstance().savePresenter(presenters, outState);
        }
    }

    @Override protected void onResume() {
        super.onResume();
        bindPresenters();
    }

    @Override protected void onPause() {
        super.onPause();
        unbindPresenters();
    }

    private void bindPresenters() {
        if (presenters == null) {
            return;
        }

        Set<String> keys = presenters.keySet();
        for (String key : keys) {
            presenters.get(key).bindView();
        }
    }

    private void unbindPresenters() {
        if (presenters == null) {
            return;
        }

        Set<String> keys = presenters.keySet();
        for (String key : keys) {
            presenters.get(key).unbindView();
        }
    }

    /**
     * Adds a new presenter to the list for management. Usually called after {@link
     * BaseMVPActivity#onCreate(Bundle)}. This method
     * will throw a RuntimeException when attempting to add a null presenter or was called before
     * {@link BaseMVPActivity#getPresenterMapInstance()}.
     */
    protected final <P extends BasePresenter> void addPresenter(P presenter) {
        if (presenter == null) {
            throw new RuntimeException(
                "Attempting to add a null presenter to managed presenter map.");
        }

        if (presenters == null) {
            throw new RuntimeException(
                "Attempting to add presenter with a null presenter map. To use the MVP feature, you must implement getPresenterMapInstance first.");
        }

        presenters.put(presenter.getClass().getCanonicalName(), presenter);
    }

    /**
     * Provides a map of presenters used in the activity. This is overridden by super classes that
     * wants to use MVP
     */
    protected HashMap<String, BasePresenter> getPresenterMapInstance() {
        return null;
    }

    /**
     * Called after restoring presenters from PresenterManager. Activities need to override this to
     * re-reference existing presenters
     */
    protected void onPresentersRestored(HashMap<String, BasePresenter> presenters) {
        // should be overridden
    }

    public static <P extends BasePresenter> P getPresenter(
        HashMap<String, BasePresenter> presenters, Class<P> clazz) {
        if (!presenters.containsKey(clazz.getCanonicalName())) {
            return null;
        }
        return (P) presenters.get(clazz.getCanonicalName());
    }

    public static class PresenterMapBuilder {
        private HashMap<String, BasePresenter> presenterMap;

        public PresenterMapBuilder() {
            presenterMap = new HashMap<>();
        }

        public <P extends BasePresenter> PresenterMapBuilder addPresenter(P presenter) {
            presenterMap.put(presenter.getClass().getCanonicalName(), presenter);
            return this;
        }

        public HashMap<String, BasePresenter> build() {
            return presenterMap;
        }
    }
}
