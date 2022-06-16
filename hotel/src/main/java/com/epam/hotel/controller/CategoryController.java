package com.epam.hotel.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.hotel.Exception.CategoryNotFoundException;
import com.epam.hotel.dto.CategoryDto;
import com.epam.hotel.model.Category;
import com.epam.hotel.service.CategoryService;

@RestController
@RequestMapping("/catogories")
public class CategoryController {

	
	
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getAllGust() {
		return ResponseEntity.ok(categoryService.getAllcategories());
	}
	
	@PostMapping("/create")
	ResponseEntity<CategoryDto> create(@RequestBody Category category) {
	    return new ResponseEntity(categoryService.saveCategory(category), HttpStatus.CREATED);
	}
	
	@GetMapping("/findCategoryById/{Id}")
	ResponseEntity<Optional<CategoryDto>> findCategoryById(@PathVariable Integer Id) throws CategoryNotFoundException{
		return ResponseEntity.ok(categoryService.findCategoryById(Id));
	}

	@DeleteMapping("/deleteCategoryById/{Id}")
	ResponseEntity<Optional<CategoryDto>> deleteCategoryById(@PathVariable Integer Id){
		return new ResponseEntity(categoryService.deleteCategoryById(Id), HttpStatus.GONE);
	}
	
	@PutMapping("/updateCategory/{Id}")
	ResponseEntity<CategoryDto> updateCategory(@PathVariable Integer Id,@RequestBody Category category){
		return ResponseEntity.ok(categoryService.updateCategory(Id,category));
	}
}
