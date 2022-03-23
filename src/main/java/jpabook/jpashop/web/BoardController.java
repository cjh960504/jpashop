package jpabook.jpashop.web;

import jpabook.jpashop.domain.Board;
import jpabook.jpashop.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping(value = "/board/list")
    public String list(Model model) {
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "/board/boardList";
    }

    @PostMapping(value = "/board/new")
    public String createBoard(@ModelAttribute("board") Board board, @RequestParam("parentId") Long parentId) {
        boardService.save(board, parentId);
        return "/board/boardList";
    }
}
