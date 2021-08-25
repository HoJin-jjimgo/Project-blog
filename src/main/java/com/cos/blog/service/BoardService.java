package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(Board board, User user){
        board.setCount(0);
        board.setUser(user);


        boardRepository.save(board);
    }
    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id){
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 해당 아이디가 없습니다.");
                });
    }

    @Transactional
    public void 삭제(int id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 수정(int id, Board board){
        Board board1 = boardRepository.findById(id).orElseThrow(
                ()-> {
                    return new IllegalArgumentException("글 찾기 실패 : 해당 아이디가 없습니다.");
                }); //영속화 완료
        board1.setTitle(board.getTitle());
        board1.setContent(board.getContent());

    }

}
