package com.ktds.board.board.api.service.impl;

import com.ktds.board.board.api.dto.request.CategoryPostReq;
import com.ktds.board.board.api.dto.response.CategoryGetResp;
import com.ktds.board.board.api.service.CategoryService;
import com.ktds.board.board.db.entity.Category;
import com.ktds.board.board.db.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public CategoryGetResp getOne(Long categoryId) {
		var category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new IllegalArgumentException("해당 카텓고리 정보가 없습니다."));

		return CategoryGetResp.builder()
			.categoryId(category.getId())
			.name(category.getCategoryName())
			.build();
	}

	@Override
	public Long saveOne(CategoryPostReq req) {
		var categoryExists = categoryRepository.existsByCategoryName(req.name());

		if(categoryExists) {
			throw new IllegalArgumentException("이미 존재하는 카테고리명입니다.");
		}

		return categoryRepository.save(Category.builder()
				.categoryName(req.name())
				.build()
			).getId();
	}
}
