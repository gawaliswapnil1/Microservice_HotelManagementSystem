package com.epam.hotel.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.hotel.controller.CategoryController;
import com.epam.hotel.dto.CategoryDto;
import com.epam.hotel.model.Category;
import com.epam.hotel.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
public class TestCategoryController {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	CategoryDto categoryDto = new CategoryDto(1, "5 Star");

	@Test
	void getAllCategorys() throws Exception {

		List<CategoryDto> categoryList = new ArrayList<>();
		categoryList.add(new CategoryDto(1, "3 Star"));
		categoryList.add(new CategoryDto(2, "5 Star"));
		when(categoryService.getAllcategories()).thenReturn(categoryList);

		mockMvc.perform(MockMvcRequestBuilders.get("/catogories/all")).andExpect(status().isOk());
	}

	@Test
	void fingCategoryById() throws Exception {

		when(categoryService.findCategoryById(categoryDto.getCategoryId())).thenReturn(Optional.of(categoryDto));

		mockMvc.perform(MockMvcRequestBuilders
				.get("/catogories/findCategoryById/" + Integer.toString(categoryDto.getCategoryId())))
				.andExpect(status().isOk());

	}

	@Test
	void createCategory() throws Exception {

		when(categoryService.saveCategory(any(Category.class))).thenReturn(categoryDto);
		ObjectMapper objectMapper = new ObjectMapper();
		String categoryJSON = objectMapper.writeValueAsString(categoryDto);

		ResultActions result = mockMvc
				.perform(post("/catogories/create").contentType(MediaType.APPLICATION_JSON).content(categoryJSON));

		result.andExpect(status().isCreated());
	}

	@Test
	void deleteCategory() throws Exception {

		when(categoryService.deleteCategoryById(categoryDto.getCategoryId())).thenReturn("SUCCESS");
		mockMvc.perform(delete("/catogories/deleteCategoryById/" + Integer.toString(categoryDto.getCategoryId())))
				.andExpect(status().isGone());
	}

	@Test
	void updateCategory() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String categoryJSON = objectMapper.writeValueAsString(categoryDto);

		when(categoryService.updateCategory(categoryDto.getCategoryId(),convertDtoToEntity(categoryDto))).thenReturn(categoryDto);

		mockMvc.perform(put("/catogories/updateCategory/" + categoryDto.getCategoryId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(categoryJSON))
				.andExpect(status().isOk());
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
