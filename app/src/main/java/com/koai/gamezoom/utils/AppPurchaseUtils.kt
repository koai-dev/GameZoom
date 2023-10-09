package com.koai.gamezoom.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetailsResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.consumePurchase
import com.android.billingclient.api.queryProductDetails
import com.koai.gamezoom.account.AccountUtils
import com.koai.gamezoom.api.ApiController
import com.koai.gamezoom.api.ApiService
import com.koai.gamezoom.models.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppPurchaseUtils(private val context: Context, private val result: AppPurchaseUtils.Result? = null) {
    companion object {
        const val PRODUCT_ID = "game10k"
    }
    private var productId = Game.TYPE_FARM_PUZZLE

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
                    CoroutineScope(Dispatchers.IO).launch {
                        handlePurchase(purchase)
                    }
                }
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                // Handle an error caused by a user cancelling the purchase flow.
                Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show()
            } else {
                // Handle any other error codes.
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }

    private lateinit var productDetailsResult: ProductDetailsResult

    private var billingClient = BillingClient.newBuilder(context)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()

    init {
        connect()
    }
    private fun connect(){
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.\
                    Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(context, "Run in Connection", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Main).launch {
                    async {
                        productDetailsResult = processPurchases()
                    }.await()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Toast.makeText(context, "Disconnect", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private suspend fun processPurchases(): ProductDetailsResult {
        val productList = ArrayList<QueryProductDetailsParams.Product>()
        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(PRODUCT_ID)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )
        val params = QueryProductDetailsParams.newBuilder()
        params.setProductList(productList)

        // leverage queryProductDetails Kotlin extension function
        val productDetailsResult = withContext(Dispatchers.IO) {
            billingClient.queryProductDetails(params.build())
        }

        // Process the result.
        return productDetailsResult
    }

    fun purchase(activity: Activity, productId: String) {
        this.productId = productId
        if (!this::productDetailsResult.isInitialized) {
            CoroutineScope(Dispatchers.Main).launch {
                async {
                    productDetailsResult = processPurchases()
                }.await()
                val productDetails =
                    productDetailsResult.productDetailsList?.firstOrNull { productDetails -> productDetails.productId == productId }
                var offerToken: String? = null
                productDetails?.subscriptionOfferDetails?.forEach { offToken ->
                    kotlin.run {
                        if (offToken != null && offToken.offerToken.isNotEmpty()) {
                            offerToken = offToken.offerToken
                            return@forEach
                        }
                    }
                }
                if (productDetails != null && offerToken != null) {
                    val productDetailsParamsList = listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                            .setProductDetails(productDetails)
                            // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                            // for a list of offers that are available to the user
                            .setOfferToken(offerToken!!)
                            .build()
                    )

                    val billingFlowParams = BillingFlowParams.newBuilder()
                        .setProductDetailsParamsList(productDetailsParamsList)
                        .build()

                    // Launch the billing flow
                    billingClient.launchBillingFlow(activity, billingFlowParams)
                }else if (productDetails!=null){
                    val productDetailsParamsList = listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                            .setProductDetails(productDetails)
                            // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                            // for a list of offers that are available to the user
                            .build()
                    )

                    val billingFlowParams = BillingFlowParams.newBuilder()
                        .setProductDetailsParamsList(productDetailsParamsList)
                        .build()

                    // Launch the billing flow
                    billingClient.launchBillingFlow(activity, billingFlowParams)
                }
            }
        } else {
            val productDetails =
                productDetailsResult.productDetailsList?.firstOrNull { productDetails -> productDetails.productId == productId }
            var offerToken: String? = null
            productDetails?.subscriptionOfferDetails?.forEach { offToken ->
                kotlin.run {
                    if (offToken != null && offToken.offerToken.isNotEmpty()) {
                        offerToken = offToken.offerToken
                        return@forEach
                    }
                }
            }
            if (productDetails != null && offerToken != null) {
                val productDetailsParamsList = listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                        .setProductDetails(productDetails)
                        // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                        // for a list of offers that are available to the user
                        .setOfferToken(offerToken!!)
                        .build()
                )

                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build()

                // Launch the billing flow
                billingClient.launchBillingFlow(activity, billingFlowParams)
            }else if (productDetails!=null){
                val productDetailsParamsList = listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                        .setProductDetails(productDetails)
                        // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                        // for a list of offers that are available to the user
                        .build()
                )

                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build()

                // Launch the billing flow
                billingClient.launchBillingFlow(activity, billingFlowParams)
            }
        }
    }

    private suspend fun handlePurchase(purchase: Purchase) {
        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
        val consumeResult = withContext(Dispatchers.IO) {
            billingClient.consumePurchase(consumeParams)
        }
        if (consumeResult.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            result?.onSuccess()
            CoroutineScope(Dispatchers.IO).launch {
                Toast.makeText(context, "Great! Thank you very much.", Toast.LENGTH_SHORT).show()
            }
        } else {
            result?.onFail()
            CoroutineScope(Dispatchers.IO).launch {
                Toast.makeText(context, "Error! Try later.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface Result{
        fun onSuccess()
        fun onFail()
    }
}