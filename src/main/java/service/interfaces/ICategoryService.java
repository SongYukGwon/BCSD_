package service.interfaces;

import domain.BoardDTO;
import domain.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> readCategoryList();
    List<BoardDTO> readBoardInCategory(Long CategoryID);
}
