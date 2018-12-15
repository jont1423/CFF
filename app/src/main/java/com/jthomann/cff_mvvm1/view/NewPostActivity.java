package com.jthomann.cff_mvvm1.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jthomann.cff_mvvm1.R;
import com.jthomann.cff_mvvm1.databinding.ActivityNewPostBinding;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.model.NewPostModel;
import com.jthomann.cff_mvvm1.utils.MyUtils;
import com.jthomann.cff_mvvm1.viewModel.NewPostViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

// change the names here, I don't like looking at so many 'news'

public class NewPostActivity extends AppCompatActivity  implements Observer<String> {

    private ActivityNewPostBinding newPostBinding;
    private NewPostModel newPostModel;
    private NewPostViewModel newPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newPostModel = new NewPostModel();
        newPostViewModel = new NewPostViewModel(newPostModel);

        newPostBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_post);
        newPostBinding.setActivity(this);
        newPostBinding.setVModel(newPostViewModel);
        newPostBinding.setModel(newPostModel);
        newPostBinding.executePendingBindings();

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

            newPostModel.setSelectedStrings(selectedStrings);

            Toast.makeText(getApplicationContext(), "Test: " +
                    Arrays.toString(newPostModel.getSelectedStrings().toArray()), Toast.LENGTH_SHORT).show();
        });

        builder.show();

    }


    @Override
    public void onObserve(int event, String eventMessage) {
        if (event == MyUtils.SHOW_TOAST) {
            Toast.makeText(this, eventMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        newPostViewModel.addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        newPostViewModel.removeObserver(this);
    }
}
