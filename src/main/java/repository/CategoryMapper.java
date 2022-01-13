package repository;

import domain.BoardDTO;
import domain.CategoryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    List<CategoryDTO> readCategoryList();
    List<BoardDTO> readBoardInCategory(@Param("categoryId") Long categoryId);
}
