package com.jthomann.cff_mvvm1.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jthomann.cff_mvvm1.R;
import com.jthomann.cff_mvvm1.databinding.ActivityMainBinding;
import com.jthomann.cff_mvvm1.viewModel.MainViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private MainViewModel mainViewModel;
    private ActivityMainBinding mainBinding;
    private ActionBarDrawerToggle toggle;

    //    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel();
        mainBinding.setVModel(mainViewModel);
        mainBinding.setActivity(this);
        mainBinding.executePendingBindings();

        setSupportActionBar(mainBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_view_list);

        // create Firebase instances and references
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mainBinding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // if user not logged in, sends to LoginActivity
    // also calls checkUserExistence
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null){
            sendToLogin();
        } else {
            checkUserExistence();
        }
    }

    // checkUserExistence method checks the database to see if the user has setup their account
    // if no user_id is found, then the user is sent to the ChooseTypeActivity to begin their
    // account setup
    private void checkUserExistence() {

        final String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(current_user_id)) {

                    SendUserToChooseActivity();
                    Toast.makeText(MainActivity.this, "Please setup your account before using the app.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "User exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });


    }

    // Method that when called, sends user to ChooseActivity
    private void SendUserToChooseActivity() {

        Intent sentToChoose = new Intent(this, ChooseTypeActivity.class);
        startActivity(sentToChoose);
        finish();

    }

    // method when called logs current user out
    private void logout() {
        mAuth.signOut();
        sendToLogin();
    }

    // method when called sends user to login
    private void sendToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
