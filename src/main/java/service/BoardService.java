package service;

import domain.BoardDTO;
import domain.PointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.BoardMapper;
import repository.PointMapper;
import service.interfaces.IBoardService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BoardService implements IBoardService{
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
    public boolean revisePoint(PointDTO point) throws Exception {
        //사용자 확인
        Long userId = CheckUserId();

        if(userId == null)
            throw new Exception("not login");
        else if(point.getUser_id() != userId)
            throw new Exception("not match user");

        //point 이상 유무 확인
        if(point.getPoint() != 1 && point.getPoint()!=-1)
            throw new Exception("Wrong Point");

        //게시물 정보가 있는지 확인
        if(point.getBoard_id() == null)
            throw new Exception("not exist board info");

        //사용자가 이전에 해당게시물에 포인트를 사용하였는지 확인
        PointDTO pointDTO = pointMapper.readUsePoint(userId, point.getBoard_id());

        if(pointDTO == null) {
            //이전에 좋/싫 기록이 없다면
            boardMapper.revisePoint(point);
            pointMapper.insertPointUsed(point);
            return true;
        }
        else {
            //좋/싫 기록이 있지만 취소했던 경우
            //좋/싫 기록이 있지만 이전과는 반대된 point를 입력하는 경우
            if (pointDTO.getIs_deleted() == 1 || pointDTO.getPoint() != point.getPoint()) {
                boardMapper.revisePoint(point);
                pointMapper.rePoint(point);
                return true;
            }
            //좋/싫 기록이 있고 같은 똑같은 입력을 했을 경우 취소
            else{
                //포인트를 원래 수치로 돌리고 point db delete true 처리
                point.setPoint(point.getPoint()*-1);
                boardMapper.revisePoint(point);
                pointMapper.cancelPoint(point);
                return false;
            }
        }
    }

    @Override
    public List<BoardDTO> findBoard(String sen, int type, int page) throws Exception {

        //페이지
        if(page < 1){
            page = 0;
        }else
            page = page*10;

        //해당 제목을 가진 게시물 검색
        if(type==1){
            List<BoardDTO> boards = boardMapper.findBoardInTitle(sen,page);
            return boards;
        }
        else if(type == 2) {
            List<BoardDTO> boards = boardMapper.findBoardInContent(sen,page);
            return boards;
        }else if(type == 3){
            List<BoardDTO> boards = boardMapper.findBoardInUser(sen,page);
            return boards;
        }else{
            throw new Exception("not match type");
        }
    }

    @Override
    public BoardDTO readBoard(long boardId) throws Exception{

        //게시글 정보 불러오기
        BoardDTO board = boardMapper.readBoard(boardId);

        //게시글 존재 여부 확인
        if(board == null)
            throw new Exception("Not Exist Board or User");

        //삭제된 게시물인지 확인
        if(board.getDeleted_at() == 1)
            throw new Exception("This post is deleted");

        //게시글 조회수 증가
        boardMapper.addView(boardId);

        return board;
    }

    public List<BoardDTO> boardList(int page){
        //페이지
        if(page < 1){
            page = 0;
        }else
            page = page*10;

        return boardMapper.boardList(page);
    }
}
