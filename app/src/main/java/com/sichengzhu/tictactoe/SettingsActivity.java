package com.sichengzhu.tictactoe;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Isra on 6/5/2016.
 */
public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}