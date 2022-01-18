package service;

import domain.CommentDTO;
import domain.PointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.CommentMapper;
import repository.PointMapper;
import service.interfaces.ICommentService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.events.Comment;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    PointMapper pointMapper;

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
        PointDTO pointDTO = pointMapper.readUseCommentPoint(userId, commentId);

        //새로운 PointDTO 생성
        PointDTO newPoint = new PointDTO();
        newPoint.setPoint(point);
        newPoint.setCommentId(commentId);
        newPoint.setUser_id(userId);

        if(pointDTO == null) {
            //이전에 좋/싫 기록이 없다면
            commentMapper.revisePointComment(point, commentId);
            pointMapper.insertCommentPointUsed(newPoint);
            return true;
        }
        else {
            //좋/싫 기록이 있지만 취소했던 경우
            if (pointDTO.getIs_deleted() == 1) {
                pointMapper.reCommentPoint(newPoint);
                commentMapper.revisePointComment(point, commentId);
                return true;
            }
            //그외
            else {
                return false;
            }
        }
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

    @Override
    public boolean cancelCommentPoint(Long commentId) throws Exception {
        Long userId = CheckUserId();

        if(userId == null)
            throw new Exception("로그인이 되어있는 상태여야 합니다.");

        //기존의 포인트db 확인
        PointDTO pointDTO = pointMapper.readUseCommentPoint(userId, commentId);

        if(pointDTO == null)
            throw new Exception("취소할 like/unlike 기록이 없습니다.");

        if(pointDTO.getIs_deleted() == 1)
            throw new Exception("이미 취소되어있는 like/unlike입니다.");

        pointMapper.cancelCommentPoint(userId, commentId);
        return true;
    }

    @Override
    public boolean replyComment(Long boardId, Long commentId, CommentDTO comment) throws Exception {
        CommentDTO chComment = commentMapper.readComment(commentId);

        if(chComment == null)
            throw new Exception("not exist comment");
        else if(chComment.getBoard_id() != boardId)
            throw new Exception("not match boardId");

        Long userId = CheckUserId();
        if(userId == null)
            throw new Exception("not login");

        comment.setUser_Id(userId);
        comment.setBoard_id(boardId);
        comment.setParent_comment_id(commentId);

        commentMapper.replyComment(comment);
        return true;
    }

}
