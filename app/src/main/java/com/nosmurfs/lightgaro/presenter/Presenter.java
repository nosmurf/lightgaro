package com.nosmurfs.lightgaro.presenter;

import android.content.Context;

public abstract class Presenter<T extends Presenter.View> {

    T view;

    public void start(T v) {
        this.view = v;
        if (v == null) {
            throw new RuntimeException();
        }

        this.initialize();
    }

    protected abstract void initialize();

    public abstract void destroy();

    public interface View {
        void showProgress(String message);

        void showProgress(int messageId);

        void hideProgress();

        void showError(String message);

        void showError(int messageId);

        Context getContext();
    }
}