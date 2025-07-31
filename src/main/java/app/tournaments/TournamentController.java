package app.tournaments;

import app.members.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/tournament")
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    // Get all tournaments
    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        return new ResponseEntity<>(tournaments, HttpStatus.OK);
    }

    // Get tournament by ID
    @GetMapping("/{tournamentId}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long tournamentId) {
        Optional<Tournament> tournament = tournamentService.findById(tournamentId);
        return tournament
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Search by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Tournament> getTournamentsByName(@PathVariable String name) {
        Optional<Tournament> tournament = tournamentService.findByName(name);
        return tournament
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Search by start date
    @GetMapping("/start-date/{startDate}")
    public ResponseEntity<List<Tournament>> getTournamentsByStartDate(@PathVariable String startDate) {
        try {
            LocalDate parsedDate = LocalDate.parse(startDate);
            List<Tournament> tournaments = tournamentService.findByStartDate(parsedDate);
            return new ResponseEntity<>(tournaments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Search by location
    @GetMapping("/location/{location}")
    public ResponseEntity<Tournament> getTournamentByLocation(@PathVariable String location) {
        Optional<Tournament> tournament = tournamentService.findByLocation(location);
        return tournament
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get members in a tournament
    @GetMapping("/{tournamentId}/members")
    public ResponseEntity<List<Member>> getMembersInTournament(@PathVariable Long tournamentId) {
        List<Member> members = tournamentService.findAllMembersInTournament(tournamentId);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    // Add members to a tournament
    @PutMapping("/{tournamentId}/members")
    public ResponseEntity<Tournament> addMembersToTournament(
            @PathVariable Long tournamentId,
            @RequestBody List<Long> memberIds) {
        Tournament updatedTournament = tournamentService.addMembersToTournament(tournamentId, memberIds);
        return new ResponseEntity<>(updatedTournament, HttpStatus.OK);
    }

    // Create a tournament
    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        Tournament createdTournament = tournamentService.createTournament(tournament);
        return new ResponseEntity<>(createdTournament, HttpStatus.CREATED);
    }

    // Update a tournament
    @PutMapping("/{tournamentId}")
    public ResponseEntity<Tournament> updateTournament(
            @PathVariable Long tournamentId,
            @RequestBody Tournament tournament) {
        Tournament updatedTournament = tournamentService.updateTournament(tournamentId, tournament);
        return new ResponseEntity<>(updatedTournament, HttpStatus.OK);
    }

    // Delete a tournament
    @DeleteMapping("/{tournamentId}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long tournamentId) {
        tournamentService.deleteTournament(tournamentId);
        return ResponseEntity.ok().build();
    }
}