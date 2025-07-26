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
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 10);
        String location = "New York";
        double entryFee = 100.0;
        double cashPrize = 1000.0;
        List<Member> participants = new ArrayList<>();

        Tournament tournament = new Tournament(startDate, endDate, location, entryFee, cashPrize, participants);

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
        tournament.setStartDate(LocalDate.of(2025, 5, 1));
        tournament.setEndDate(LocalDate.of(2025, 5, 5));
        tournament.setLocation("Boston");
        tournament.setEntryFee(50.0);
        tournament.setCashPrize(500.0);

        List<Member> members = new ArrayList<>();
        tournament.setParticipants(members);

        assertEquals(1L, tournament.getTournamentId());
        assertEquals("Boston", tournament.getLocation());
        assertEquals(50.0, tournament.getEntryFee());
        assertEquals(500.0, tournament.getCashPrize());
        assertEquals(members, tournament.getParticipants());
    }

    @Test
    void testToString() {
        Tournament tournament = new Tournament();
        tournament.setTournamentId(10L);
        tournament.setStartDate(LocalDate.of(2025, 2, 15));
        tournament.setEndDate(LocalDate.of(2025, 2, 20));
        tournament.setLocation("LA");
        tournament.setEntryFee(75.0);
        tournament.setCashPrize(750.0);
        tournament.setParticipants(new ArrayList<>());

        String output = tournament.toString();
        assertTrue(output.contains("Tournament ID: 10"));
        assertTrue(output.contains("Location: LA"));
        assertTrue(output.contains("Entry Fee: $75.0"));
    }
}
