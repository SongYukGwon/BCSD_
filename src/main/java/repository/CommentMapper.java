package repository;

import domain.CommentDTO;
import domain.PointDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    void makeComment(CommentDTO comment);
    CommentDTO readComment(Long commentId);
    void deleteComment(Long commentId);
    void updateComment(CommentDTO comment) ;
    void revisePointComment(PointDTO point);
    List<CommentDTO> commentList(Long boardId);
    void replyComment(CommentDTO comment);
}
