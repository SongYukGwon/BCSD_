package controller;

import domain.CommentDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.CommentService;

import java.util.List;

@Controller
@RequestMapping(value = "board/{boardId}/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "댓글 리스트", notes = "게시물의 댓글리스트를 가져옵니다.")
    public ResponseEntity<List<CommentDTO>> commentList(@PathVariable("boardId") Long boardId){
        return new ResponseEntity<>(commentService.commentList(boardId), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "댓글 작성", notes = "게시물의 댓글을 작성합니다.")
    public ResponseEntity<String> makeComment(@PathVariable("boardId") Long boardId, @RequestBody CommentDTO comment){
        comment.setBoard_id(boardId);
        if(commentService.makeComment(comment))
            return new ResponseEntity<>("Success create Comment", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail create Comment", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.POST)
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다")
    public ResponseEntity<String> updateComment(@PathVariable("commentId")Long commentId, @RequestBody CommentDTO comment){
        if(commentService.updateComment(comment, commentId))
            return new ResponseEntity<>("Success update Comment", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail update Comment", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId")Long commentId) throws Exception {
        if (commentService.deleteComment(commentId))
            return new ResponseEntity<>("Success delete Comment", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail delete Comment", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{commentId}/like", method = RequestMethod.POST)
    @ApiOperation(value = "댓글 좋아요", notes = "댓글을 좋아요 합니다.")
    public ResponseEntity<String> upPoint(@PathVariable("commentId")Long commentId) throws Exception {
        if (commentService.revisePointComment(commentId, 1))
            return new ResponseEntity<>("Success like Comment", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail like Comment", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{commentId}/unlike", method = RequestMethod.POST)
    @ApiOperation(value = "댓글 싫어요", notes = "댓글을 싫어요 합니다.")
    public ResponseEntity<String> downPoint(@PathVariable("commentId")Long commentId) throws Exception {
        if (commentService.revisePointComment(commentId, -1))
            return new ResponseEntity<>("Success like Comment", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail like Comment", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = {"/{commentId}/like/cancel","/{commentId}/unlike/cancel"}, method = RequestMethod.POST)
    @ApiOperation(value = "댓글 좋/싫 취소", notes = "댓글 좋/싫 취소합니다.")
    public ResponseEntity<String> cancelCommentPoint(@PathVariable("commentId")Long commentId) throws Exception {
        if(commentService.cancelCommentPoint(commentId))
            return new ResponseEntity<>("Success cancel point", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail cancel Point", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{commentId}/reply", method = RequestMethod.POST)
    @ApiOperation(value = "대댓글", notes = "대댓글을 생성합니다.")
    public ResponseEntity<String> replyComment(@PathVariable("commentId")Long commentId, @RequestBody CommentDTO comment, @PathVariable("boardId") Long boardId) throws Exception {
        if (commentService.replyComment(boardId, commentId, comment))
            return new ResponseEntity<>("Success  reply Comment", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail reply Comment", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
