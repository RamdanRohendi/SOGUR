package com.example.solusinganggur.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.solusinganggur.R;
import com.example.solusinganggur.UserMessageActivity;
import com.example.solusinganggur.entity.ChatRoom;
import com.example.solusinganggur.entity.detailPesan;
import java.util.ArrayList;

public class ItemChatRoomAdapter extends RecyclerView.Adapter<ItemChatRoomAdapter.ViewHolder> {
    private static final String TAG = "ItemListChatAdapter";

    private final ArrayList<ChatRoom> pesan;
    private Context context;

    public ItemChatRoomAdapter(ArrayList<ChatRoom> pesan) {
        this.pesan = pesan;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView, textView2;
        private ConstraintLayout itemKontak;

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
            itemKontak = view.findViewById(R.id.item_kontak);
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
                .inflate(R.layout.item_list_user_chat_left, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // viewHolder.getTextView().setText(pesan.get(position).getPesan());
        // viewHolder.getTextView().setText(pesan.get(position).getPengirim());
        viewHolder.itemKontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, UserMessageActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pesan.size();
    }
}

