package app.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByPhoneNum(String phoneNum);

    List<Member> findByType(MembershipType type);
}
