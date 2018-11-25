package com.jthomann.cff_mvvm1.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jthomann.cff_mvvm1.R;
import com.jthomann.cff_mvvm1.databinding.ActivityMainBinding;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.utils.MyUtils;
import com.jthomann.cff_mvvm1.viewModel.MainViewModel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity implements Observer<String>
{

    private FirebaseAuth mAuth;
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
            mainViewModel.checkUserExistence();
        }
    }

    // checkUserExistence method checks the database to see if the user has setup their account
    // if no user_id is found, then the user is sent to the ChooseTypeActivity to begin their
    // account setup
    @Override
    public void onObserve(int event, String eventMessage) {
        if (event == MyUtils.SHOW_TOAST) {
            Toast.makeText(this, eventMessage, Toast.LENGTH_SHORT).show();
        } else if (event == MyUtils.OPEN_ACTIVITY) {
            SendUserToChooseActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.addObserver(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mainViewModel.removeObserver(this);
    }

    private void SendUserToChooseActivity() {
        Intent sentToChoose = new Intent(this, ChooseTypeActivity.class);
        startActivity(sentToChoose);
        finish();
    }

    private void sendToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
