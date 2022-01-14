package service;

import domain.BoardDTO;
import domain.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CategoryMapper;
import service.interfaces.ICategoryService;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> readCategoryList() {
        List<CategoryDTO> abc = categoryMapper.readCategoryList();
        return abc;
    }

    @Override
    public List<BoardDTO> readBoardInCategory(Long categoryId) {
        return categoryMapper.readBoardInCategory(categoryId);
    }
}
