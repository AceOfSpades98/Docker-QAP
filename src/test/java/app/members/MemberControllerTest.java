package app.members;

import app.tournaments.TournamentService;
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
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private TournamentService tournamentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = new Member();
        testMember.setMemberId(1L);
        testMember.setFirstName("John");
        testMember.setLastName("Doe");
        testMember.setEmail("john@example.com");
        testMember.setPhoneNum("1234567890");
        testMember.setType(MembershipType.INDIVIDUAL);
    }

    @Test
    void getMemberById_Found() throws Exception {
        Mockito.when(memberService.getMemberById(1L)).thenReturn(Optional.of(testMember));

        mockMvc.perform(get("/api/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getMemberByName() throws Exception {
        Mockito.when(memberService.getMemberByName("John", "Doe")).thenReturn(Optional.of(testMember));

        mockMvc.perform(get("/api/members/name/John/Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void getMembersByType() throws Exception {
        Mockito.when(memberService.getMembersByType(MembershipType.INDIVIDUAL))
                .thenReturn(Collections.singletonList(testMember));

        mockMvc.perform(get("/api/members/type/INDIVIDUAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void getMemberByEmail() throws Exception {
        Mockito.when(memberService.getMemberByEmail("john@example.com"))
                .thenReturn(Optional.of(testMember));

        mockMvc.perform(get("/api/members/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNum").value("1234567890"));
    }

    @Test
    void getMemberByPhone() throws Exception {
        Mockito.when(memberService.getMemberByPhoneNumber("1234567890"))
                .thenReturn(Optional.of(testMember));

        mockMvc.perform(get("/api/members/phoneNum/1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getMembersByTournamentStartDate() throws Exception {
        Mockito.when(memberService.getMembersByTournamentStartDate(LocalDate.of(2024, 1, 1)))
                .thenReturn(Collections.singletonList(testMember));

        mockMvc.perform(get("/api/members/startDate/2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    void getAllMembers() throws Exception {
        Mockito.when(memberService.getAllMembers()).thenReturn(Collections.singletonList(testMember));

        mockMvc.perform(get("/api/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    void createMember() throws Exception {
        Mockito.when(memberService.createMember(any(Member.class))).thenReturn(testMember);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMember)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void updateMember() throws Exception {
        Mockito.when(memberService.updateMember(eq(1L), any(Member.class))).thenReturn(testMember);

        mockMvc.perform(put("/api/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMember)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void deleteMember() throws Exception {
        mockMvc.perform(delete("/api/members/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(memberService).deleteMember(1L);
    }
}
