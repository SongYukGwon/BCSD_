package controller;

import domain.BoardDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.BoardService;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "게시글 목록 읽기", notes = "게시글 목록을 읽어옵니다.")
    public ResponseEntity<List<BoardDTO>> readBoardList(){
        List<BoardDTO> boardList= boardService.boardList();
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{categoryId}/create", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 생성", notes = "게시글을 생성합니다.")
    public ResponseEntity<String> makeBoard(@RequestBody BoardDTO board, @PathVariable("categoryId") Long categoryId){
        board.setCategory_id(categoryId);
        if(boardService.makeBoard(board))
            return new ResponseEntity<>("Success make board", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{boardId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBoard(@PathVariable("boardId") Long boardId) throws Exception {
        if(boardService.deleteBoard(boardId))
            return new ResponseEntity<>("Success delete board", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail delete board", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{boardId}", method = RequestMethod.GET)
    @ApiOperation(value = " 게시글 읽기", notes = "게시글을 읽어옵니다")
    public ResponseEntity<BoardDTO> readBoard(@PathVariable("boardId") Long boardId) throws Exception {
        BoardDTO board = boardService.readBoard(boardId);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }
}
