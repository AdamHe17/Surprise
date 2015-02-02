package com.valkyrie.lemonz.surprise;

import android.content.Context;

import com.amazon.device.associates.PurchaseResponse;
import com.amazon.device.associates.ReceiptsResponse;
import com.amazon.device.associates.SearchByIdResponse;
import com.amazon.device.associates.SearchResponse;
import com.amazon.device.associates.ServiceStatusResponse;
import com.amazon.device.associates.ShoppingListener;

public class GlobalPurchasingListener implements ShoppingListener {

    // Allow creation of a singleton object
    private static final GlobalPurchasingListener instance = new GlobalPurchasingListener();
    private static Context context = null;

    public static GlobalPurchasingListener getInstance() {
        return instance;
    }

    public void setContext(Context appContext) {
        context = appContext;
    }

    // Provide a method to register local event listeners
    private static ShoppingListener localListener = null;

    public void setLocalListener(ShoppingListener listener) {
        localListener = listener;
    }

    // Dispatch response to corresponding callback in localListener.
    @Override
    public void onServiceStatusResponse(ServiceStatusResponse response) {
        localListener.onServiceStatusResponse(response);
    }
    @Override
    public void onPurchaseResponse(PurchaseResponse response) {
        localListener.onPurchaseResponse(response);
    }
    @Override
    public void onSearchByIdResponse(SearchByIdResponse response) {
        localListener.onSearchByIdResponse(response);
    }
    @Override
    public void onSearchResponse(SearchResponse response) {
        localListener.onSearchResponse(response);
    }
    @Override
    public void onReceiptsResponse(ReceiptsResponse response) {
        localListener.onReceiptsResponse(response);
    }
}