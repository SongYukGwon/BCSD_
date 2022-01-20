package controller;

import domain.BoardDTO;
import domain.CategoryDTO;
import domain.PointDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.BoardService;
import service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    CategoryService categoryService;

    //등록되어있는 카테고리 검색
    @RequestMapping(value = {"/category"}, method = RequestMethod.GET)
    @ApiOperation(value = "카테고리", notes = "등록되어있는 카테고리를 나열 합니다.")
    public ResponseEntity<List<CategoryDTO>> readCategoryList(){
        return new ResponseEntity<>(categoryService.readCategoryList(),HttpStatus.OK);
    }

    //카테고리에 포함될 게시물 검색
    @RequestMapping(value = {"/{categoryId}"}, method = RequestMethod.GET)
    @ApiOperation(value = "카테고리내 게시물", notes = "카테고리에 등록되어있는 게시물을 나열 합니다.")
    public ResponseEntity<List<BoardDTO>> readCategoryList(@PathVariable("categoryId") Long categoryId){
        return new ResponseEntity<>(categoryService.readBoardInCategory(categoryId),HttpStatus.OK);
    }

    //게시글 목록 읽기
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "게시글 목록 읽기", notes = "게시글 목록을 읽어옵니다.")
    public ResponseEntity<List<BoardDTO>> readBoardList(@RequestParam("page")int page){
        List<BoardDTO> boardList= boardService.boardList(page);
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    //게시글 생성
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 생성", notes = "게시글을 생성합니다.")
    public ResponseEntity<String> makeBoard(@RequestBody BoardDTO board){
        if(boardService.makeBoard(board))
            return new ResponseEntity<>("Success make board", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //게시글 삭제
    @RequestMapping(value = "/{categoryId}/{boardId}", method = RequestMethod.DELETE)
    @ApiOperation(value = " 게시글 삭제", notes = "게시글을 삭제합니다.")
    public ResponseEntity<String> deleteBoard(@PathVariable("boardId") Long boardId) throws Exception {
        if(boardService.deleteBoard(boardId))
            return new ResponseEntity<>("Success delete board", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail delete board", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //게시글 읽기
    @RequestMapping(value = "/{categoryId}/{boardId}", method = RequestMethod.GET)
    @ApiOperation(value = " 게시글 읽기", notes = "게시글을 읽어옵니다")
    public ResponseEntity<BoardDTO> readBoard(@PathVariable("boardId") Long boardId) throws Exception {
        BoardDTO board = boardService.readBoard(boardId);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    //게시글 수정
    @RequestMapping(value = "/{categoryId}/{boardId}", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    public ResponseEntity<String> updateBoard(@RequestBody BoardDTO board, @PathVariable("boardId") Long boardId) throws Exception {
        if(boardService.updateBoard(board, boardId))
            return new ResponseEntity<>("Success update board", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail update", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //게시물 검색
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ApiOperation(value = "게시물 검색", notes = "게시글을 검색합니다")
    public ResponseEntity<List<BoardDTO>> findBoard(@RequestParam("page") int page, @RequestParam("keyword")String keyword, @RequestParam(value = "type", required = false) int type) throws Exception {
        return new ResponseEntity<>(boardService.findBoard(keyword, type, page*10-1), HttpStatus.OK);
    }


    //수정 필요

    //게시글 좋아요
    @RequestMapping(value = "/{categoryId}/{boardId}/like", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 좋아요", notes = "게시글을 좋아요/좋아요취소 합니다.")
    public ResponseEntity<String> upPointBoard(@RequestBody PointDTO point) throws Exception {
        if(boardService.revisePoint(point))
            return new ResponseEntity<>("Success up point", HttpStatus.OK);
        else
            return new ResponseEntity<>("Success up cancel point", HttpStatus.OK);
    }

    //게시글 싫어요
    @RequestMapping(value = "/{categoryId}/{boardId}/unlike", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 싫어요", notes = "게시글을 싫어요/싫어요취소 합니다.")
    public ResponseEntity<String> downPointBoard(@RequestBody PointDTO point) throws Exception {
        if(boardService.revisePoint(point))
            return new ResponseEntity<>("Success down point", HttpStatus.OK);
        else
            return new ResponseEntity<>("Success down cancel Point", HttpStatus.OK);
    }
}
