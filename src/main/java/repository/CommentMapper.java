package repository;

import domain.CommentDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    void makeComment(CommentDTO comment);
    CommentDTO readComment(Long commentId);
    void deleteComment(Long commentId);
    void updateComment(CommentDTO comment) ;
    void revisePointComment(@Param("point") int point, @Param("commentId") long commentId);
    List<CommentDTO> commentList(Long boardId);
}
