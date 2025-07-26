package app.members;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        Member member = new Member(
                "John", "Doe", "123 Main St", "john@example.com", "555-1234",
                MembershipType.INDIVIDUAL, startDate, 12.0
        );

        assertNull(member.getMemberId()); // Not set yet
        assertEquals("John", member.getFirstName());
        assertEquals("Doe", member.getLastName());
        assertEquals("123 Main St", member.getAddress());
        assertEquals("john@example.com", member.getEmail());
        assertEquals("555-1234", member.getPhoneNum());
        assertEquals(MembershipType.INDIVIDUAL, member.getType());
        assertEquals(startDate, member.getStartOfMembership());
        assertEquals(12.0, member.getDuration());
    }

    @Test
    void testSetters() {
        Member member = new Member();

        member.setMemberId(1L);
        member.setFirstName("Jane");
        member.setLastName("Smith");
        member.setAddress("456 Elm St");
        member.setEmail("jane@example.com");
        member.setPhoneNum("555-5678");
        member.setType(MembershipType.FAMILY);
        member.setStartOfMembership(LocalDate.of(2024, 5, 10));
        member.setDuration(6.5);

        assertEquals(1L, member.getMemberId());
        assertEquals("Jane", member.getFirstName());
        assertEquals("Smith", member.getLastName());
        assertEquals("456 Elm St", member.getAddress());
        assertEquals("jane@example.com", member.getEmail());
        assertEquals("555-5678", member.getPhoneNum());
        assertEquals(MembershipType.FAMILY, member.getType());
        assertEquals(LocalDate.of(2024, 5, 10), member.getStartOfMembership());
        assertEquals(6.5, member.getDuration());
    }

    @Test
    void testToStringIncludesKeyFields() {
        Member member = new Member(
                "Alice", "Walker", "789 Oak St", "alice@example.com", "555-9012",
                MembershipType.SENIOR, LocalDate.of(2022, 9, 15), 24.0
        );
        member.setMemberId(42L);

        String result = member.toString();

        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("Walker"));
        assertTrue(result.contains("789 Oak St"));
        assertTrue(result.contains("alice@example.com"));
        assertTrue(result.contains("555-9012"));
        assertTrue(result.contains("SENIOR"));
        assertTrue(result.contains("2022-09-15"));
        assertTrue(result.contains("24.0"));
        assertTrue(result.contains("42"));
    }
}
