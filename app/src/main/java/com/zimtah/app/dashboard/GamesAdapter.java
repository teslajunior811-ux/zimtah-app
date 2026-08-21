package com.zimtah.app.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.VH> {

    public interface OnPlayClickListener {
        void onPlayClicked(Game game);
    }

    private final List<Game> games = new ArrayList<>();
    private final OnPlayClickListener listener;

    public GamesAdapter(OnPlayClickListener listener) {
        this.listener = listener;
    }

    public void setGames(List<Game> list) {
        games.clear();
        if (list != null) games.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Game game = games.get(position);
        holder.bind(game);
        // attach click listener here to capture current game
        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlayClicked(game);
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvDesc;
        private final Button btnPlay;

        VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            btnPlay = itemView.findViewById(R.id.btnPlay);
        }

        void bind(final Game game) {
            tvTitle.setText(game.getTitle());
            tvDesc.setText(game.getDescription());
        }
    }
}
