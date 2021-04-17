package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.payload.CategoryDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    // ADD
    public Result add(CategoryDto categoryDto) {
        boolean existsByNameAndParentCategoryId = categoryRepository.existsByNameAndParentCategoryId(categoryDto.getName(), categoryDto.getParentId());
        if (existsByNameAndParentCategoryId) {
            return new Result("Category already exist", false);
        }

        Category category = new Category();
        category.setName(categoryDto.getName());

        if (categoryDto.getParentId() != null) {
            Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentId());
            if (!optionalParentCategory.isPresent()) {
                return new Result("ParentCategory not found", false);
            }
            category.setParentCategory(optionalParentCategory.get());
        }

        categoryRepository.save(category);
        return new Result("Successfully added", true);
    }

    // GET
    public List<Category> get() {
        return categoryRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return new Result("Category not found", false);
        }

        return new Result("Success", true, optionalCategory.get());
    }

    // DELETE
    public Result delete(Integer id) {
        try {
            categoryRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, CategoryDto categoryDto) {
        boolean existsByNameAndParentCategoryId = categoryRepository.existsByNameAndParentCategoryId(categoryDto.getName(), categoryDto.getParentId());
        if (existsByNameAndParentCategoryId) {
            return new Result("Category already exist", false);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return new Result("Category not found", false);
        }

        Category editedCategory = optionalCategory.get();
        editedCategory.setName(categoryDto.getName());
        editedCategory.setActive(categoryDto.isActive());
        categoryRepository.save(editedCategory);
        return new Result("Successfully edited", true);
    }
}
