package com.example.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.market.APIs.APIClient;
import com.example.market.APIs.APIInterface;
import com.example.market.Retrofit.CryptoDetail;
import com.example.market.Retrofit.Datum;
import com.example.market.Retrofit.USD;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraphActivity extends AppCompatActivity  {

    private CandleStickChart chart;
    APIInterface apiInterface;
    NumberFormat chartUSDPriceFormat = NumberFormat.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        String Symbol = getIntent().getStringExtra("coin_symbol");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        ((TextView)findViewById(R.id.tv_coin_name)).setText(Symbol);
        setupChart();

        chartUSDPriceFormat = NumberFormat.getInstance();
        chartUSDPriceFormat.setMaximumFractionDigits(10);

        SingleSelectToggleGroup buttonGroup = findViewById(R.id.chart_interval_button_grp);
        buttonGroup.check(R.id.dayButton);

        buttonGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Calendar.getInstance();
            String url = null;

            if(checkedId == R.id.dayButton)
                url = "https://min-api.cryptocompare.com/data/v2/histohour?fsym="+Symbol+"&tsym=USD&limit=15&aggregate=1";
            else if(checkedId == R.id.weekButton)
                url = "https://min-api.cryptocompare.com/data/v2/histohour?fsym="+Symbol+"&tsym=USD&limit=15&aggregate=4";
            else if(checkedId == R.id.monthButton)
                url = "https://min-api.cryptocompare.com/data/v2/histohour?fsym="+Symbol+"&tsym=USD&limit=15&aggregate=12";
            else if(checkedId == R.id.threeMonthButton)
                url = "https://min-api.cryptocompare.com/data/v2/histoday?fsym="+Symbol+"&tsym=USD&limit=15&aggregate=1";
            else if(checkedId == R.id.yearButton)
                url = "https://min-api.cryptocompare.com/data/v2/histoday?fsym="+Symbol+"&tsym=USD&limit=15&aggregate=7";
            else if(checkedId == R.id.allTimeButton)
                url = "https://min-api.cryptocompare.com/data/v2/histoday?fsym="+Symbol+"&tsym=USD&limit=15&aggregate=30";

            getData(url);
        });

        String url = "https://min-api.cryptocompare.com/data/v2/histoday?fsym="+Symbol+"&tsym=USD&limit=15&aggregate=1";
        getData(url);

        FetchCoin(Symbol);
    }


    void setupChart(){
        chart = findViewById(R.id.chart);
        chart.setBackgroundColor(Color.TRANSPARENT);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(60);

        chart.setPinchZoom(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);

        chart.setDrawGridBackground(false);
        chart.setNoDataText(" ");
        chart.getLegend().setEnabled(true);
    }

    public void  getData(String url){
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(7, false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.resetTracking();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);


        RequestQueue queue = Volley.newRequestQueue(GraphActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

        try {
            //JSONObject dataObj = dataArray.getJSONObject(i);
            JSONObject Data1 = response.getJSONObject("Data");
            JSONArray Data2 = Data1.getJSONArray("Data");
            //Log.e("data1", "onResponse: "+Data2 );
            ArrayList<CandleEntry> yValues = new ArrayList<>();

            int x = 0;
            for (int i = 0; i < Data2.length(); i++) {

                Log.d("GraphActivity Resp", "onResponse: "+ Data2.getJSONObject(i) );
                Log.d("GraphActivity Resp", "onResponse: "+ getDatehour(Data2.getJSONObject(i).getLong("time")) );
                Log.d("GraphActivity Resp", "onResponse: "+ Data2.getJSONObject(i).getDouble("close") );
                Log.d("GraphActivity Resp", "onResponse: "+ Data2.getJSONObject(i).getDouble("high") );
                Log.d("GraphActivity Resp", "onResponse: "+ Data2.getJSONObject(i).getDouble("low") );
                Log.d("GraphActivity Resp", "onResponse: "+ Data2.getJSONObject(i).getDouble("open") );
                Log.d("GraphActivity Resp", "onResponse: "+ "============================" );

                x = x+1;
                yValues.add(new CandleEntry(i, (float) Data2.getJSONObject(i).getDouble("high"),(float) Data2.getJSONObject(i).getDouble("low"),(float) Data2.getJSONObject(i).getDouble("open"),(float) Data2.getJSONObject(i).getDouble("close")));
            }

            CandleDataSet set2 = new CandleDataSet(yValues,"");
            set2.setShadowColor(Color.WHITE);
            set2.setShadowWidth(0.7f);
            set2.setDecreasingColor(ContextCompat.getColor(getApplicationContext(),R.color.colorRed));
            set2.setDecreasingPaintStyle(Paint.Style.FILL_AND_STROKE);
            set2.setIncreasingColor(ContextCompat.getColor(getApplicationContext(),R.color.colorGreen));
            set2.setIncreasingPaintStyle(Paint.Style.FILL_AND_STROKE);
            set2.setNeutralColor(Color.BLUE);
            set2.setValueTextColor(Color.WHITE);

            CandleData data = new CandleData(set2);

            chart.setData(data);
            chart.invalidate();
            chart.getData().setHighlightEnabled(false);

        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("ooi", "onResponse: " + e.getMessage());
        }

    }, error -> {
        // displaying error response when received any error.
        chart.setNoDataText("No Data available for this coin");
        Toast.makeText(GraphActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
    });
    queue.add(jsonObjectRequest);
    }

    private String getDatehour(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        return DateFormat.format("MM.dd.hh", cal).toString();
    }

    public void setTable(Datum datum) {

        USD usd = datum.getQuote().getUSD();

        String usdFormat = getString(R.string.usd_format);
        String usdFormat1 = getString(R.string.usd_format1);
        String usdFormat2 = getString(R.string.usd_format2);

        String negativePctFormat = getString(R.string.negative_pct_format);
        String positivePctFormat = getString(R.string.positive_pct_format);
        int negativeRedColor = getResources().getColor(R.color.colorRed);
        int positiveGreenColor = getResources().getColor(R.color.colorGreen);
        TextView nameTextView = findViewById(R.id.tableNameDataTextView);

        if (datum.getName() == null)
            nameTextView.setText("N/A");
        else
            nameTextView.setText(datum.getName());

        TextView priceUSDTextView = findViewById(R.id.tablePriceUSDDataTextView);

        if (usd.getPrice() == null)
            priceUSDTextView.setText("N/A");
        else {
            String str_price = String.valueOf(usd.getPrice().intValue());
            if(str_price.length()<=1)
                priceUSDTextView.setText(String.format(usdFormat2, usd.getPrice()));
            else
                priceUSDTextView.setText(String.format(usdFormat, usd.getPrice()));
        }

        TextView mktCapTextView = findViewById(R.id.tableMktCapDataTextView);
        if (usd.getMarketCap() == null)
            mktCapTextView.setText("N/A");
        else
            mktCapTextView.setText(String.format(usdFormat1, usd.getMarketCap()));


        TextView oneHrChangeTextView = findViewById(R.id.table1hrChangeDataTextView);
        if (usd.getPercentChange1h() == null)
            oneHrChangeTextView.setText("N/A");
        else {
            if (usd.getPercentChange1h() >= 0) {
                oneHrChangeTextView.setText(String.format(positivePctFormat, usd.getPercentChange1h()));
                oneHrChangeTextView.setTextColor(positiveGreenColor);
            } else {
                oneHrChangeTextView.setText(String.format(negativePctFormat, usd.getPercentChange1h()));
                oneHrChangeTextView.setTextColor(negativeRedColor);
            }
        }


        TextView dayChangeTextView = findViewById(R.id.table24hrChangeDataTextView);
        if (usd.getPercentChange24h() == null)
            dayChangeTextView.setText("N/A");
        else {
            if (usd.getPercentChange24h() >= 0) {
                dayChangeTextView.setText(String.format(positivePctFormat, usd.getPercentChange24h()));
                dayChangeTextView.setTextColor(positiveGreenColor);
            } else {
                dayChangeTextView.setText(String.format(negativePctFormat, usd.getPercentChange24h()));
                dayChangeTextView.setTextColor(negativeRedColor);
            }
        }

        TextView weekChangeTextView = findViewById(R.id.tableWeekChangeDataTextView);
        if (usd.getPercentChange7d() == null)
            weekChangeTextView.setText("N/A");
        else {
            if (usd.getPercentChange7d() >= 0) {
                weekChangeTextView.setText(String.format(positivePctFormat, usd.getPercentChange7d()));
                weekChangeTextView.setTextColor(positiveGreenColor);
            } else {
                weekChangeTextView.setText(String.format(negativePctFormat, usd.getPercentChange7d()));
                weekChangeTextView.setTextColor(negativeRedColor);
            }
        }
    }

    // test commit

    void FetchCoin(String coin_symbol){
        Call<CryptoDetail> call2 = apiInterface.getCoinDetail(coin_symbol);
        call2.enqueue(new Callback<CryptoDetail>() {
            @Override
            public void onResponse(@NonNull Call<CryptoDetail> call, @NonNull Response<CryptoDetail> response) {
                try {
                    CryptoDetail coin_detail = response.body();
                    System.out.println(response.body());
                    if (coin_detail != null) {
                        JsonObject jsonElements = coin_detail.getData().getAsJsonObject(coin_symbol);
                        Gson gson = new Gson();
                        System.out.println(jsonElements);
                        Datum datum = gson.fromJson(jsonElements.toString(), Datum.class);
                        setTable(datum);
                    } else {
                        System.out.println("null");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CryptoDetail> call, @NonNull Throwable t) {
                Toast.makeText(GraphActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.d("Failure Cause", t.getLocalizedMessage());
                call.cancel();
            }
        });
    }
}