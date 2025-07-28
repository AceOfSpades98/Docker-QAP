package app.tournaments;

import app.members.Member;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentTest {

    @Test
    void testConstructorAndGetters() {
        String name = "Test Tournament";
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 10);
        String location = "New York";
        double entryFee = 100.0;
        double cashPrize = 1000.0;
        List<Member> participants = new ArrayList<>();

        Tournament tournament = new Tournament(name, startDate, endDate, location, entryFee, cashPrize, participants);

        assertEquals(name, tournament.getName());
        assertEquals(startDate, tournament.getStartDate());
        assertEquals(endDate, tournament.getEndDate());
        assertEquals(location, tournament.getLocation());
        assertEquals(entryFee, tournament.getEntryFee());
        assertEquals(cashPrize, tournament.getCashPrize());
        assertEquals(participants, tournament.getParticipants());
    }

    @Test
    void testSetters() {
        Tournament tournament = new Tournament();
        tournament.setTournamentId(1L);
        tournament.setName("New Name");
        tournament.setStartDate(LocalDate.of(2025, 5, 1));
        tournament.setEndDate(LocalDate.of(2025, 5, 5));
        tournament.setLocation("Boston");
        tournament.setEntryFee(50.0);
        tournament.setCashPrize(500.0);

        List<Member> members = new ArrayList<>();
        tournament.setParticipants(members);

        assertEquals(1L, tournament.getTournamentId());
        assertEquals("New Name", tournament.getName());
        assertEquals(LocalDate.of(2025, 5, 1), tournament.getStartDate());
        assertEquals(LocalDate.of(2025, 5, 5), tournament.getEndDate());
        assertEquals("Boston", tournament.getLocation());
        assertEquals(50.0, tournament.getEntryFee());
        assertEquals(500.0, tournament.getCashPrize());
        assertEquals(members, tournament.getParticipants());
    }

    @Test
    void testToString() {
        Tournament tournament = new Tournament();
        tournament.setTournamentId(10L);
        tournament.setName("Test Tournament");
        tournament.setStartDate(LocalDate.of(2025, 2, 15));
        tournament.setEndDate(LocalDate.of(2025, 2, 20));
        tournament.setLocation("LA");
        tournament.setEntryFee(75.0);
        tournament.setCashPrize(750.0);
        tournament.setParticipants(new ArrayList<>());

        String output = tournament.toString();

        assertTrue(output.contains("Tournament ID: 10"));
        assertTrue(output.contains("Test Tournament"));
        assertTrue(output.contains("Location: LA"));
        assertTrue(output.contains("Entry Fee: $75.0"));
        assertTrue(output.contains("Start Date: 2025-02-15"));
    }
}
