package com.jan.mvp;

import android.os.Bundle;
import android.os.Handler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Manager for caching/restoring viewPresentersMap
 */
public final class PresenterManager {
    private static final String SIS_KEY_PRESENTER_ID = "presenter_id";
    private static final int SECOND_TO_MILLISECOND = 1000;
    private static final int DEFAULT_EXPIRATION_VALUE = 30 * SECOND_TO_MILLISECOND;
    private static final long INITIAL_PRESENTER_ID = 1;

    private static PresenterManager instance;

    private final AtomicLong currentId;
    private final Map<Long, HashMap<String, BasePresenter>> viewPresentersMap;
    private Handler handler;

    private class ExpiryRunnable implements Runnable {
        private long presenterListId;

        ExpiryRunnable(long presenterListId) {
            this.presenterListId = presenterListId;
        }

        @Override public void run() {
            viewPresentersMap.remove(presenterListId);
        }
    }

    private PresenterManager() {
        currentId = new AtomicLong(INITIAL_PRESENTER_ID);
        viewPresentersMap = new HashMap<>();
        handler = new Handler();
    }

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager();
        }
        return instance;
    }

    public HashMap<String, BasePresenter> restorePresenter(Bundle savedInstanceState) {
        Long presenterId = savedInstanceState.getLong(SIS_KEY_PRESENTER_ID);
        HashMap<String, BasePresenter> presenters = viewPresentersMap.remove(presenterId);
        return presenters;
    }

    public void savePresenter(HashMap<String, BasePresenter> presenters,
        Bundle savedInstanceState) {
        long presenterId = currentId.incrementAndGet();
        viewPresentersMap.put(presenterId, presenters);
        savedInstanceState.putLong(SIS_KEY_PRESENTER_ID, presenterId);
        handler.postDelayed(new ExpiryRunnable(presenterId), DEFAULT_EXPIRATION_VALUE);
    }
}
