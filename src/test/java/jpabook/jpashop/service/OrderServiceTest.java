package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.exception.NotEnoughStockQuantity;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @PersistenceContext EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //Given
        Member member = createMember();
        Book book = createBook();
        int orderCount = 10;

        //When
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findOne(orderId);

        /* 주문 상태 확인*/
        assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getOrderStatus());
        /* 주문한 상품 종류 수 확인 */
        assertThat(1).isEqualTo(getOrder.getOrderItems().size());
        /* 주문 가격은 가격 * 수량 */
        assertThat(10000 * orderCount).isEqualTo(getOrder.getTotalPrice());
        /* 주문 수량만큼 재고가 줄어야 함 */
        assertThat(490).isEqualTo(book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockQuantity.class)
    public void 상품주문_재고수량초과() throws Exception{
        //G
        Member member = createMember();
        Book book = createBook();
        
        //W
        orderService.order(member.getId(), book.getId(), 501);

        //T
        fail("재고 수량 초과 예외 발생해야 한다.");
    }

    @Test
    public void 주문취소() {
        //G
        Member member = createMember();
        Book book = createBook();
        Long orderId = orderService.order(member.getId(), book.getId(), 10);

        //W
        orderService.cancelOrder(orderId);

        //Then
        Order getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(10).isEqualTo(book.getStockQuantity()); //영속성 컨텍스트에서 관리하므로 book 이용

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("Choi");
        member.setAddress(new Address("서울", "신림", "30304"));
        em.persist(member);
        return member;
    }

    private Book createBook(){
        Book book = new Book();
        book.setName("소설");
        book.setAuthor("지학근");
        book.setPrice(10000);
        book.setIsbn("132141241241241");
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }




}