package jpabook.jpashop.service;

import jpabook.jpashop.domain.Board;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {
    @Autowired
    BoardService boardService;

    @Test
    public void 답글저장() {
        //G
        Board board = new Board();
        board.setContent("Test");
        board.setWriter("tester");

        boardService.save(board, null);


        Board child = new Board();
        child.setContent("test");
        child.setWriter("child");

        //W
        boardService.save(child, board.getId());
        Board findBoard = boardService.findId(child.getId());


        //T
        assertThat(findBoard.getGroupOrd()).isEqualTo(board.getGroupOrd() + 1);
        assertThat(findBoard.getGroupLayer()).isEqualTo(board.getGroupLayer() + 1);
    }
}