package java15.projectrestaurant.service.impl;

import java15.projectrestaurant.dto.request.CategoryRequest;
import java15.projectrestaurant.dto.response.CategoryView;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.SubcategoryView;
import java15.projectrestaurant.exception.BadRequestException;
import java15.projectrestaurant.mapper.CategoryViewMapper;
import java15.projectrestaurant.mapper.SubcategoryViewMapper;
import java15.projectrestaurant.model.Category;
import java15.projectrestaurant.repository.CategoryRepository;
import java15.projectrestaurant.repository.SubcategoryRepository;
import java15.projectrestaurant.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryViewMapper categoryViewMapper;
    private final SubcategoryViewMapper subcategoryViewMapper;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public CategoryView createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new BadRequestException("Category with name '" + request.name() + "' already exists.");
        }

        Category category = request.toEntity(new Category());

        Category savedCategory = categoryRepository.save(category);

        return categoryViewMapper.toView(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<CategoryView> getCategories(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<Category> categoryPage = categoryRepository.findAll(pageRequest);

        List<CategoryView> categoryViews = categoryPage.getContent().stream()
                .map(category -> {
                    List<SubcategoryView> subcategoryViews = subcategoryRepository
                            .findAllByCategoryId(category.getId())
                            .stream()
                            .map(subcategoryViewMapper::toView)
                            .collect(Collectors.toList());

                    return CategoryView.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .subcategories(subcategoryViews)
                            .build();
                })
                .collect(Collectors.toList());

        Page<CategoryView> categoryViewPage = new PageImpl<>(categoryViews, pageRequest, categoryPage.getTotalElements());

        return new PaginationResponse<CategoryView>().setValuesTo(categoryViewPage);
    }

    @Override
    public CategoryView getCategoryById(Long id) {
        Category category = categoryRepository.findByIdOrThrow(id);
        return categoryViewMapper.toView(category);
    }

    @Override
    public CategoryView updateCategory(Long id, CategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new BadRequestException("Category with name '" + request.name() + "' already exists.");
        }

        Category category = categoryRepository.findByIdOrThrow(id);

        Category updatedCategory = request.toEntity(category);

        Category savedCategory = categoryRepository.save(updatedCategory);

        return categoryViewMapper.toView(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        boolean existsById = categoryRepository.existsById(id);

        if (!existsById) {
            throw new BadRequestException("Category with ID: " + id + " not found.");
        }

        categoryRepository.deleteById(id);
    }
}
