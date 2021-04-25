package com.mwguy.vezdecod_20_mobile.serives;

import android.content.Context;
import com.mwguy.vezdecod_20_mobile.model.Category;
import com.mwguy.vezdecod_20_mobile.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(Context context) {
        this.categoryRepository = new CategoryRepository(context);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        for (String id : categoryRepository.getAllIds()) {
            categories.add(getById(id));
        }

        return categories;
    }

    public Category getById(String id) {
        return categoryRepository.getCategoryById(id);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
