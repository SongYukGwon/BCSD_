package service;

import domain.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.BoardMapper;
import service.interfaces.IBoardService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BoardService implements IBoardService{
    @Autowired
    BoardMapper boardMapper;


    public Long CheckUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long CUserId = (Long)request.getSession().getAttribute("userId");
        return CUserId;
    }

    @Override
    public boolean makeBoard(BoardDTO board){
        Long userId = CheckUserId();
        if(userId==null)
            return false;
        else{
            board.setUser_Id(userId);
            boardMapper.makeBoard(board);
            return true;
        }
    }

    @Override
    public boolean deleteBoard(long boardId) throws Exception {
        //boardId로 해당 board를 찾아오기
        BoardDTO board = boardMapper.readBoard(boardId);

        //현재로그인하고있는 사용자의 정보를 받아오기
        Long userId = CheckUserId();
        if(userId == null)
            throw new Exception("Have to Login");

        //사용자의 정보와 board의 정보를 비교
        if(board.getUser_Id() != userId)
            throw new Exception("Have to Login");

        //일치한다면 게시글 삭제
        boardMapper.deleteBoard(boardId);
        return true;
    }

    @Override
    public boolean updateBoard(BoardDTO UpBoard, long boardID) throws Exception{
        Long userId = CheckUserId();
        BoardDTO board = boardMapper.readBoard(boardID);

        //오류처리
        if(board.getDeleted_at() == 1)
            return false;
//            throw new Exception("This board is deleted");
        if(board.getUser_Id() != userId)
            return false;
//            throw new Exception("Not match UserId");

        //수정
        board.setTitle(UpBoard.getTitle());
        board.setContent(UpBoard.getContent());
        board.setId(boardID);

        //수정한 내용 업데이트
        boardMapper.updateBoard(board);
        return true;
    }

    @Override
    public boolean revisePoint(int point, Long boardId){
        //계정당 한번만 누를 수 있도록 구현

        //이상없으면 결과 반영
        boardMapper.revisePoint(point, boardId);
        return true;
    }

    @Override
    public List<BoardDTO> findBoard(String title){

        //해당 제목을 가진 게시물 검색
        List<BoardDTO> boards = boardMapper.findBoard(title);

        return boards;

    }

    @Override
    public BoardDTO readBoard(long boardId) throws Exception{
        //게시글 정보 불러오기
        BoardDTO board = boardMapper.readBoard(boardId);

        //삭제된 게시물인지 확인
        if(board.getDeleted_at() == 1)
            throw new Exception("This post is deleted");

        //게시글 조회수 증가
        boardMapper.addView(boardId);

        return board;
    }

    public List<BoardDTO> boardList(){
        List<BoardDTO> boardList = boardMapper.boardList();
        return boardList;
    }
}
