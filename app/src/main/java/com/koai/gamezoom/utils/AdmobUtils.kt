package com.koai.gamezoom.utils

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object AdmobUtils {
    private var mRewardedAd: RewardedAd? = null

    fun setAdmob(context: Context, liveData: MutableLiveData<Boolean>? = null) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            AppConfig.rewardAds,
            adRequest,
            object : RewardedAdLoadCallback() {

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mRewardedAd = null
                    setAdmob(context)
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                    liveData?.postValue(true)
                }
            })
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                mRewardedAd = null
                RewardedAd.load(
                    context,
                    AppConfig.rewardAds,
                    adRequest,
                    object : RewardedAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            mRewardedAd = null
                        }

                        override fun onAdLoaded(rewardedAd: RewardedAd) {
                            mRewardedAd = rewardedAd
                        }
                    })

            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                mRewardedAd = null
                RewardedAd.load(
                    context,
                    AppConfig.rewardAds,
                    adRequest,
                    object : RewardedAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            mRewardedAd = null
                        }

                        override fun onAdLoaded(rewardedAd: RewardedAd) {
                            mRewardedAd = rewardedAd
                        }
                    })
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
            }
        }
    }

    fun showAdmob(context: Activity, action: Action? = null) {
        if (mRewardedAd != null) {
            mRewardedAd?.show(context) {
                action?.onReward()
                mRewardedAd = null
                setAdmob(context)
            }
        } else {
            setAdmob(context)
        }
    }

    interface Action {
        fun onReward()
    }
}