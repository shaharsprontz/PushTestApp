package com.example.shahar.pushtestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextView mConditionTextView;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("apps").child("condition");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConditionTextView = (TextView) findViewById(R.id.downApps);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String text = dataSnapshot.getValue(String.class);
                    mConditionTextView.setText(text);

                String username = "puf";
                FirebaseMessaging.getInstance().subscribeToTopic("user_"+username);
                sendNotificationToUser("puf", "Hi there puf!");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static void sendNotificationToUser(String user, final String message) {
        Firebase ref = new Firebase("https://pushtest-eb14f.firebaseio.com");
        final Firebase notifications = ref.child("condition");

        Map notification = new HashMap<>();
        notification.put("username", user);
        notification.put("message", message);

        notifications.push().setValue(notification);
    }

}







