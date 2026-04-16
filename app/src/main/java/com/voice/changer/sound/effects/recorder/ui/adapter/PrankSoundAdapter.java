package com.voice.changer.sound.effects.recorder.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.voice.changer.sound.effects.recorder.R;

public class PrankSoundAdapter extends RecyclerView.Adapter<PrankSoundAdapter.Holder> {
    private static final String[] EMOJI = {"🤣", "💨", "🚗", "🤧", "😴", "💥", "🐶", "🐱", "🦁"};
    private static final String[] LABEL = {"Funny", "Fart", "Engine", "Sneeze", "Snore", "Breaking", "Dog", "Cat", "Lion"};

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prank_sound, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvEmoji.setText(EMOJI[position % EMOJI.length]);
        holder.tvCategory.setText(LABEL[position % LABEL.length]);
    }

    @Override
    public int getItemCount() {
        return 18;
    }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView tvEmoji;
        final TextView tvCategory;

        Holder(@NonNull View itemView) {
            super(itemView);
            tvEmoji = itemView.findViewById(R.id.tvPrankEmoji);
            tvCategory = itemView.findViewById(R.id.tvPrankCategory);
        }
    }
}
