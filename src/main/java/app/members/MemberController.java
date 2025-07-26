package app.members;

import app.tournaments.TournamentController;
import app.tournaments.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping
public class MemberController {

    private final MemberService memberService;
    private final TournamentService tournamentService;

    @Autowired
    public MemberController(MemberService memberService, TournamentService tournamentService) {
        this.memberService = memberService;
        this.tournamentService = tournamentService;
    }

    // Search by ID

    // Search by name

    // Search by membership type

    // Search by email

    // Search by phone number

    // Search by start date of a tournament

    // Get all members

    // Create member

    // Update member

    // Delete member

}
