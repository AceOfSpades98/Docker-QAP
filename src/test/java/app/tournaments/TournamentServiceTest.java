package app.tournaments;

import app.members.Member;
import app.members.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TournamentServiceTest {

    @InjectMocks
    private TournamentService tournamentService;

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByStartDate() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        List<Tournament> mockList = List.of(new Tournament());
        when(tournamentRepository.findByStartDate(date)).thenReturn(mockList);

        List<Tournament> result = tournamentService.findByStartDate(date);
        assertEquals(1, result.size());
    }

    @Test
    void testFindByLocation() {
        Tournament tournament = new Tournament();
        when(tournamentRepository.findByLocation("NY")).thenReturn(Optional.of(tournament));

        Optional<Tournament> result = tournamentService.findByLocation("NY");
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAllMembersInTournament() {
        List<Member> members = List.of(new Member());
        Tournament tournament = new Tournament();
        tournament.setParticipants(members);

        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));

        List<Member> result = tournamentService.findAllMembersInTournament(1L);
        assertEquals(1, result.size());
    }

    @Test
    void testAddMembersToTournament() {
        Member member = new Member();
        member.setMemberId(42L);

        Tournament tournament = new Tournament();
        tournament.setParticipants(new ArrayList<>());

        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));
        when(memberService.getMemberById(42L)).thenReturn(Optional.of(member));
        when(tournamentRepository.save(any())).thenReturn(tournament);

        Tournament result = tournamentService.addMembersToTournament(1L, List.of(42L));
        assertEquals(1, result.getParticipants().size());
    }

    @Test
    void testCreateTournament() {
        Tournament input = new Tournament();
        Tournament saved = new Tournament();
        when(tournamentRepository.save(any())).thenReturn(saved);

        Tournament result = tournamentService.createTournament(input);
        assertNotNull(result);
    }

    @Test
    void testUpdateTournamentSuccess() {
        Tournament original = new Tournament();
        Tournament updated = new Tournament();
        updated.setStartDate(LocalDate.now());
        updated.setEndDate(LocalDate.now().plusDays(1));
        updated.setLocation("LA");
        updated.setEntryFee(25);
        updated.setCashPrize(500);
        updated.setParticipants(new ArrayList<>());

        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(original));
        when(tournamentRepository.save(any())).thenReturn(original);

        Tournament result = tournamentService.updateTournament(1L, updated);
        assertEquals("LA", result.getLocation());
    }

    @Test
    void testDeleteTournament() {
        doNothing().when(tournamentRepository).deleteById(1L);
        tournamentService.deleteTournament(1L);
        verify(tournamentRepository, times(1)).deleteById(1L);
    }
}
