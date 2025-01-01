package java15.projectrestaurant.service.impl;

import java15.projectrestaurant.dto.request.SubcategoryRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.SubcategoryGroupedByCategoryView;
import java15.projectrestaurant.dto.response.SubcategoryView;
import java15.projectrestaurant.exception.BadRequestException;
import java15.projectrestaurant.mapper.SubcategoryViewMapper;
import java15.projectrestaurant.model.Category;
import java15.projectrestaurant.model.Subcategory;
import java15.projectrestaurant.repository.CategoryRepository;
import java15.projectrestaurant.repository.SubcategoryRepository;
import java15.projectrestaurant.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryViewMapper subcategoryViewMapper;

    @Override
    @Transactional
    public SubcategoryView createSubcategory(SubcategoryRequest request, Long categoryId) {
        if (request == null || categoryId == null) {
            throw new BadRequestException("Request and category ID cannot be null");
        }

        Category category = categoryRepository.findByIdOrThrow(categoryId);

        if (subcategoryRepository.existsByNameAndCategoryId(request.name(), categoryId)) {
            throw new BadRequestException(String.format(
                    "Subcategory with the name '%s' already exists in the category '%s'.",
                    request.name(), category.getName()
            ));
        }

        Subcategory subcategory = request.toEntity(new Subcategory());

        subcategory.setCategory(category);

        Subcategory savedSubcategory = subcategoryRepository.save(subcategory);

        return subcategoryViewMapper.toView(savedSubcategory);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<SubcategoryGroupedByCategoryView> getSubcategories(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<Subcategory> subcategoryPage = subcategoryRepository.findAll(pageRequest);

        Map<Category, List<Subcategory>> groupedSubcategories = subcategoryPage.getContent().stream()
                .collect(Collectors.groupingBy(Subcategory::getCategory));

        List<SubcategoryGroupedByCategoryView> subcategoriesGroupedByCategory = groupedSubcategories.entrySet().stream()
                .map(entry -> {
                    Category category = entry.getKey();
                    List<SubcategoryView> subcategoryViews = entry.getValue().stream()
                            .sorted(Comparator.comparing(Subcategory::getName))
                            .map(subcategoryViewMapper::toView)
                            .collect(Collectors.toList());
                    return new SubcategoryGroupedByCategoryView(category.getId(), category.getName(), subcategoryViews);
                })
                .collect(Collectors.toList());

        Page<SubcategoryGroupedByCategoryView> resultPage = new PageImpl<>(
                subcategoriesGroupedByCategory, pageRequest, subcategoryPage.getTotalElements()
        );

        return new PaginationResponse<SubcategoryGroupedByCategoryView>().setValuesTo(resultPage);
    }


    @Override
    public SubcategoryView getSubcategoryById(Long id) {
        Subcategory subcategory = subcategoryRepository.findByIdOrThrow(id);

        return subcategoryViewMapper.toView(subcategory);
    }

    @Override
    @Transactional
    public SubcategoryView updateSubcategory(Long id, SubcategoryRequest request) {
        if (id == null || request == null) {
            throw new BadRequestException("ID and request cannot be null.");
        }

        Subcategory existingSubcategory = subcategoryRepository.findByIdOrThrow(id);

        if (!existingSubcategory.getName().equals(request.name()) &&
                subcategoryRepository.existsByNameAndCategoryId(request.name(), existingSubcategory.getCategory().getId())) {
            throw new BadRequestException(String.format(
                    "Subcategory with the name '%s' already exists in the category '%s'.",
                    request.name(), existingSubcategory.getCategory().getName()
            ));
        }

        Subcategory updateSubcategory = request.toEntity(existingSubcategory);

        Subcategory savedSubcategory = subcategoryRepository.save(updateSubcategory);

        return subcategoryViewMapper.toView(savedSubcategory);
    }

    @Override
    @Transactional
    public void deleteSubcategory(Long id) {
        if (id == null) {
            throw new BadRequestException("Subcategory ID cannot be null.");
        }

        Subcategory subcategory = subcategoryRepository.findByIdOrThrow(id);

        subcategoryRepository.delete(subcategory);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<SubcategoryView> getSubcategoriesByCategory(Long categoryId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<SubcategoryView> subcategoryPage = subcategoryRepository.findAllByCategoryId(categoryId, pageRequest)
                .map(subcategoryViewMapper::toView);

        return new PaginationResponse<SubcategoryView>().setValuesTo(subcategoryPage);
    }
}
