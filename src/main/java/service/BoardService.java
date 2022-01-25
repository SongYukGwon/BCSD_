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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class BoardService implements IBoardService{
    @Autowired
    BoardMapper boardMapper;

    @Autowired
    PointMapper pointMapper;


    public boolean CheckPreviousView(HttpServletRequest request, HttpServletResponse response ,Long boardId){
        Cookie[] cookies = request.getCookies();
        //쿠키가 있는지 없는지 확인하기 위한 변수
        int check = 0;

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("view")){
                check = 1;
                //쿠키에 방문 했는지 안했는지 확인하기 위한 변수
                int view = 0;
                // _를 기준으로 나눔
                String[] viewList = cookie.getValue().split("_");
                for(String tmp : viewList){
                    //일치하는것이 있다면 view = 1
                    if(tmp.equals(boardId.toString())) {
                        view = 1;
                        break;
                    }
                }
                //view가 1이면 이미 본것
                if(view == 1){
                    return false;
                }else{
                    //view가 0이면 안본것이므로 cookie 업데이트
                    cookie.setValue(cookie.getValue()+"_"+boardId.toString());
                    response.addCookie(cookie);
                    return true;
                }
            }
        }
        //쿠키가 없을때
        if(check == 0){
            Cookie cookie1 = new Cookie("view", boardId.toString());
            cookie1.setMaxAge(60*60); //한시간 설정
            response.addCookie(cookie1);

            return true;
        }
        //예외치 못한 에러
        System.out.println("error");
        return true;
    }

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
            if (pointDTO.getIs_deleted() == 1) {
                //기존의 point db 수정
                pointMapper.rePoint(point);
                boardMapper.revisePoint(point);
                return true;
            }
            else if(pointDTO.getPoint() != point.getPoint()){
                //기존의 point db 수정
                pointMapper.rePoint(point);
                //싫어요 했다가 좋아요를 누르면 -1되었던것에서 +1이 되어야한다.
                point.setPoint(point.getPoint()*2);
                boardMapper.revisePoint(point);
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
        throw new Exception("None Exist Erro");
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
    public BoardDTO readBoard(long boardId, HttpServletResponse response) throws Exception{

        //게시글 정보 불러오기
        BoardDTO board = boardMapper.readBoard(boardId);

        //게시글 존재 여부 확인
        if(board == null)
            throw new Exception("Not Exist Board or User");

        //삭제된 게시물인지 확인
        if(board.getDeleted_at() == 1)
            throw new Exception("This post is deleted");

        //게시글 조회수 증가
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if(CheckPreviousView(request, response, boardId))
            boardMapper.addView(boardId);

        return board;
    }

    @Override
    public List<BoardDTO> boardList(int page){
        //페이지
        if(page < 1){
            page = 0;
        }else
            page = page*10;

        return boardMapper.boardList(page);
    }
}
