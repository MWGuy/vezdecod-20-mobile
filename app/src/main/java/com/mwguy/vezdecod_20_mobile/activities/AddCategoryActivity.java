package com.mwguy.vezdecod_20_mobile.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.mwguy.vezdecod_20_mobile.R;
import com.mwguy.vezdecod_20_mobile.model.Category;
import com.mwguy.vezdecod_20_mobile.model.ContentType;
import com.mwguy.vezdecod_20_mobile.serives.CategoryService;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private CategoryService categoryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category_activity);

        categoryService = new CategoryService(this);

        Button button = findViewById(R.id.save_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextInputEditText editText = findViewById(R.id.title_field);
        SwitchMaterial switchMaterial = findViewById(R.id.password_category_switch);

        if (editText.getText() == null || editText.getText().length() == 0) {
            editText.setError("Название категории не может быть пустым");
            return;
        }

        Category category = new Category();
        category.setTitle(editText.getText().toString());
        category.setContentType(switchMaterial.isChecked() ? ContentType.PASSWORD : ContentType.PLAIN_TEXT);
        categoryService.save(category);

        finish();
    }
}
