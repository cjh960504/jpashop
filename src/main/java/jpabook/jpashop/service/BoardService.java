package jpabook.jpashop.service;

import jpabook.jpashop.domain.Board;
import jpabook.jpashop.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(Board board, Long parentId) {
        if (parentId != null) {
            Board parent = boardRepository.findOne(parentId);
            board.setParent(parent);
            board.setGroupOrd(parent.getGroupOrd() + 1);
            board.setGroupLayer(parent.getGroupLayer() + 1);
        }

        boardRepository.save(board);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findId(Long id) {
        return boardRepository.findOne(id);
    }
}
