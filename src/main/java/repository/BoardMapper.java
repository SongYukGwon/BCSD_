package repository;

import domain.BoardDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMapper {
    //생성
    void makeBoard(BoardDTO board);
    //삭제
    void deleteBoard(long boardId);
    //유저가 삭제될시 같이 삭제될것 추후에 추가
    //게시글 읽기
    BoardDTO readBoard(long boardId);
    //게시글 조회수 증가
    void addView(Long boardId);
    //수정
    void updateBoard(BoardDTO board);
    //좋아요 싫어요 포인트변화
    void revisePoint(int point);
    //게시글 제목으로 검색
    List<BoardDTO> findBoard(String title);
    //게시글 목록
    List<BoardDTO> boardList();

}
