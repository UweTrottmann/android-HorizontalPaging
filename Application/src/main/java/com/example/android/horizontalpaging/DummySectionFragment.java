package com.example.android.horizontalpaging;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.horizontalpaging.databinding.FragmentMainDummyBinding;

public class DummySectionFragment extends Fragment {
    protected String logTag = MainActivity.TAG + "/" + DummySectionFragment.class.getSimpleName();
    public static final String ARG_SECTION_NUMBER = "section_number";

    protected FragmentMainDummyBinding binding;
    protected int sectionNumber;

    public DummySectionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        logTag = MainActivity.TAG + "/" + DummySectionFragment.class.getSimpleName() + sectionNumber;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_dummy, container, false);
        binding.sectionLabel.setText(String.valueOf(sectionNumber));
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(logTag, "initLoader");
        getLoaderManager().initLoader(sectionNumber, null, loaderCallbacks);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(logTag, "onStart");
    }

    private LoaderManager.LoaderCallbacks<String> loaderCallbacks =
            new LoaderManager.LoaderCallbacks<String>() {
                @Override
                public Loader<String> onCreateLoader(int id, Bundle args) {
                    Log.d(logTag, "onCreateLoader");
                    return new DummyLoader(getContext());
                }

                @Override
                public void onLoadFinished(Loader<String> loader, String data) {
                    Log.d(logTag, "onLoadFinished");
                    binding.sectionLabel.setText(data);
                }

                @Override
                public void onLoaderReset(Loader<String> loader) {
                    Log.d(logTag, "onLoaderReset");
                    binding.sectionLabel.setText("no data");
                }
            };
}
