package app.members;

import app.tournaments.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/members")  // Add base path for clarity
public class MemberController {

    private final MemberService memberService;
    private final TournamentService tournamentService;

    @Autowired
    public MemberController(MemberService memberService, TournamentService tournamentService) {
        this.memberService = memberService;
        this.tournamentService = tournamentService;
    }

    // Search by ID
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long memberId) {
        return memberService.getMemberById(memberId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Search by name
    @GetMapping("/search/by-name")
    public ResponseEntity<Member> getMemberByName(@RequestParam String firstName, @RequestParam String lastName) {
        return memberService.getMemberByName(firstName, lastName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Search by membership type
    @GetMapping("/search/by-type")
    public List<Member> getMembersByType(@RequestParam MembershipType type) {
        return memberService.getMembersByType(type);
    }

    // Search by email
    @GetMapping("/search/by-email")
    public ResponseEntity<Member> getMemberByEmail(@RequestParam String email) {
        return memberService.getMemberByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Search by phone number
    @GetMapping("/search/by-phone")
    public ResponseEntity<Member> getMemberByPhone(@RequestParam String phoneNum) {
        return memberService.getMemberByPhoneNumber(phoneNum)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Search by tournament start date
    @GetMapping("/search/by-tournament-date")
    public List<Member> getMembersByTournamentStartDate(@RequestParam String startDate) {
        return memberService.getMembersByTournamentStartDate(LocalDate.parse(startDate));
    }

    // Get all members
    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    // Create member
    @PostMapping
    public Member createMember(@RequestBody Member newMember) {
        return memberService.createMember(newMember);
    }

    // Update member
    @PutMapping("/{memberId}")
    public ResponseEntity<Member> updateMember(@PathVariable Long memberId, @RequestBody Member updated) {
        try {
            Member member = memberService.updateMember(memberId, updated);
            return ResponseEntity.ok(member);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete member
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }
}
