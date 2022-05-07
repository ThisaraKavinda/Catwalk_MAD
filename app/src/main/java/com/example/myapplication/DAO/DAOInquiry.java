package com.example.myapplication.DAO;

import com.example.myapplication.Model.Inquiry;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DAOInquiry {

    private DatabaseReference databaseReference;

    public DAOInquiry() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Inquiry.class.getSimpleName());
    }

    public Task<Void> add(Inquiry inquiry) {
        return databaseReference.push().setValue(inquiry);
    }

//    public Task<Void> viewAll() {
//        return DataSnapshot();
//    }

    public Task<Void> remove(String key) {
        return databaseReference.child(key).removeValue();
    }

    public Query selectInquiry(String title) {
        Query query =  databaseReference.child(Inquiry.class.getSimpleName()).orderByChild("title").equalTo(title);
        return query;
    }

}
