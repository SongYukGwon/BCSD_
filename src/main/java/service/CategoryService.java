package service;

import domain.BoardDTO;
import domain.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import repository.CategoryMapper;
import service.interfaces.ICategoryService;

import java.util.List;

public class CategoryService implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> readCategoryList() {
        return categoryMapper.readCategoryList();
    }

    @Override
    public List<BoardDTO> readBoardInCategory(Long categoryId) {
        return categoryMapper.readBoardInCategory(categoryId);
    }
}
