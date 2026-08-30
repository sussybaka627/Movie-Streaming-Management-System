package controllers;

import data.FileHandler;
import models.datastructures.MyLinkedList;
import models.entities.Category;

public class CategoryController {
    private MyLinkedList<Category> categories;
    private FileHandler fileHandler;

    public CategoryController() {
        this.fileHandler = new FileHandler();
        this.categories = fileHandler.loadCategories();
        if (this.categories == null) {
            this.categories = new MyLinkedList<>();
        }
    }

    public MyLinkedList<Category> getAllCategories() {
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
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            if (cat.getId().equalsIgnoreCase(id)) {
                return cat;
            }
        }
        return null;
    }
}