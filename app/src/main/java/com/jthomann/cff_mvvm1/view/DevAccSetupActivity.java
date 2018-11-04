package com.jthomann.cff_mvvm1.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jthomann.cff_mvvm1.R;
import com.jthomann.cff_mvvm1.databinding.ActivityDevAccSetupBinding;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.model.SpinnerModel;
import com.jthomann.cff_mvvm1.model.User;
import com.jthomann.cff_mvvm1.utils.MyUtils;
import com.jthomann.cff_mvvm1.viewModel.DevAccSetupViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class DevAccSetupActivity extends AppCompatActivity implements Observer<String> {

    private DevAccSetupViewModel viewModel;
    private SpinnerModel spinnerModel;
    private ActivityDevAccSetupBinding devAccSetupBinding;
    private User userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        devAccSetupBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_dev_acc_setup);

        spinnerModel = new SpinnerModel();
        userModel = new User();

        viewModel = new DevAccSetupViewModel(
                getResources().getStringArray(R.array.operating_systems),
                getResources().getStringArray(R.array.programming_languages),
                spinnerModel);
        devAccSetupBinding.setActivity(this);
        devAccSetupBinding.setVModel(viewModel);
        devAccSetupBinding.setModel(spinnerModel);
        devAccSetupBinding.setUserModel(userModel);
        devAccSetupBinding.executePendingBindings();

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.removeObserver(this);
    }

    @Override
    public void onObserve(int event, String eventMessage) {
        if (event == MyUtils.SHOW_TOAST) {
            Toast.makeText(this, eventMessage, Toast.LENGTH_SHORT).show();
        } else if (event == MyUtils.OPEN_ACTIVITY) {
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent intent = new Intent(DevAccSetupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void multiChoiceDialog(View view) {

        final String[] items = getResources().getStringArray(R.array.programming_languages);
        final ArrayList<Integer> selectedList = new ArrayList<>();
        ArrayList<String> selectedStrings = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Programming Languages");
        builder.setMultiChoiceItems(items, null,
                (mInterface, which, isChecked) -> {
                    if (isChecked) {
                        selectedList.add(which);
                    } else if (selectedList.contains(which)) {
                        selectedList.remove(Integer.valueOf(which));
                    }
                });

        builder.setPositiveButton("DONE", (mInterface, i) -> {

            for (int j = 0; j < selectedList.size(); j++) {
                selectedStrings.add(items[selectedList.get(j)]);
            }

            spinnerModel.setSelectedStrings(selectedStrings);

            Toast.makeText(getApplicationContext(), "Test: " +
                    Arrays.toString(spinnerModel.getSelectedStrings().toArray()), Toast.LENGTH_SHORT).show();
        });

        builder.show();


    }

}
