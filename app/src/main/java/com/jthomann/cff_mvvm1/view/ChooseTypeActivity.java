package com.jthomann.cff_mvvm1.view;

import android.content.Intent;
import android.os.Bundle;

import com.jthomann.cff_mvvm1.R;
import com.jthomann.cff_mvvm1.databinding.ActivityChooseTypeBinding;
import com.jthomann.cff_mvvm1.model.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class ChooseTypeActivity extends AppCompatActivity {

    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityChooseTypeBinding chooseTypeBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_type);

        chooseTypeBinding.setActivity(this);
        chooseTypeBinding.executePendingBindings();
    }

    public void sendToDevSetup() {
        user.setAccountType("developer");
        Intent sendToDev = new Intent(this, DevAccSetupActivity.class);
        startActivity(sendToDev);
        finish();
    }
}
