package com.valkyrie.lemonz.surprise;

import android.app.Activity;
import android.os.Bundle;

import com.amazon.device.associates.AssociatesAPI;
import com.amazon.device.associates.FilterType;
import com.amazon.device.associates.NoListenerException;
import com.amazon.device.associates.NotInitializedException;
import com.amazon.device.associates.Product;
import com.amazon.device.associates.PurchaseResponse;
import com.amazon.device.associates.ReceiptsResponse;
import com.amazon.device.associates.SearchByIdResponse;
import com.amazon.device.associates.SearchRequest;
import com.amazon.device.associates.SearchResponse;
import com.amazon.device.associates.ServiceStatusResponse;
import com.amazon.device.associates.ShoppingListener;
import com.amazon.device.associates.SortType;

import java.util.List;

public class StoreActivity extends Activity implements ShoppingListener {

    // Retrieve instance of global event listener and register its context
    private GlobalPurchasingListener globalListener;
    String max, min, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        globalListener = GlobalPurchasingListener.getInstance();
        globalListener.setContext(this);

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");
        min = extras.getString("min");
        max = extras.getString("max");
    }
    // In OnResume, register with it's local event listener with the global event listener
    @Override
    protected void onResume() {
        globalListener.setLocalListener(this);
    }

    // local method to initiate product look up.
    public void searchForProductsUsingKeywords() throws NotInitializedException, NoListenerException{
        // Specify the search, filter and sort criteria
        String category = "all";
        String term = key;
        int page = 1;
        SearchRequest request = new SearchRequest(category,term,page);
        request.setSortType(SortType.RELEVANCE);
        request.addFilter(FilterType.MaximumPrice, max);
        request.addFilter(FilterType.MinimumPrice, min);

        AssociatesAPI.getShoppingService().search(request);
    }

    @Override
    public void onSearchResponse(SearchResponse response) {
        // Note: Be aware that the callbacks is invoked in the UI thread.
        // Any CPU intensive or time consuming task should be executed in a separate thread using an AsyncTask.
        SearchResponse.Status status = response.getStatus();

        if (status == SearchResponse.Status.FAILED) {
            // Error occured while trying to locate the items in the store

        } else if (status == SearchResponse.Status.NOT_SUPPORTED) {
            // Mobile Associates API not supported

        } else {
            // Get the list of search results
            List<Product> searchResults = response.getProducts();

            // Traverse through product list and retrieve product information
            for (final Product product : searchResults) {
                // get product ID using product.getProductId()
                // get product title using product.getTitle()
                // get product description using product.getDescription()
                if (product.getPrice() != null){
                    // get product price using product.getPrice()
                    // get formatted string using product.getPrice().getFormattedString()
                }
                if (product.getImage() != null){
                    // get product image URL, width and height using product.getImage()
                }
                if (product.getBrand() != null){
                    // get product brand using product.getBrand()
                }
                // get product rating using product.getRating()

                // Update UI with product infomration.
            }
            if (response.getPage() < response.getTotalPages()){
                String category = "all";
                String term = "piano";
                int page = response.getPage() + 1;
                SearchRequest request = new SearchRequest(category,term,page);
                request.setSortType(SortType.BESTSELLING);
                request.addFilter(FilterType.MaximumPrice, "5000");
                request.addFilter(FilterType.MinimumPrice, "1");
                try {
                    AssociatesAPI.getShoppingService().search(request);
                }catch(final NoListenerException e) {
                }catch(final NotInitializedException e) {
                }
            }
        }
    }

    // Handle the required purchasing service callback
    @Override
    public void onServiceStatusResponse(ServiceStatusResponse response) {
        // Code to handle onServiceStatusResponse
        // Note: Be aware that the callbacks is invoked in the UI thread.
        // Any CPU intensive or time consuming task should be executed in a separate thread using an AsyncTask.

    }
    @Override
    public void onPurchaseResponse(PurchaseResponse response) {
        // Code to handle onPurchaseResponse
        // Note: Be aware that the callbacks is invoked in the UI thread.
        // Any CPU intensive or time consuming task should be executed in a separate thread using an AsyncTask.
    }
    @Override
    public void onSearchByIdResponse(SearchByIdResponse response) {
        // Code to handle onSearchByIdResponse
        // Note: Be aware that the callbacks is invoked in the UI thread.
        // Any CPU intensive or time consuming task should be executed in a separate thread using an AsyncTask.
    }
    @Override
    public void onReceiptsResponse(ReceiptsResponse response) {
        // This method is only required if you are tracking purchases with receipts
        // Code to handle onReceiptsResponse
        // Note: Be aware that the callbacks is invoked in the UI thread.
        // Any CPU intensive or time consuming task should be executed in a separate thread using an AsyncTask.
    }
}