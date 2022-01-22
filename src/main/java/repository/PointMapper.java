package repository;

import domain.PointDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointMapper {
    PointDTO readUsePoint(@Param("user_id")Long userId, @Param("board_id")Long boardId);
    PointDTO readUseCommentPoint(@Param("user_id")Long userId, @Param("comment_id")Long commentId);
    void insertPointUsed(PointDTO pointDTO);
    void insertCommentPointUsed(PointDTO pointDTO);
    void cancelPoint(PointDTO pointDTO);
    void cancelCommentPoint(PointDTO pointDTO);
    void rePoint(PointDTO pointDTO);
    void reCommentPoint(PointDTO pointDTO);
}
