package service.interfaces;

import domain.CommentDTO;
import domain.PointDTO;

import java.util.List;

public interface ICommentService {
    boolean makeComment(CommentDTO comment) throws Exception;
    boolean deleteComment(Long commentId) throws Exception;
    boolean revisePointComment(PointDTO point) throws Exception;
    boolean updateComment(CommentDTO comment, long commentID);
    List<CommentDTO> commentList(Long boardId);
    boolean replyComment(Long boardId, Long commentId, CommentDTO comment) throws Exception;
}
