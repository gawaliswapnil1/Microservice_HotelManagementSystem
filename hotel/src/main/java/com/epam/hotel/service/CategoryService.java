package com.epam.hotel.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.hotel.Exception.CategoryNotFoundException;
import com.epam.hotel.Exception.NoDataFoundException;
import com.epam.hotel.dto.CategoryDto;
import com.epam.hotel.model.Category;
import com.epam.hotel.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<CategoryDto> getAllcategories() {
		return convertEntityToDto(categoryRepository.findAll());
	}

	public CategoryDto saveCategory(Category category) {
		return convertEntityToDto(categoryRepository.save(category));
	}

	public Optional<CategoryDto> findCategoryById(Integer Id) throws CategoryNotFoundException {
		Optional<Category> categoryFound = categoryRepository.findById(Id);

		if (categoryFound.isEmpty()) {
			throw new CategoryNotFoundException(Id);
		} else {
			return Optional.of(convertEntityToDto(categoryFound.get()));
		}
	}

	public String deleteCategoryById(Integer Id) {

		try {
			categoryRepository.deleteById(Id);
			return "SUCCESS";
		} catch (NoDataFoundException ex) {
			log.debug("Data not found for ID " + ex.getMessage());
		}

		return "FAILED!!";
	}

	public CategoryDto updateCategory(Integer Id, Category category) {

		Category categoryDB = new Category();
		Optional<Category> categoryFound = categoryRepository.findById(Id);
		if (categoryFound.isPresent()) {

			categoryDB = categoryFound.get();

			if (Objects.nonNull(category.getCategoryName()) && !"".equalsIgnoreCase(category.getCategoryName())) {
				categoryDB.setCategoryName(category.getCategoryName());
			}
						
		}
		return convertEntityToDto(categoryRepository.save(categoryDB));
	}
	
	private List<CategoryDto> convertEntityToDto(List<Category> categories) {
		return categories.stream().filter(Objects::nonNull).map(c->convertEntityToDto(c)).collect(Collectors.toList());
		
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
