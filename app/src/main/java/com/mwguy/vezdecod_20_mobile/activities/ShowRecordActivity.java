package com.mwguy.vezdecod_20_mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.mwguy.vezdecod_20_mobile.R;
import com.mwguy.vezdecod_20_mobile.model.Record;
import com.mwguy.vezdecod_20_mobile.serives.CategoryService;
import com.mwguy.vezdecod_20_mobile.serives.RecordService;

import java.util.Date;

public class ShowRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private RecordService recordService;
    private CategoryService categoryService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_record);

        recordService = new RecordService(this);
        categoryService = new CategoryService(this);

        Bundle bundle = getIntent().getExtras();
        Record record = recordService.getById(bundle.getString("id"));

        TextView titleTextView = findViewById(R.id.record_title);
        titleTextView.setText(record.getTitle());

        TextView dateTextView = findViewById(R.id.record_date);
        dateTextView.setText(new Date(record.getTimestamp()).toString());

        TextView categoryTextView = findViewById(R.id.record_category);
        categoryTextView.setText(categoryService.getById(record.getCategoryId()).getTitle());

        TextView contentTextView = findViewById(R.id.record_content);
        contentTextView.setText(record.getContent());

        Button removeButton = findViewById(R.id.record_remove_button);
        removeButton.setOnClickListener(this);

        Button editButton = findViewById(R.id.record_edit_button);
        editButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Record record = recordService.getById(getIntent().getExtras().getString("id"));
        if (view.getId() == R.id.record_remove_button) {
            recordService.remove(record);
            finish();
            return;
        }

        // edit
        Intent intent = new Intent(this, EditRecordActivity.class);
        intent.putExtra("id", record.getId());
        finish();
        startActivity(intent);
    }
}
