package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    /* 값 타입은 불변하도록 설계
    *  - 여러 엔티티에서 공유하면 한 쪽에서 값을 바꾸면 다른 한쪽도 바뀔 수 있기 때문이다. */
    private String city;
    private String street;
    private String zipcode;

    /* JPA 스펙상 엔티티나 임베디드 타입은 자바 기본 생성자를 protected 로 설정해야 한다. - 더 안전하기 때문
    (리플렉션 같은 기술을 사용할 수 있게 지원해야 하기 때문이다.)*/
    protected Address() {}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
