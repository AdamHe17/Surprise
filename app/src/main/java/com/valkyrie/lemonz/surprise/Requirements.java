package com.valkyrie.lemonz.surprise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.amazon.device.associates.AssociatesAPI;
import com.amazon.device.associates.FilterType;
import com.amazon.device.associates.NoListenerException;
import com.amazon.device.associates.NotInitializedException;
import com.amazon.device.associates.PurchaseResponse;
import com.amazon.device.associates.ReceiptsResponse;
import com.amazon.device.associates.RequestId;
import com.amazon.device.associates.SearchByIdResponse;
import com.amazon.device.associates.SearchRequest;
import com.amazon.device.associates.SearchResponse;
import com.amazon.device.associates.ServiceStatusResponse;
import com.amazon.device.associates.ShoppingListener;
import com.amazon.device.associates.ShoppingService;
import com.amazon.device.associates.ShoppingServiceRequest;


public class Requirements extends Activity implements OnClickListener, ShoppingListener{

    Button next;
    EditText category, min, max;
    String max_val, min_val, category_t;
    GlobalPurchasingListener globalListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);

        globalListener = GlobalPurchasingListener.getInstance();
        AssociatesAPI.Config config = new AssociatesAPI.Config("bb4f7da61e5640b495c585ae0d7e3849", getApplicationContext());

        try {
            AssociatesAPI.initialize(config);
            AssociatesAPI.getShoppingService().setListener(globalListener);
        } catch (final IllegalArgumentException illegalArgumentException) {
        } catch (final NotInitializedException notInitializedException) {
        }

        next = (Button) findViewById(R.id.Next);
        next.setOnClickListener(this);

        category = (EditText) findViewById(R.id.Category_text);

        min = (EditText) findViewById(R.id.Min_value);

        max = (EditText) findViewById(R.id.Max_value);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_requirements, menu);
        return true;
    }

    public void storeAction(View v) {
        Intent intent = new Intent(this, StoreActivity.class);
        intent.putExtra("key", category_t);
        intent.putExtra("min", min_val);
        intent.putExtra("max", max_val);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        globalListener.setLocalListener(this);
        try {
            AssociatesAPI.getShoppingService().getServiceStatus();
        } catch(final NoListenerException e) {
        }
        catch(final NotInitializedException e) {
        }
    }

    @Override
    public void onClick(View v) {
        min_val = min.getText().toString();
        max_val = max.getText().toString();
        category_t = category.getText().toString();
        SearchRequest query;

        try {
            query = findItem(v, category_t, min_val, max_val);
        } catch (final NoListenerException e) {
        }

        storeAction(v);

        Intent goToPayment = new Intent(getApplicationContext(), Payment.class);
        goToPayment.putExtra("value", max_val);
        startActivity(goToPayment);
    }

    public SearchRequest findItem(View v, String keyword, String min, String max) throws NoListenerException{
        SearchRequest query = new SearchRequest("All", keyword);
        query.addFilter(FilterType.MaximumPrice, max);
        query.addFilter(FilterType.MinimumPrice, min);
        return query;
    }

    @Override
    public void onServiceStatusResponse(ServiceStatusResponse serviceStatusResponse) {

    }

    @Override
    public void onPurchaseResponse(PurchaseResponse purchaseResponse) {

    }

    @Override
    public void onReceiptsResponse(ReceiptsResponse receiptsResponse) {

    }

    @Override
    public void onSearchResponse(SearchResponse searchResponse) {

    }

    @Override
    public void onSearchByIdResponse(SearchByIdResponse searchByIdResponse) {

    }
}
