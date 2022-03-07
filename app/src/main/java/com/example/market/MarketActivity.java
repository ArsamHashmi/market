package com.example.market;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.market.adapters.CoinListAdapter;
import com.example.market.models.model_coin_list;

import java.util.ArrayList;

public class MarketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        ArrayList<model_coin_list> coins_list = new ArrayList<>();
        ListView yourListView = findViewById(R.id.listview_coins);

        model_coin_list btc = new model_coin_list("Bitcoin","BTC", 42000.00, -2.00, R.drawable.btc_1);
        model_coin_list eth = new model_coin_list("Ethereum","ETH", 2500.00, -2.00, R.drawable.eth_2);
        model_coin_list bnb = new model_coin_list("BNB","BNB", 380.00, -2.00, R.drawable.bnb_4);
        model_coin_list xrp = new model_coin_list("Ripple","XRP", 0.7351, -2.00, R.drawable._xrp_6);
        model_coin_list ada = new model_coin_list("Cardano","ADA", 0.863, -2.00, R.drawable.ada_3);
        model_coin_list rvn = new model_coin_list("Ravencoin","RVN", 0.05313, -2.00, R.drawable.rvn_icon);
        model_coin_list firo = new model_coin_list("Firo","FIRO", 3.251, -2.00, R.drawable.firo);
        model_coin_list doge = new model_coin_list("Dogecoin","DOGE", 0.1237, -2.00, R.drawable.doge);
        model_coin_list vrsc = new model_coin_list("Verus Coin","VRSC", 0.8107, -2.00, R.drawable.vrsc);
        model_coin_list usdt = new model_coin_list("TetherUS","USDT", 1.00, 0.00, R.drawable.uadt);

        coins_list.add(btc);
        coins_list.add(eth);
        coins_list.add(bnb);
        coins_list.add(xrp);
        coins_list.add(ada);
        coins_list.add(rvn);
        coins_list.add(firo);
        coins_list.add(doge);
        coins_list.add(vrsc);
        coins_list.add(usdt);

        CoinListAdapter customAdapter = new CoinListAdapter(coins_list,this);
        yourListView .setAdapter(customAdapter);
    }
}