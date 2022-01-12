package repository;

import domain.PointDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardPointMapper {
    PointDTO readUsePoint(@Param("user_id")Long userId, @Param("board_id")Long boardId);
    void insertPointUsed(PointDTO pointDTO);
    void cancelPoint(@Param("user_id")Long userId, @Param("board_id")Long boardId);
    void rePoint(PointDTO pointDTO);
}
