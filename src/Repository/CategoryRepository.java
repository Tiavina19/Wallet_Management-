package Repository;

import Models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements CategoryCrudOperations {

    private List<Category> categories = new ArrayList<>();

    @Override
    public void addCategory(Category category) {
        categories.add(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    @Override
    public void deleteCategory(int categoryId) {
        categories.removeIf(cat -> cat.getId() == categoryId);
    }

    @Override
    public void updateCategory(Category updatedCategory) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == updatedCategory.getId()) {
                categories.set(i, updatedCategory);
                break;
            }
        }
    }
}


