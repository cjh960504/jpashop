package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String content;

    private String writer;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Board parent;

    @OneToMany(mappedBy = "parent")
    private List<Board> child = new ArrayList<>();

    private int groupOrd; //원글(답글포함)에 대한 순서

    private int groupLayer; //답글 계층

}
