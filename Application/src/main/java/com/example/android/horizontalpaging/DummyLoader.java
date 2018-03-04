package com.example.android.horizontalpaging;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Very basic AsyncTaskLoader based on https://developer.android.com/reference/android/content/AsyncTaskLoader.html.
 */
public class DummyLoader extends AsyncTaskLoader<String> {

    protected String items;

    public DummyLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        SystemClock.sleep(300); // simulate loading taking a while
        return "finished";
    }

    /**
     * Called when there is new data to deliver to the client. The super class will take care of
     * delivering it; the implementation here just adds a little more logic.
     */
    @Override
    public void deliverResult(String newItems) {
        if (isReset()) {
            // An async query came in while the loader is stopped. We
            // don't need the result.
            if (newItems != null) {
                onReleaseResources(newItems);
            }
        }
        String oldItems = this.items;
        this.items = newItems;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(newItems);
        }

        if (oldItems != null) {
            onReleaseResources(oldItems);
        }
    }

    @Override
    protected void onStartLoading() {
        if (items != null) {
            deliverResult(items);
        }
        if (takeContentChanged() || items == null) {
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(String items) {
        if (items != null) {
            onReleaseResources(items);
        }
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release resources
        if (items != null) {
            onReleaseResources(items);
            items = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated with an actively loaded data
     * set.
     */
    protected void onReleaseResources(String items) {
        // For simple items there is nothing to do. For something
        // like a Cursor, we would close it here.
    }
}
