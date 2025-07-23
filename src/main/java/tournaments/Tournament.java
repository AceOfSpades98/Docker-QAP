package tournaments;

import members.Member;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentId;

    private String startDate;
    private String endDate;
    private String location;
    private double entryFee;
    private double cashPrize;
    @ManyToMany
    private List<Member> participants;

    public Tournament() {
    }

    public Tournament(String startDate, String endDate, String location, double entryFee, double cashPrize, List<Member> participants) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.entryFee = entryFee;
        this.cashPrize = cashPrize;
        this.participants = participants;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

    public double getEntryFee() {
        return entryFee;
    }

    public double getCashPrize() {
        return cashPrize;
    }

    public List<Member> getParticipants() {
        return participants;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEntryFee(double entryFee) {
        this.entryFee = entryFee;
    }

    public void setCashPrize(double cashPrize) {
        this.cashPrize = cashPrize;
    }

    public void setParticipants(List<Member> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Tournament ID: " + tournamentId + "\n" +
                "Start Date: " + startDate + "\n" +
                "End Date: " + endDate + "\n" +
                "Location: " + location + "\n" +
                "Entry Fee: $" + entryFee + "\n" +
                "Cash Prize: $" + cashPrize + "\n" +
                "Participants:\n" + participants.toString();
    }

}
