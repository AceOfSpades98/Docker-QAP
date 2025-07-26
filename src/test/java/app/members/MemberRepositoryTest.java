package app.members;

import app.members.Member;
import app.members.MemberRepository;
import app.members.MembershipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member1;

    @BeforeEach
    void setUp() {
        member1 = new Member("John", "Doe", "123 Main St", "john@example.com", "555-1234",
                MembershipType.INDIVIDUAL, LocalDate.of(2023, 1, 1), 12.0);

        Member member2 = new Member("Jane", "Smith", "456 Elm St", "jane@example.com", "555-5678",
                MembershipType.FAMILY, LocalDate.of(2024, 5, 10), 6.5);

        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @Test
    void testFindByFirstNameAndLastName() {
        Optional<Member> found = memberRepository.findByFirstNameAndLastName("John", "Doe");
        assertTrue(found.isPresent());
        assertEquals(member1.getEmail(), found.get().getEmail());

        Optional<Member> notFound = memberRepository.findByFirstNameAndLastName("Non", "Existent");
        assertTrue(notFound.isEmpty());
    }

    @Test
    void testFindByEmail() {
        Optional<Member> found = memberRepository.findByEmail("jane@example.com");
        assertTrue(found.isPresent());
        assertEquals("Jane", found.get().getFirstName());

        Optional<Member> notFound = memberRepository.findByEmail("nope@example.com");
        assertTrue(notFound.isEmpty());
    }

    @Test
    void testFindByPhoneNumber() {
        Optional<Member> found = memberRepository.findByPhoneNum("555-1234");
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getFirstName());

        Optional<Member> notFound = memberRepository.findByPhoneNum("000-0000");
        assertTrue(notFound.isEmpty());
    }

    @Test
    void testFindByType() {
        List<Member> familyMembers = memberRepository.findByType(MembershipType.FAMILY);
        assertEquals(1, familyMembers.size());
        assertEquals("Jane", familyMembers.getFirst().getFirstName());

        List<Member> seniorMembers = memberRepository.findByType(MembershipType.SENIOR);
        assertTrue(seniorMembers.isEmpty());
    }
}