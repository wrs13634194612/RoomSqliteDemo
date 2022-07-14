package com.example.myapplication;


import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;


/**
 * @author acampbell
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    private final List<Category> categories;

    public CategoryAdapter(@NonNull Context context,
                           @LayoutRes int resource,
                           List<Category> categories) {
        super(context, resource, categories);
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Nullable
    @Override
    public Category getItem(int position) {
        if (position == 0) {
            Category category = new Category();
            category.setId(0);
            category.setName(getContext().getString(R.string.create_new_category));
            return category;
        }
        return super.getItem(position - 1);
    }

    public int getCategoryPosition(@Nullable Long categoryId) {
        if (categoryId != null) {
            for (int i = 0; i < categories.size(); i++) {
                if (categoryId == categories.get(i).getId()) {
                    return i + 1;
                }
            }
        }
        return -1;
    }
}
