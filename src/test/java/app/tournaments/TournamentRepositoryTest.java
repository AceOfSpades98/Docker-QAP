package app.tournaments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    void testSaveAndFindByStartDate() {
        Tournament tournament = new Tournament();
        tournament.setStartDate(LocalDate.of(2025, 1, 1));
        tournament.setEndDate(LocalDate.of(2025, 1, 5));
        tournament.setLocation("Testville");
        tournament.setEntryFee(30);
        tournament.setCashPrize(300);
        tournament.setParticipants(List.of());

        tournamentRepository.save(tournament);

        List<Tournament> found = tournamentRepository.findByStartDate(LocalDate.of(2025, 1, 1));
        assertFalse(found.isEmpty());
        assertEquals("Testville", found.get(0).getLocation());
    }

    @Test
    void testFindByLocation() {
        Tournament tournament = new Tournament();
        tournament.setStartDate(LocalDate.of(2025, 6, 1));
        tournament.setEndDate(LocalDate.of(2025, 6, 3));
        tournament.setLocation("Chicago");
        tournament.setEntryFee(20);
        tournament.setCashPrize(200);
        tournament.setParticipants(List.of());

        tournamentRepository.save(tournament);

        Optional<Tournament> found = tournamentRepository.findByLocation("Chicago");
        assertTrue(found.isPresent());
        assertEquals(200, found.get().getCashPrize());
    }
}
