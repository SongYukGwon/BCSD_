package service;

import domain.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.CommentMapper;
import service.interfaces.ICommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    CommentMapper commentMapper;

    public Long CheckUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long CUserId = (Long)request.getSession().getAttribute("userId");
        return CUserId;
    }

    @Override
    public boolean makeComment(CommentDTO comment){
        Long userId = CheckUserId();
        if(userId == null)
            return false;

        comment.setUser_Id(userId);
        commentMapper.makeComment(comment);
        return true;
    }

    @Override
    public boolean deleteComment(Long commentId) throws Exception {
        Long userId = CheckUserId();

        if(userId == null)
            throw new Exception("not login");

        CommentDTO comment = commentMapper.readComment(commentId);

        if(userId != comment.getUser_Id())
            throw new Exception("not match user");

        commentMapper.deleteComment(commentId);
        return true;
    }

    @Override
    public boolean revisePointComment(long commentId, int point) throws Exception {
        //사용자가 로그인 되어 있는지 확인
        Long userId = CheckUserId();
        if(userId == null)
            throw new Exception("not login");

        //댓글이 이미 삭제된 댓글인지 확인 필요
        CommentDTO comment = commentMapper.readComment(commentId);
        if(comment.getIs_deleted() == 1)
            throw new Exception("deleted comment error");

        //사용자가 이전에 추천했는지 확인 필요



        commentMapper.revisePointComment(point, commentId);
        return true;
    }

    @Override
    public boolean updateComment(CommentDTO upComment, long commentID) {
        Long userId = CheckUserId();
        CommentDTO comment = commentMapper.readComment(commentID);

        if(userId != comment.getUser_Id())
            return false;

        comment.setContent(upComment.getContent());

        commentMapper.updateComment(comment);
        return true;
    }

    @Override
    public List<CommentDTO> commentList(Long boardId) {

        List<CommentDTO> comments = commentMapper.commentList(boardId);
        return comments;
    }

}
