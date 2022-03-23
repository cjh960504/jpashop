package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    //주문 검색 시 입력되는 회원명,주문 상태를 받을 DTO
    private String memberName;
    private OrderStatus orderStatus;
}
