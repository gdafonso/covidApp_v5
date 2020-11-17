package com.example.covidapp_v5.ui.preferences;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covidapp_v5.R;

public class SettingsContainerActivity extends AppCompatActivity implements SettingsFragment.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_container);

    }
}
