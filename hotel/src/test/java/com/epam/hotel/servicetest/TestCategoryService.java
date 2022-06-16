package com.epam.hotel.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.hotel.Exception.CategoryNotFoundException;
import com.epam.hotel.dto.CategoryDto;
import com.epam.hotel.model.Category;
import com.epam.hotel.repository.CategoryRepository;
import com.epam.hotel.service.CategoryService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestCategoryService {

	@Autowired
	private CategoryService categoryService;

	@MockBean
	private CategoryRepository categoryRepository;

	Category categoryInput = new Category(10000, "5 Star",null);

	@BeforeEach
	void setUp() {
		when(categoryRepository.save(any(Category.class))).thenReturn(categoryInput);
	}

	@AfterEach
	void tearDown() {
		categoryRepository.deleteAll();
	}

	@Test
	void createCategory() {

		when(categoryRepository.save(any(Category.class))).thenReturn(categoryInput);

		CategoryDto savedCategory = categoryService.saveCategory(categoryInput);
		assertEquals(categoryInput.getCategoryName(), savedCategory.getCategoryName());
	}

	void getAllCategorys() {
		List<CategoryDto> categoryList = new ArrayList<>();

		categoryService.saveCategory(new Category(10000, "5 Star",null));
		categoryService.saveCategory(new Category(10001, "3 Star",null));
		categoryService.saveCategory(new Category(10000, "2 Star",null));

		List<CategoryDto> categoryOutput = categoryService.getAllcategories();
		assertEquals(3, categoryOutput.size());
	}

	@Test
	void findCategoryById() throws CategoryNotFoundException {

		CategoryDto savedCategory = categoryService.saveCategory(categoryInput);

		when(categoryRepository.findById(savedCategory.getCategoryId())).thenReturn(Optional.of(convertDtoToEntity(savedCategory)));
		Optional<CategoryDto> categoryFound = categoryService.findCategoryById(savedCategory.getCategoryId());
		assertEquals(categoryInput.getCategoryName(), categoryFound.get().getCategoryName());
	}

	@Test
	void deleteCategoryById() {
		CategoryDto savedCategory = categoryService.saveCategory(categoryInput);

		String isSuccessful = categoryService.deleteCategoryById(savedCategory.getCategoryId());
		assertEquals("SUCCESS", isSuccessful);
	}

	@Test
	void updateCategory() {
		CategoryDto savedCategory =categoryService.saveCategory(categoryInput);

		when(categoryRepository.save(any(Category.class))).thenReturn(convertDtoToEntity(savedCategory));
		savedCategory.setCategoryName("new details");
		CategoryDto updateCategory = categoryService.updateCategory(savedCategory.getCategoryId(),convertDtoToEntity(savedCategory));

		assertEquals(categoryInput.getCategoryName(), updateCategory.getCategoryName());

	}

	private CategoryDto convertEntityToDto(Category category) {
		CategoryDto categoryDto=new CategoryDto();
		categoryDto.setCategoryId(category.getCategoryId());
		categoryDto.setCategoryName(category.getCategoryName());
		return categoryDto;
	}
	private Category convertDtoToEntity(CategoryDto categoryDto) {
		Category category=new Category();
		category.setCategoryId(categoryDto.getCategoryId());
		category.setCategoryName(categoryDto.getCategoryName());
		return category;
	}
}
