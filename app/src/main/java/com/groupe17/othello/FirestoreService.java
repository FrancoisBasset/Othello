package com.groupe17.othello;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import static android.support.v4.content.ContextCompat.getSystemService;

public class FirestoreService {
    private Context context;

    public FirestoreService(Context context) {
        this.context = context;
    }

    public String getMacAddress() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }

    public void put(String player, double percent) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, String> map = new HashMap<>();
        map.put("mac", getMacAddress());
        map.put("player", player);
        map.put("percent", String.valueOf(percent));

        db.collection("accounts").add(map);
    }

    public void getAllResults() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("accounts").whereEqualTo("mac", getMacAddress());

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                final List<Result> results = new ArrayList<>();

                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                    switch (doc.getString("player")) {
                        case "white":
                            results.add(new Result(DiskColor.White, Double.parseDouble(doc.getString("percent"))));
                            break;
                        case "black":
                            results.add(new Result(DiskColor.White, Double.parseDouble(doc.getString("percent"))));
                            break;
                    }
                }

                ListView listView = ((LastGamesActivity) context).findViewById(R.id.lastGames_listView);
                listView.setAdapter(new ResultAdapter(context, results));
            }
        });
    }
}