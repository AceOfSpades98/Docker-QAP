package tournaments;

import members.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {
    @Autowired
    public TournamentRepository tournamentRepository;

    @Autowired
    public MemberService memberService;

    // Search by start date
    public List<Tournament> findByStartDate(LocalDate startDate) {
        return tournamentRepository.findByStartDate(startDate);
    }

    // Search by location
    public Optional<Tournament> findByLocation(String location) {
        return tournamentRepository.findByLocation(location);
    }

    // Find all members in a tournament
    public List<Member> findAllMembersInTournament(Long tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .map(Tournament::getParticipants)
                .orElseThrow(() -> new RuntimeException("Tournament not found."));
    }

    // Add members to a tournament
    public Tournament addMembersToTournament(Long tournamentId, List<Long> memberIds) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found with ID: " + tournamentId));

        List<Member> participants = tournament.getParticipants();

        for (Long memberId : memberIds) {
            Member member = memberService.getMemberById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

            if (!participants.contains(member)) {
                participants.add(member);
            }
        }

        tournament.setParticipants(participants);
        return tournamentRepository.save(tournament);
    }

    // Get all tournaments
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    // Create a tournament
    public Tournament createTournament(Tournament newTournament) {
        return tournamentRepository.save(new Tournament());
    }

    // Update a tournament
    public Tournament updateTournament(Long tournamentId, Tournament updatedTournament) {
        Optional<Tournament> existingTournamentOpt = tournamentRepository.findById(tournamentId);

        if (existingTournamentOpt.isPresent()) {
            Tournament existingTournament = existingTournamentOpt.get();

            existingTournament.setStartDate(updatedTournament.getStartDate());
            existingTournament.setEndDate(updatedTournament.getEndDate());
            existingTournament.setLocation(updatedTournament.getLocation());
            existingTournament.setEntryFee(updatedTournament.getEntryFee());
            existingTournament.setCashPrize(updatedTournament.getCashPrize());
            existingTournament.setParticipants(updatedTournament.getParticipants());

            return tournamentRepository.save(existingTournament);
        } else {
            throw new RuntimeException("Tournament with ID: " + tournamentId + " not found.");
        }
    }

    // Delete a tournament
    public void deleteTournament(Long tournamentId) {
        tournamentRepository.deleteById(tournamentId);
    }
}
