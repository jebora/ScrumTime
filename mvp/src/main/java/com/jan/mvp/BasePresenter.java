package com.jan.mvp;

import java.lang.ref.WeakReference;

/**
 * Abstract BasePresenter used to extend by future presenters
 */

public abstract class BasePresenter<V extends BaseView> {
    private WeakReference<V> view;

    public abstract V getViewForBinding();

    public abstract void updateView();

    public abstract void resetState();

    public void bindView() {
        V view = getViewForBinding();
        if (view == null) {
            return;
        }

        this.view = new WeakReference<>(view);
        if (isSetupDone()) {
            updateView();
        }
    }

    public void unbindView() {
        this.view = null;
    }

    public V view() {
        if (view == null) {
            return null;
        }
        return view.get();
    }

    protected boolean isSetupDone() {
        return view() != null;
    }
}
