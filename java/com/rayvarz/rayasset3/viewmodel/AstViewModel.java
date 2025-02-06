package com.rayvarz.rayasset3.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rayvarz.rayasset3.adapter.RecAstItemAdapter;
import com.rayvarz.rayasset3.model.RecAstItem;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.rayvarz.rayasset3.view.SelectionItemManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AstViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> _navigateToAstDsc = new MutableLiveData<>();
    public LiveData<Boolean> navigateToAstDsc = _navigateToAstDsc;
    private final MutableLiveData<Boolean> _navigateToMain = new MutableLiveData<>();
    private MutableLiveData<List<RecAstItem>> astItemList = new MutableLiveData<>();
    private boolean isLoading = false;
    private final MutableLiveData<Boolean> _isCloseClick = new MutableLiveData<>();
    public LiveData<Boolean> isCloseClick = _isCloseClick;

    public AstViewModel(Application application) {
        super(application);
    }

    public LiveData<List<RecAstItem>> getAstItemList() {
        return astItemList;
    }

    public void fetchAstItemsAsync(int from, int take, String contain) {
        if (isLoading) return;
        isLoading = true;

        String url = "http://192.168.20.26/khftyedevku/weatherforecast?contain=" + contain + "&from=" + from + "&take=" + take;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<RecAstItem> currentList;

                            // new search, clear previous list
                            if (from == 0) {
                                currentList = new ArrayList<>();
                            } else {
                                currentList = astItemList.getValue();
                                if (currentList == null) {
                                    currentList = new ArrayList<>();
                                }
                            }

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String astTitle = jsonObject.optString("astDsc", "Unknown Title");
                                String astLocation = jsonObject.optString("rfAsset", "Unknown Location");
                                String astBarcode = jsonObject.optString("idNo", "Unknown Barcode");
                                String astImg = jsonObject.optString("rowGuid", "https://icon-library.com/images/free-building-icon/free-building-icon-0.jpg");
                                int astClassify = jsonObject.optInt("classify", 0);

                                currentList.add(new RecAstItem(astImg, astTitle, astBarcode, astLocation , astClassify));
                            }

                            astItemList.postValue(currentList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            isLoading = false;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        isLoading = false;
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    public void onAddAstClicked(){
        Log.d("onAddAstClicked", "button clicked");
        _navigateToAstDsc.setValue(true);
    }

    public void resetNavigation() {
        _navigateToAstDsc.setValue(false);
    }


    //  Btn Check Fliter
    public void onFilterClicked() {
        Log.d("LoginViewModel", "Login button clicked");
        _navigateToMain.setValue(true);
    }

    public boolean isLoading() {
        return isLoading;
    }

}
