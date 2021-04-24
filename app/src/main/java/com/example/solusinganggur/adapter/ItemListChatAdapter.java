package com.example.solusinganggur.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.solusinganggur.R;

public class ItemListChatAdapter extends RecyclerView.Adapter<ItemListChatAdapter.ViewHolder> {
    private static final String TAG = "ItemListChatAdapter";

    private String[] pengirim, pesan;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView, textView2;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });
            textView = (TextView) v.findViewById(R.id.txt_namaprofile_message);
            textView2 = (TextView) v.findViewById(R.id.txt_chat_message);
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getTextView2() {
            return textView2;
        }

    }

    public ItemListChatAdapter(String[] pengirim, String[] pesan) {
        this.pengirim = pengirim;
        this.pesan = pesan;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_user_message, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTextView().setText(pengirim[position]);
        viewHolder.getTextView2().setText(pesan[position]);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

