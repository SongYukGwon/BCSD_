package repository;

import domain.BoardDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMapper {
    //생성
    void makeBoard(BoardDTO board);
    //삭제
    void deleteBoard(BoardDTO board);
    //유저가 삭제될시 같이 삭제될것 추후에 추가

    //게시글 읽기
    BoardDTO readBoard(long boardId);

    //수정
    void updateBoard(BoardDTO board);

    //좋아요 싫어요 포인트변화
    void revisePoint(int point);

    //게시글 제목으로 검색
    void findBoard(String title);

}
