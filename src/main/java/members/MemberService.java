package members;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tournaments.Tournament;
import tournaments.TournamentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TournamentRepository tournamentRepository;

    // Search by ID
    public Optional<Member> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // Search by name
    public Optional<Member> getMemberByName(String firstName, String lastName) {
        return memberRepository.findByName(firstName, lastName);
    }

    // Search by membership type

    // Search by email
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // Search by phone number
    public Optional<Member> getMemberByPhoneNumber(String phoneNum) {
        return memberRepository.findByPhoneNumber(phoneNum);
    }

    // Search by start date of tournament
    public List<Member> getMembersByTournamentStartDate(LocalDate startDate) {
        List<Tournament> tournaments = tournamentRepository.findByStartDate(startDate);
        return tournaments
                .stream()
                .flatMap(t -> t.getParticipants().stream())
                .distinct()
                .toList();
    }

    // Get all members
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Create member
    public Member createMember(Member newMember) {
        return memberRepository.save(newMember);
    }

    // Update member
    public Member updateMember(Long memberId, Member updatedMember) {
        Optional<Member> existingMemberOpt = memberRepository.findById(memberId);

        if (existingMemberOpt.isPresent()) {
            Member existingMember = existingMemberOpt.get();

            existingMember.setFirstName(updatedMember.getFirstName());
            existingMember.setLastName(updatedMember.getLastName());
            // existingMember.setType
            existingMember.setAddress(updatedMember.getAddress());
            existingMember.setEmail(updatedMember.getEmail());
            existingMember.setPhoneNum(updatedMember.getEmail());
            existingMember.setStartOfMembership((updatedMember.getStartOfMembership()));
            existingMember.setDuration(updatedMember.getDuration());

            return memberRepository.save(existingMember);
        } else {
            throw new RuntimeException("Member with ID: " + memberId + " not found.");
        }
    }

    // Delete member
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
