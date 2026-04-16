package com.voice.changer.sound.effects.recorder.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.voice.changer.sound.effects.recorder.R;
import com.voice.changer.sound.effects.recorder.audio.effects.VoiceEffect;

import java.util.ArrayList;
import java.util.List;

public class EffectGridAdapter extends RecyclerView.Adapter<EffectGridAdapter.Holder> {
    public interface OnEffectClick {
        void onClick(VoiceEffect effect);
    }

    private final List<VoiceEffect> data = new ArrayList<>();
    private VoiceEffect selected;
    private final OnEffectClick callback;

    public EffectGridAdapter(OnEffectClick callback) {
        this.callback = callback;
    }

    public void submit(List<VoiceEffect> effects) {
        data.clear();
        if (effects != null) {
            data.addAll(effects);
        }
        notifyDataSetChanged();
    }

    public void setSelected(VoiceEffect effect) {
        selected = effect;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_effect_grid, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        VoiceEffect item = data.get(position);
        holder.tvName.setText(item.getName());
        holder.tvEmoji.setText(item.getEmoji());
        boolean isSelected = selected != null && selected.getName().equals(item.getName());
        holder.card.setBackgroundResource(isSelected ? R.drawable.bg_effect_selected : R.drawable.bg_effect_unselected);
        holder.itemView.setOnClickListener(v -> {
            selected = item;
            notifyDataSetChanged();
            if (callback != null) {
                callback.onClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        final View card;
        final TextView tvEmoji;
        final TextView tvName;

        Holder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardEffect);
            tvEmoji = itemView.findViewById(R.id.tvEmoji);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
