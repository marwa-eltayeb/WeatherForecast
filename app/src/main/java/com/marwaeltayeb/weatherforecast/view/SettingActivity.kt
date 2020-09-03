package com.marwaeltayeb.weatherforecast.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.marwaeltayeb.weatherforecast.R


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

    // Inner Class for using a PreferenceFragment inside SettingsActivity.
    class SettingFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

        override fun onCreatePreferences(bundle: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.settings_main)
            val unit: Preference = findPreference(getString(R.string.unit_key))
            bindPreferenceSummaryToValue(unit)
        }

        override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
            // Takes in the preference value, converts it to a String,
            // and displays that value in the summary UI.
            val stringValue = newValue.toString()
            if (preference is ListPreference) {
                val listPreference: ListPreference = preference
                val prefIndex: Int = listPreference.findIndexOfValue(stringValue)
                if (prefIndex >= 0) {
                    val labels: Array<CharSequence> = listPreference.entries
                    preference.setSummary(labels[prefIndex])
                }
            } else {
                preference.summary = stringValue
            }
            return true
        }

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.onPreferenceChangeListener = this
            val preferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(preference.context)
            val preferenceString = preferences.getString(preference.key, "")
            onPreferenceChange(preference, preferenceString)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        NavUtils.navigateUpFromSameTask(this)
    }
}

