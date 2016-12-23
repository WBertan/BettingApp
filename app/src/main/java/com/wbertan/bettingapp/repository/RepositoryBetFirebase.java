package com.wbertan.bettingapp.repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.wbertan.bettingapp.generic.CallbackError;
import com.wbertan.bettingapp.model.Bet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

class RepositoryBetFirebase extends RepositoryBet {
    RepositoryBetFirebase() {
        super();
    }

    @Override
    public void load(final int aRequestCode) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceBet = database.getReference("bet");
        databaseReferenceBet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onRepositorySuccess(aRequestCode, dataSnapshot.getValue(new GenericTypeIndicator<List<Bet>>(){}));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                onRepositoryError(aRequestCode, new CallbackError(-1, databaseError.getMessage()));
            }
        });
    }

    @Override
    public void loadFavorite(final int aRequestCode) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceUserFavorite = database.getReference(FirebaseInstanceId.getInstance().getId()).child("favorite");
        databaseReferenceUserFavorite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                onRepositorySuccess(aRequestCode, dataSnapshot.getValue(new GenericTypeIndicator<List<Bet>>() {})); // A little buggy, sometimes get exception trying to get HashMap<>
                List<Bet> listBet = new ArrayList<Bet>();
                for(DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()){
                    listBet.add(dataSnapshotChild.getValue(Bet.class));
                }
                onRepositorySuccess(aRequestCode, listBet);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                onRepositoryError(aRequestCode, new CallbackError(-1, databaseError.getMessage()));
            }
        });
    }

    @Override
    public void addToFavorite(Bet aBet, int aRequestCode) {
        aBet.setFavorite(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceUserFavorite = database.getReference(FirebaseInstanceId.getInstance().getId()).child("favorite");
        databaseReferenceUserFavorite.child(String.valueOf(aBet.getBetId())).setValue(aBet);
        onRepositorySuccess(aRequestCode, (List<Bet>) null);
    }

    @Override
    public void removeFromFavorite(Bet aBet, int aRequestCode) {
        aBet.setFavorite(false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceUserFavorite = database.getReference(FirebaseInstanceId.getInstance().getId()).child("favorite");
        databaseReferenceUserFavorite.child(String.valueOf(aBet.getBetId())).removeValue();
        onRepositorySuccess(aRequestCode, (List<Bet>) null);
    }
}
