package repository;

import domain.BoardDTO;
import domain.CategoryDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    List<CategoryDTO> readCategoryList();
    List<BoardDTO> readBoardInCategory(@Param("categoryId") Long categoryId);
}
