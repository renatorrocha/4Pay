package com.example.appbanco.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanco.R;
import com.example.appbanco.model.DataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CryptoListAdapter extends RecyclerView.Adapter<CryptoListAdapter.ViewHolder> {

    private List<DataModel> mData;
    private Context context;
    private ItemClickListener mClickListener;
    // data is passed into the constructor
    public CryptoListAdapter(List<DataModel> data,Context context) {
        this.context=context;
        this.mData = data;
    }

    // Usually involves inflating a layout from XML and returning the holder
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.coin_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        DataModel datum = mData.get(position);

        // Set item views based on your views and data model
        TextView name = holder.name;
        name.setText(datum.getName() + " (" + datum.getSymbol() + ")");

        TextView price = holder.price_usd;
        price.setText("Price: $" + String.format("%,f", datum.getQuote().getUSD().getPrice()));

        TextView textView1h = holder.textView1h;
        textView1h.setText(String.format("1h: %.2f", datum.getQuote().getUSD().getPercentChange1h()) + "%");

        TextView textView24h = holder.textView24h;
        textView24h.setText(String.format("24h: %.2f", datum.getQuote().getUSD().getPercentChange24h()) + "%");

        TextView textView7d = holder.textView7d;
        textView7d.setText(String.format("7d: %.2f", datum.getQuote().getUSD().getPercentChange7d()) + "%");
   if(datum.getQuote().getUSD().getPercentChange7d() > 0) {
       Double.toString(datum.getQuote().getUSD().getPercentChange7d()).trim().split("-");
               textView7d.setTextColor(ContextCompat.getColor(context, R.color.teal_700));
   }else {
       textView7d.setTextColor(ContextCompat.getColor(context, R.color.vermelho));
   }
        if(datum.getQuote().getUSD().getPercentChange1h() > 0) {
            Double.toString(datum.getQuote().getUSD().getPercentChange1h()).trim().split("-");
            textView1h.setTextColor(ContextCompat.getColor(context, R.color.teal_700));
        }else {
            textView1h.setTextColor(ContextCompat.getColor(context, R.color.vermelho));
        }

        if(datum.getQuote().getUSD().getPercentChange24h() > 0) {
            Double.toString(datum.getQuote().getUSD().getPercentChange24h()).trim().split("-");
            textView24h.setTextColor(ContextCompat.getColor(context, R.color.teal_700));
        }else {
            textView24h.setTextColor(ContextCompat.getColor(context, R.color.vermelho));
        }


        Picasso.get()
                .load(new StringBuilder("https://res.cloudinary.com/dxi90ksom/image/upload/")
                        .append(datum.getSymbol().toLowerCase()).append(".png").toString())
                .error(R.drawable.coin)
                .placeholder(R.drawable.coin)
                .into(holder.coin_icon );



    }


    // Returns the total count of items in the list
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ImageView coin_icon;
        TextView name;
        TextView price_usd;
        TextView symbol;
        TextView textView1h;
        TextView textView24h;
        TextView textView7d;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            name = itemView.findViewById(R.id.name);
            symbol=itemView.findViewById(R.id.coin_symbol);
            price_usd= itemView.findViewById(R.id.priceUsdText);
            textView1h = itemView.findViewById(R.id.oneHourText);
            textView24h = itemView.findViewById(R.id.twentyFourHourText);
            textView7d = itemView.findViewById(R.id.sevenDayText);
            coin_icon=itemView.findViewById(R.id.coin_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    DataModel getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}