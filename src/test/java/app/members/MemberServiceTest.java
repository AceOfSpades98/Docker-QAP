package app.members;

import app.tournaments.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member(
                "Jane", "Doe", "123 Maple St", "jane@example.com", "777-123-4567",
                MembershipType.INDIVIDUAL, LocalDate.of(2025, 1, 1), 12.0
        );
        member.setMemberId(1L);
    }

    @Test
    void getMemberById_found_returnsMember() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        Optional<Member> result = memberService.getMemberById(1L);
        assertThat(result).contains(member);
        verify(memberRepository).findById(1L);
    }

    @Test
    void getMemberById_notFound_returnsEmpty() {
        when(memberRepository.findById(2L)).thenReturn(Optional.empty());
        assertThat(memberService.getMemberById(2L)).isEmpty();
    }

    @Test
    void getMemberByName_returnsExpected() {
        when(memberRepository.findByFirstNameAndLastName("Jane", "Doe"))
                .thenReturn(Optional.of(member));
        Optional<Member> result = memberService.getMemberByName("Jane", "Doe");
        assertThat(result).contains(member);
    }

    @Test
    void getMembersByType_filtersCorrectly() {
        Member other = new Member("John","Smith","X","john@example.com","999",
                MembershipType.INDIVIDUAL, LocalDate.now(), 6.0);
        other.setMemberId(2L);
        when(memberRepository.findByType(MembershipType.INDIVIDUAL))
                .thenReturn(List.of(member, other));

        List<Member> result = memberService.getMembersByType(MembershipType.INDIVIDUAL);
        assertThat(result).hasSize(2)
                .allMatch(m -> m.getType() == MembershipType.INDIVIDUAL);
    }

    @Test
    void getMemberByEmail_andByPhoneNumber_returnCorrect() {
        when(memberRepository.findByEmail("jane@example.com"))
                .thenReturn(Optional.of(member));
        when(memberRepository.findByPhoneNum("123-4567"))
                .thenReturn(Optional.of(member));

        assertThat(memberService.getMemberByEmail("jane@example.com")).contains(member);
        assertThat(memberService.getMemberByPhoneNumber("123-4567")).contains(member);
    }

    @Test
    void getMembersByTournamentStartDate_collectsDistinct() {
        Tournament t = mock(Tournament.class);
        Member secondMember = new Member("Alice","Brown","Y","alice@example.com","321",
                MembershipType.STUDENT, LocalDate.now(),3.0);
        secondMember.setMemberId(2L);
        when(t.getParticipants()).thenReturn(List.of(member, secondMember, member));

        LocalDate start = LocalDate.of(2025, 6, 1);
        when(tournamentRepository.findByStartDate(start))
                .thenReturn(List.of(t));

        List<Member> result = memberService.getMembersByTournamentStartDate(start);
        assertThat(result).containsExactlyInAnyOrder(member, secondMember);
    }

    @Test
    void createMember_savesAndReturns() {
        when(memberRepository.save(member)).thenReturn(member);
        Member created = memberService.createMember(member);
        assertThat(created).isSameAs(member);
        verify(memberRepository).save(member);
    }

    @Test
    void updateMember_existing_updatesFields() {
        Member updated = new Member("Janet","Doe","New Addr","janet@example.com","987-654",
                MembershipType.SENIOR, LocalDate.of(2025, 2, 1), 24.5);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenAnswer(inv -> inv.getArgument(0));

        Member result = memberService.updateMember(1L, updated);

        assertThat(result.getFirstName()).isEqualTo("Janet");
        assertThat(result.getAddress()).isEqualTo("New Addr");
        assertThat(result.getDuration()).isCloseTo(24.5, within(1e-6));
        assertThat(result.getType()).isEqualTo(MembershipType.SENIOR);
        verify(memberRepository).save(member);
    }

    @Test
    void updateMember_notFound_throwsRuntimeException() {
        when(memberRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> memberService.updateMember(99L, member))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Member with ID: 99 not found.");
    }

    @Test
    void deleteMember_deletesById() {
        doNothing().when(memberRepository).deleteById(1L);
        memberService.deleteMember(1L);
        verify(memberRepository).deleteById(1L);
    }
}
