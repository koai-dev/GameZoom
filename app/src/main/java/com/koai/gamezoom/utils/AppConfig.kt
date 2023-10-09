/*
 *  Created by Nguyễn Kim Khánh on 09:49, 08/08/2022
 *     dtako.developer@gmail.com
 *     Last modified 09:49, 08/08/2022
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.koai.gamezoom.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.koai.gamezoom.R

object AppConfig {
    private const val REWARD_ADS = "reward_ads"

    fun initRemote() {
        FirebaseRemoteConfig.getInstance().setDefaultsAsync(R.xml.remote_config_defaults)
        FirebaseRemoteConfig.getInstance().setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(10).build()
        )
        FirebaseRemoteConfig.getInstance().fetchAndActivate()
    }

    val rewardAds: String
        get() = FirebaseRemoteConfig.getInstance().getString(REWARD_ADS)

    const val licenseKey: String = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmkIMfbvOBHfvxDL9yuNCYd2b2qO77Yc9FVnkihMGqXOD82T1o/E5cQ29vSa3DdqEY6XDw7B5cIHa0bpWINoj2WxLgxiQzHhADCWL3kgtDmFqx7YyetcyeAE/8V0EBR/tYItzdR5lrdGUtmvRQxHro4P66/N8d8o/m3OMlFTxrns66PqzVAOUUpKCkn/m6yNyO93jxsi5nZxhHMdL7sRUtzz+0E/W/fEpM98FgBE6doFMRTbEfNwOCp88foY5vdgcUHScEdKP/GS04b2R6eXQh8Zpj+pDr/0fcEC3PAjERpUERp73nYta9ebGQiLZrOU6cjwnlGUKvt4glpXRPrVDkwIDAQAB"
}