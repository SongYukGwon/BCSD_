package service;

import domain.BoardDTO;
import domain.CommentDTO;
import domain.PointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.BoardMapper;
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
    BoardMapper boardMapper;

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
    public boolean revisePointComment(PointDTO point) throws Exception {
        //사용자가 로그인 되어 있는지 확인
        Long userId = CheckUserId();
        if(userId == null)
            throw new Exception("not login");
        //사용자 일치 여부 확인
        else if(point.getUser_id() != userId)
            throw new Exception("not match user");

        //point 이상 유무 확인
        if(point.getPoint() != 1 && point.getPoint()!=-1)
            throw new Exception("Wrong Point");

        //게시물, 댓글 정보가 있는지 확인
        if(point.getBoard_id() == null || point.getCommentId() == null)
            throw new Exception("not exist boardId or comment_id");

        //게시글 유무 확인
        BoardDTO board = boardMapper.readBoard(point.getBoard_id());
        if(board == null)
            throw new Exception("Wrong Board Id");
        else if(board.getDeleted_at() == 1)
            throw new Exception("Deleted Board");

        //댓글이 이미 삭제된 댓글인지 확인 필요
        CommentDTO comment = commentMapper.readComment(point.getCommentId());
        if(comment.getIs_deleted() == 1)
            throw new Exception("deleted comment");

        //사용자가 이전에 추천했는지 확인 필요
        PointDTO pointDTO = pointMapper.readUseCommentPoint(userId, point.getCommentId());

        if(pointDTO == null) {
            //이전에 좋/싫 기록이 없다면 댓글 포인트 증가 및 point db 생성
            commentMapper.revisePointComment(point);
            pointMapper.insertCommentPointUsed(point);
            return true;
        }
        else {
            //좋/싫 기록이 있지만 취소했던 경우
            //좋/싫 기록이 있지만 이전과는 반대된 point를 입력하는 경우
            if (pointDTO.getIs_deleted() == 1) {
                //기존의 point db 수정
                pointMapper.reCommentPoint(point);
                commentMapper.revisePointComment(point);
                return true;
            }
            else if(pointDTO.getPoint() != point.getPoint()) {
                //싫어요 했다가 좋아요를 누르면 -1되었던것에서 +1이 되어야한다.
                pointMapper.reCommentPoint(point);
                point.setPoint(point.getPoint() * 2);
                commentMapper.revisePointComment(point);
                return true;
            }
            else{
                //좋/싫 기록이 있고 같은 똑같은 입력을 했을 경우 취소
                //포인트를 원래 수치로 돌리고 point db delete true 처리
                point.setPoint(point.getPoint()*-1);
                commentMapper.revisePointComment(point);
                pointMapper.cancelCommentPoint(point);
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
