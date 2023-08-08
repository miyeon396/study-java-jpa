package jpabook.jpashop;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        // 엔티티 매니저 설정 / 트랜잭션 관리 / 비지니스 로직

        // 엔티티 매니저 팩토리 - 생성 -> 애플리케이션 전체에서 딱 한번만 생성하고 공유해서 사용
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook"); //persistence.xml의 설정 정보 사용해서 엔티티매니저 팩토리 생성
        // 엔티티 매니저 - 생성 -> JPA 기능 대부분은 이 엔티티 매니저가 제공
        EntityManager em = emf.createEntityManager();
        //트랜잭션 - 획득
        EntityTransaction tx = em.getTransaction();
        tx.begin(); //트랜잭션 시작

        try {

            tx.commit(); //트랜잭션 커밋

        } catch (Exception e) {
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }
        emf.close(); //엔티티 매니저 팩토리 종료
    }

}
