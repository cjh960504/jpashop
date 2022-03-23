package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }


    /*public List<Order> findAll(OrderSearch orderSearch){
        // 검색 조건에 맞는 동적 쿼리를 생성하여 주문 엔티티 조회
        // 1. JPQL
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.orderStatus = :orderStatus";
        }

        //회원명 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.username like :username";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("orderStatus", orderSearch.getOrderStatus());
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("username", orderSearch.getMemberName());
        }

        return query.getResultList();

        // JPQL 쿼리를 문자로 생성하기는 번거롭고, 실수로 인한 버그가 충분히 발생할 수 있다.
    }*/

    public List<Order> findAll(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        ArrayList<Predicate> criteria = new ArrayList<>();

        if (orderSearch.getOrderStatus() != null) {
            //주문 상태 비교 Predicate 생성
            Predicate orderStatus = cb.equal(o.get("orderStatus"), orderSearch.getOrderStatus());
            criteria.add(orderStatus);
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            //회원명 검색 Predicate 생성
            Predicate username = cb.like(m.<String>get("username"),
                    "%" + orderSearch.getMemberName() + "%");
            criteria.add(username);
        }

        //Criteria Query 객체에 where 절로 추가
        //Criteria API 의 and() 파라미터 방식인 predicate 배열을 파라미터로 넘김
        //한 개 이상일 수도 있어서 and()를 이용하는 것인가?
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));

        return em.createQuery(cq)
                .setMaxResults(1000)
                .getResultList();
    }

}
