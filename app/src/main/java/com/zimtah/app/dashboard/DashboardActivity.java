package com.zimtah.app.dashboard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private GamesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recycler = findViewById(R.id.recyclerGames);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new GamesAdapter(new GamesAdapter.OnPlayClickListener() {
            @Override
            public void onPlayClicked(Game game) {
                openGameLink(game.getUrl());
            }
        });
        recycler.setAdapter(adapter);

        loadGames();
    }

    private void openGameLink(String url) {
        if (url == null || url.isEmpty()) {
            Snackbar.make(recycler, "No link available for this game", Snackbar.LENGTH_SHORT).show();
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(recycler, "No application can open this link", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void loadGames() {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("games").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Game> games = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String id = doc.getId();
                        String title = doc.getString("title");
                        if (title == null) title = "Untitled";
                        String description = doc.getString("description");
                        if (description == null) description = "";
                        String url = doc.getString("url");
                        if (url == null) url = "";
                        String imageUrl = doc.getString("imageUrl");
                        games.add(new Game(id, title, description, url, imageUrl));
                    }
                    if (games.isEmpty()) {
                        adapter.setGames(builtinGames());
                    } else {
                        adapter.setGames(games);
                    }
                })
                .addOnFailureListener(e -> adapter.setGames(builtinGames()));
        } catch (Exception e) {
            // Firestore not configured or SDK missing
            adapter.setGames(builtinGames());
        }
    }

    private List<Game> builtinGames() {
        List<Game> list = new ArrayList<>();
        list.add(new Game("efootball", "eFootball", "Play matches together online", "https://play.google.com/store/apps/details?id=jp.konami.efootball", null));
        list.add(new Game("dls", "DLS", "Dream League Soccer - play with friends", "https://play.google.com/store/apps/details?id=com.firsttouchgames.dls3", null));
        list.add(new Game("amongus", "Among Us", "Quick party games for groups", "https://play.google.com/store/apps/details?id=com.innersloth.spacemafia", null));
        list.add(new Game("pubg", "PUBG Mobile", "Battle royale with friends", "https://play.google.com/store/apps/details?id=com.tencent.ig", null));
        return list;
    }
}
