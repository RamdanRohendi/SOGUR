package com.example.solusinganggur.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.solusinganggur.R;
import com.example.solusinganggur.entity.detailPesan;
import java.util.ArrayList;

public class ItemListChatAdapter extends RecyclerView.Adapter<ItemListChatAdapter.ViewHolder> {
    private static final String TAG = "ItemListChatAdapter";

    private final ArrayList<detailPesan> pesan;
    private Context context;

    public ItemListChatAdapter(ArrayList<detailPesan> pesan, Context context ) {
        this.pesan = pesan;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView, textView2;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });
            textView = (TextView) view.findViewById(R.id.txt_namaprofile_message);
            textView2 = (TextView) view.findViewById(R.id.txt_chat_message);
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getTextView2() {
            return textView2;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_user_message, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTextView2().setText((CharSequence) pesan.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

