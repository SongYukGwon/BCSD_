package service.interfaces;

import domain.CommentDTO;

import java.util.List;

public interface ICommentService {
    boolean makeComment(CommentDTO comment) throws Exception;
    boolean deleteComment(Long commentId) throws Exception;
    boolean revisePointComment(long commentId, int point) throws Exception;
    boolean updateComment(CommentDTO comment, long commentID);
    List<CommentDTO> commentList(Long boardId);
    boolean cancelCommentPoint(Long commentId) throws Exception;
}
