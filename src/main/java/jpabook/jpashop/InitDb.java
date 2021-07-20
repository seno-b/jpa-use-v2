package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "111-111", "1", "서울");
            em.persist(member);

            Book book = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book);

            Book book1 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book1);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book1, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB", "111-111", "1", "진주");
            em.persist(member);

            Book book = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book);

            Book book1 = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(book1);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book1, 40000, 4);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private Member createMember(String name, String zipcode, String street, String city) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }


}
