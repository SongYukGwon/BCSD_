package controller;

import domain.BoardDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.BoardService;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    //게시글 목록 읽기
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "게시글 목록 읽기", notes = "게시글 목록을 읽어옵니다.")
    public ResponseEntity<List<BoardDTO>> readBoardList(){
        List<BoardDTO> boardList= boardService.boardList();
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }
    
    //게시글 생성
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 생성", notes = "게시글을 생성합니다.")
    public ResponseEntity<String> makeBoard(@RequestBody BoardDTO board){
        if(boardService.makeBoard(board))
            return new ResponseEntity<>("Success make board", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //게시글 삭제
    @RequestMapping(value = "/{boardId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBoard(@PathVariable("boardId") Long boardId) throws Exception {
        if(boardService.deleteBoard(boardId))
            return new ResponseEntity<>("Success delete board", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail delete board", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //게시글 읽기
    @RequestMapping(value = "/{boardId}", method = RequestMethod.GET)
    @ApiOperation(value = " 게시글 읽기", notes = "게시글을 읽어옵니다")
    public ResponseEntity<BoardDTO> readBoard(@PathVariable("boardId") Long boardId) throws Exception {
        BoardDTO board = boardService.readBoard(boardId);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    //게시글 수정
    @RequestMapping(value = "/{boardId}", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    public ResponseEntity<String> updateBoard(@RequestBody BoardDTO board, @PathVariable("boardId") Long boardId) throws Exception {
        if(boardService.updateBoard(board, boardId))
            return new ResponseEntity<>("Success update board", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail update", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //게시글 좋아요
    @RequestMapping(value = "/{boardId}/like", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 좋아요", notes = "게시글을 좋아요합니다.")
    public ResponseEntity<String> upPointBoard(@PathVariable("boardId") Long boardId){
        if(boardService.revisePoint(1, boardId))
            return new ResponseEntity<>("Success up point", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail up Point", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //게시글 싫어요
    @RequestMapping(value = "/{boardId}/unlike", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 싫어요", notes = "게시글을 싫어요 합니다.")
    public ResponseEntity<String> downPointBoard(@PathVariable("boardId") Long boardId){
        if(boardService.revisePoint(-1, boardId))
            return new ResponseEntity<>("Success up point", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail up Point", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    


}
