package com.bravo.android.bravo.data.local.pref

import android.content.Context

class PermanentPreferences(
    applicationContext: Context
) : BDPreferences.Permanent {
    private val permanentPref =
        applicationContext.getSharedPreferences(PREF_PERMANENT, Context.MODE_PRIVATE)


    companion object {
        private const val PREF_PERMANENT = "pref_permanent"
    }

}