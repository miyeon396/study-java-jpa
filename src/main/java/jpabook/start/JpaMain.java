package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // 엔티티 매니저 설정 / 트랜잭션 관리 / 비지니스 로직

        // 엔티티 매니저 팩토리 - 생성 -> 애플리케이션 전체에서 딱 한번만 생성하고 공유해서 사용
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook"); //persistence.xml의 설정 정보 사용해서 엔티티매니저 팩토리 생성
        // 엔티티 매니저 - 생성 -> JPA 기능 대부분은 이 엔티티 매니저가 제공
        EntityManager em = emf.createEntityManager();
        //트랜잭션 - 획득
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); //트랜잭션 시작
            logic(em); //비즈니스 로직
            tx.commit(); //트랜잭션 커밋

        } catch (Exception e) {
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }
        emf.close(); //엔티티 매니저 팩토리 종료
    }

    //비지니스 로직
    private static void logic(EntityManager em) {
        //회원 엔티티를 하나 생성ㅎ나 다음 엔티티 매니저를 통해 데이터베이스에 등록, 수정, 삭제, 조회 함
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        em.persist(member); //등록

        member.setAge(20); //수정

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " +findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        // JPQL -> SQL을 추상화한 JPQL이라는 객체지향 쿼리 언어 / 데이터베이스 테이블을 전혀 알지 못한다.
        // JPQL : 엔티티 객체 대상으로 쿼리. 클래스와 필드 대상으로 쿼리
        // SQL : 데이터베이스 테이블을 대상으로 쿼리
        System.out.println("members.size= = " + members.size());

        em.remove(member); //삭제
    }
}
