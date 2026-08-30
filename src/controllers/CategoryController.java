package controllers;

import data.FileHandler;
import models.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryController {
    private List<Category> categories;
    private FileHandler fileHandler;

    public CategoryController() {
        this.fileHandler = new FileHandler();
        this.categories = fileHandler.loadCategories();
        if (this.categories == null) {
            this.categories = new ArrayList<>();
        }
    }

    public List<Category> getAllCategories() {
        return categories;
    }

    public boolean addCategory(String id, String name) {
        if (findCategoryById(id) != null) {
            return false; 
        }
        Category newCategory = new Category(id, name);
        categories.add(newCategory);
        fileHandler.saveCategories(categories);
        return true;
    }

    public boolean updateCategory(String id, String newName) {
        Category category = findCategoryById(id);
        if (category == null) {
            return false; // Không tìm thấy ID để sửa
        }
        category.setName(newName);
        fileHandler.saveCategories(categories);
        return true;
    }

    public boolean deleteCategory(String id) {
        Category category = findCategoryById(id);
        if (category == null) {
            return false; 
        }
        categories.remove(category);
        fileHandler.saveCategories(categories);
        return true;
    }

    public Category findCategoryById(String id) {
        for (Category cat : categories) {
            if (cat.getId().equalsIgnoreCase(id)) {
                return cat;
            }
        }
        return null;
    }
}