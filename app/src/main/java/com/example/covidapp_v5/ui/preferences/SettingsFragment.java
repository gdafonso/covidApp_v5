package com.example.covidapp_v5.ui.preferences;

import android.content.Context;
import android.os.Bundle;

import android.preference.PreferenceFragment;
import com.example.covidapp_v5.R;

public class SettingsFragment extends PreferenceFragment {
    private OnFragmentInteractionListener mListener;

    //@Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        //TODO ESTO
        //setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {}
}