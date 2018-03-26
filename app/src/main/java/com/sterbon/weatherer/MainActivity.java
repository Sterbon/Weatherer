package com.sterbon.weatherer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.sterbon.weatherer.R.id.place;

public class MainActivity extends AppCompatActivity  {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Weather mWeather;
    @BindView(place) TextView mTimeLabel;
    @BindView(R.id.temp) TextView mTemperatureLabel;
    @BindView(R.id.humidity) TextView mHumidityValue;
    @BindView(R.id.precip) TextView mPrecipValue;
    @BindView(R.id.summary) TextView mSummaryLabel;
    @BindView(R.id.icon) ImageView mIconImageView;
    @BindView(R.id.time) TextView mTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ProgressDialog progressDialog;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                String forecast_url=null;
                Log.i(TAG,"VAL: "+place.getLatLng());
                String value = place.getLatLng().toString();

                    int start = value.indexOf(value.charAt(10));
                    int end = value.indexOf(")");
                    String latlang = value.substring(start, end);

                forecast_url = "https://api.darksky.net/forecast/5100be8d8a2ccb9f5520d9086a4b49bc/" + latlang;

                if (connection()) {

                    OkHttpClient mClient = new OkHttpClient();
                    Request mrequest = new Request.Builder()
                            .url(forecast_url)
                            .build();

                    Call mCall = mClient.newCall(mrequest);
                    mCall.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String data = response.body().string();
                            Log.v(TAG, data);
                            try {
                                if (response.isSuccessful()) {
                                    mWeather = getWeather(data);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            mIconImageView.setVisibility(View.VISIBLE);
                                            updateDisplay();
                                        }
                                    });

                                } else {
                                    alert();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }

            }

            @Override
            public void onError(Status status) {

                Log.i(TAG, "ERROR : " + status);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateDisplay() {

        mTemperatureLabel.setText(" "+mWeather.getTemp()+"°");
        mHumidityValue.setText(mWeather.getHumid()+" %");
        mPrecipValue.setText(mWeather.getPrecip()+"%");
        mTimeLabel.setText(mWeather.getTimezone());
        mSummaryLabel.setText("' "+ mWeather.getSum()+" '");
        mTime.setText(mWeather.getFormattedTime());
        Drawable drawable = getResources().getDrawable(mWeather.getIconId(),null);
        mIconImageView.setImageDrawable(drawable);
    }


    private Weather getWeather(String data) throws JSONException {
        JSONObject weather = null;
            weather = new JSONObject(data);
            String timezone = weather.getString("timezone");
            Log.i(TAG,"DATA: "+timezone);

        JSONObject current = null;
            current = weather.getJSONObject("currently");
            Weather currentWeather = new Weather();
            currentWeather.setHumid(current.getDouble("humidity"));
            currentWeather.setIcon(current.getString("icon"));
            currentWeather.setPrecip(current.getDouble("precipProbability"));
            currentWeather.setTemp(current.getDouble("temperature"));
            currentWeather.setSum(current.getString("summary"));
            currentWeather.setTime(current.getLong("time"));
            currentWeather.setTimezone(timezone);

        Log.d(TAG, currentWeather.getFormattedTime());
        return currentWeather;
    }

    private boolean connection() {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getActiveNetworkInfo();
            if (mWifi!= null) {

                return true;
            }

            else
                return false;
        }

    private void alert() {

        AlertDialogFrag dialog = new AlertDialogFrag();
        dialog.show(getFragmentManager(),"Error");
    }



}