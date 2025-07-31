package app.tournaments;

import app.members.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TournamentController.class)
class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TournamentService tournamentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Tournament sampleTournament;

    @BeforeEach
    void setUp() {
        sampleTournament = new Tournament();
        sampleTournament.setTournamentId(1L);
        sampleTournament.setName("Spring Open Invitational");
        sampleTournament.setStartDate(LocalDate.of(2025, 3, 15));
        sampleTournament.setLocation("New York City");
    }

    @Test
    void getAllTournaments_ShouldReturnListOfTournaments() throws Exception {
        Mockito.when(tournamentService.getAllTournaments())
                .thenReturn(Collections.singletonList(sampleTournament));

        mockMvc.perform(get("/api/tournament"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Spring Open Invitational"));
    }

    @Test
    void getTournamentByTournamentId_ShouldReturnTournament_WhenFound() throws Exception {
        Mockito.when(tournamentService.findById(1L))
                .thenReturn(Optional.of(sampleTournament));

        mockMvc.perform(get("/api/tournament/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("New York City"));
    }

    @Test
    void getTournamentByTournamentId_ShouldReturnNotFound_WhenNotFound() throws Exception {
        Mockito.when(tournamentService.findById(999L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tournament/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTournamentsByStartDate_ShouldReturnTournaments() throws Exception {
        Mockito.when(tournamentService.findByStartDate(LocalDate.of(2025, 3, 15)))
                .thenReturn(Collections.singletonList(sampleTournament));

        mockMvc.perform(get("/api/tournament/start-date/2025-03-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Spring Open Invitational"));
    }

    @Test
    void getTournamentsByStartDate_ShouldReturnBadRequest_ForInvalidDate() throws Exception {
        mockMvc.perform(get("/api/tournament/start-date/not-a-date"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTournamentByLocation_ShouldReturnTournament_WhenFound() throws Exception {
        Mockito.when(tournamentService.findByLocation("New York City"))
                .thenReturn(Optional.of(sampleTournament));

        mockMvc.perform(get("/api/tournament/location/New York City"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spring Open Invitational"));
    }

    @Test
    void getTournamentByLocation_ShouldReturnNotFound_WhenMissing() throws Exception {
        Mockito.when(tournamentService.findByLocation("Atlantis"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tournament/location/Atlantis"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMembersInTournament_ShouldReturnListOfMembers() throws Exception {
        Member exampleMember = new Member();
        exampleMember.setMemberId(1L);
        exampleMember.setFirstName("Alice");

        Mockito.when(tournamentService.findAllMembersInTournament(1L))
                .thenReturn(List.of(exampleMember));

        mockMvc.perform(get("/api/tournament/1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Alice"));
    }

    @Test
    void addMembersToTournament_ShouldReturnUpdatedTournament() throws Exception {
        List<Long> mockMemberIds = Arrays.asList(1L, 2L);

        Mockito.when(tournamentService.addMembersToTournament(eq(1L), any()))
                .thenReturn(sampleTournament);

        mockMvc.perform(put("/api/tournament/1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockMemberIds)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spring Open Invitational"));
    }

    @Test
    void createTournament_ShouldReturnCreatedTournament() throws Exception {
        Mockito.when(tournamentService.createTournament(any(Tournament.class)))
                .thenReturn(sampleTournament);

        mockMvc.perform(post("/api/tournament")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTournament)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.location").value("New York City"));
    }

    @Test
    void updateTournament_ShouldReturnUpdatedTournament() throws Exception {
        Mockito.when(tournamentService.updateTournament(eq(1L), any()))
                .thenReturn(sampleTournament);

        mockMvc.perform(put("/api/tournament/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTournament)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spring Open Invitational"));
    }

    @Test
    void deleteTournament_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/tournament/1"))
                .andExpect(status().isOk());

        Mockito.verify(tournamentService).deleteTournament(1L);
    }

    @Test
    void shouldReturnTournamentWhenFoundByName() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setTournamentId(1L);
        tournament.setName("Champions Cup");
        tournament.setLocation("New York");

        Mockito.when(tournamentService.findByName("Champions Cup"))
                .thenReturn(Optional.of(tournament));

        mockMvc.perform(get("/api/tournament/name/Champions Cup"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Champions Cup"))
                .andExpect(jsonPath("$.location").value("New York"));
    }

    @Test
    void shouldReturn404WhenTournamentNotFoundByName() throws Exception {
        Mockito.when(tournamentService.findByName("Unknown Cup"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tournament/name/Unknown Cup"))
                .andExpect(status().isNotFound());
    }
}
