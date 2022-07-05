package com.example.appbanco.view.Crypto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.adapter.CryptoList;
import com.example.appbanco.adapter.CryptoListAdapter;
import com.example.appbanco.model.APIClient;
import com.example.appbanco.model.CryptoMyApi;
import com.example.appbanco.model.DataModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Crypto extends AppCompatActivity {

    private List<DataModel> cryptoList = null;
    CryptoMyApi myApi;
    private RecyclerView recyclerView;
    CryptoListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto);
        myApi = APIClient.getClient().create(CryptoMyApi.class);
        getCoinList();
        initRecyclerView();
    }

    private void initRecyclerView() {
        // Lookup the recyclerview in activity layout
        recyclerView = findViewById(R.id.coinList);

        // Initialize data
        cryptoList = new ArrayList<>();

        // Create adapter passing in the sample user data
        adapter = new CryptoListAdapter(cryptoList ,getBaseContext());

        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);

        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setClickListener(new CryptoListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(MainActivity.this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Crypto.this, CryptoNext.class);
                intent.putExtra("coin", adapter.getItemId(position));
                startActivity(intent);
            }
        });
    }

    private void getCoinList() {
        Call<CryptoList> call2 = myApi.doGetUserList("100");
        call2.enqueue(new Callback<CryptoList>() {
            @Override
            public void onResponse(Call<CryptoList> call, Response<CryptoList> response) {
                CryptoList list = response.body();

                // do not reinitialize an existing reference used by an adapter
                // add to the existing list
                cryptoList.clear();
                cryptoList.addAll(list.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CryptoList> call, Throwable t) {
                Toast.makeText(Crypto.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.d("XXXX", t.getLocalizedMessage());
                call.cancel();

            }
        });
    }
}