package com.mwguy.vezdecod_20_mobile.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.mwguy.vezdecod_20_mobile.R;
import com.mwguy.vezdecod_20_mobile.model.Category;
import com.mwguy.vezdecod_20_mobile.model.Record;
import com.mwguy.vezdecod_20_mobile.serives.CategoryService;
import com.mwguy.vezdecod_20_mobile.serives.RecordService;

import java.util.ArrayList;
import java.util.List;

public class EditRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private CategoryService categoryService;
    private RecordService recordService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record_activity);

        categoryService = new CategoryService(this);
        recordService = new RecordService(this);

        List<String> categories = new ArrayList<>();
        for (Category category : categoryService.getAllCategories()) {
            categories.add(category.getTitle());
        }

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.category_field);
        autoCompleteTextView.setInputType(0);
        autoCompleteTextView.setAdapter(new ArrayAdapter<>(this, R.layout.list_item, categories));

        Button button = findViewById(R.id.save_button);
        button.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        Record record = recordService.getById(bundle.getString("id"));

        TextInputEditText titleEditText = findViewById(R.id.title_field);
        titleEditText.setText(record.getTitle());

        TextInputEditText contentEditText = findViewById(R.id.content_field);
        contentEditText.setText(record.getContent());

        autoCompleteTextView.setText(categoryService.getById(record.getCategoryId()).getTitle());
    }

    @Override
    public void onClick(View v) {
        TextInputEditText titleEditText = findViewById(R.id.title_field);
        TextInputEditText contentEditText = findViewById(R.id.content_field);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.category_field);

        if (titleEditText.getText() == null || titleEditText.getText().length() == 0) {
            titleEditText.setError("Название не должно быть пустым");
            return;
        }

        if (contentEditText.getText() == null || contentEditText.getText().length() == 0) {
            contentEditText.setError("Контент не должен быть пустым");
            return;
        }

        if (autoCompleteTextView.getText() == null || contentEditText.getText().length() == 0) {
            autoCompleteTextView.setError("Категория не может быть пустой");
            return;
        }

        Category category = null;
        for (Category categoryItem : categoryService.getAllCategories()) {
            if (categoryItem.getTitle().equals(autoCompleteTextView.getText().toString())) {
                category = categoryItem;
                break;
            }
        }

        if (category == null) {
            return;
        }

        Bundle bundle = getIntent().getExtras();
        Record record = recordService.getById(bundle.getString("id"));
        record.setTitle(titleEditText.getText().toString());
        record.setContent(contentEditText.getText().toString());
        record.setTimestamp(System.currentTimeMillis());
        record.setCategoryId(category.getId());
        recordService.save(record);

        finish();
    }
}
