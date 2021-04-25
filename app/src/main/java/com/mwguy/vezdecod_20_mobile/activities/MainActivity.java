package com.mwguy.vezdecod_20_mobile.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mwguy.vezdecod_20_mobile.R;
import com.mwguy.vezdecod_20_mobile.model.Category;
import com.mwguy.vezdecod_20_mobile.model.ContentType;
import com.mwguy.vezdecod_20_mobile.model.Record;
import com.mwguy.vezdecod_20_mobile.serives.CategoryService;
import com.mwguy.vezdecod_20_mobile.serives.RecordService;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CategoryService categoryService;
    private RecordService recordService;
    private Category selectedCategory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordService = new RecordService(this);
        categoryService = new CategoryService(this);

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(this);

        ChipGroup chipGroup = findViewById(R.id.chips_group);

        Chip allChip = new Chip(this);
        allChip.setText("Всё");
        allChip.setOnClickListener((v) -> showRecords(null));
        chipGroup.addView(allChip);

        for (Category category : categoryService.getAllCategories()) {
            Chip chip = new Chip(this);
            chip.setText(category.getTitle());
            chip.setOnClickListener((v) -> showRecords(category));

            if (category.getContentType().equals(ContentType.PASSWORD)) {
                chip.setChipIcon(getDrawable(R.drawable.ic_baseline_lock_24));
            }

            chipGroup.addView(chip);
        }
    }

    @SuppressLint("SetTextI18n")
    public void showRecords(@Nullable Category category) {
        selectedCategory = category;

        LinearLayout layout = findViewById(R.id.scroll_view);
        layout.removeAllViews();

        List<Record> recordList = category == null ? recordService.getAllRecords() : recordService.getByCategory(category);
        for (Record record : recordList) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setOnClickListener(v -> {
                Intent intent = new Intent(this, ShowRecordActivity.class);
                intent.putExtra("id", record.getId());
                startActivity(intent);
            });

            TextView textView = new TextView(this);
            textView.setText(record.getTitle());
            textView.setTextSize(24);
            linearLayout.addView(textView);

            Category currentCategory = categoryService.getById(record.getCategoryId());

            TextView categoryTextView = new TextView(this);
            categoryTextView.setText(currentCategory.getTitle() + " - " + new Date(record.getTimestamp()));
            linearLayout.addView(categoryTextView);

            layout.addView(linearLayout);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showRecords(selectedCategory);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.floating_action_button) {
            showMenu(view, R.menu.add_conent);
        }
    }

    private void showMenu(View view, @MenuRes int menuRes) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            startActivity(new Intent(this, item.getItemId() == R.id.record ? AddRecordActivity.class : AddCategoryActivity.class));
            return true;
        });

        popup.show();
    }
}
