package com.voice.changer.sound.effects.recorder.presentation.savedrecordings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.voice.changer.sound.effects.recorder.R;
import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecordingAdapter extends RecyclerView.Adapter<RecordingAdapter.Holder> {
    private final List<RecordingEntity> items = new ArrayList<>();

    public void submit(List<RecordingEntity> data) {
        items.clear();
        if (data != null) {
            items.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recording, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        RecordingEntity item = items.get(position);
        holder.tvName.setText(item.fileName);
        holder.tvMeta.setText((item.effectName == null ? "Effect" : item.effectName) + " • " + formatMs(item.durationMs));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String formatMs(int ms) {
        int total = Math.max(ms, 0) / 1000;
        return String.format(Locale.US, "%02d:%02d", total / 60, total % 60);
    }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvMeta;

        Holder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRecordingName);
            tvMeta = itemView.findViewById(R.id.tvRecordingMeta);
        }
    }
}
