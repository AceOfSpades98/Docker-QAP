package members;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private MembershipType type;

    private LocalDate startOfMembership;
    private double duration;

    public Member() {
    }

    public Member(String firstName, String lastName, String address, String email, String phoneNum,
                  MembershipType type, LocalDate startOfMembership, double duration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phoneNum = phoneNum;
        this.type = type;
        this.startOfMembership = startOfMembership;
        this.duration = duration;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public MembershipType getType() {
        return type;
    }

    public LocalDate getStartOfMembership() {
        return startOfMembership;
    }

    public double getDuration() {
        return duration;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }

    public void setStartOfMembership(LocalDate startOfMembership) {
        this.startOfMembership = startOfMembership;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ID: " + memberId + "\n" +
                "Name: " + firstName + " " + lastName + "\n" +
                "Address: " + address + "\n" +
                "Email: " + email + "\n" +
                "Phone #: " + phoneNum + "\n" +
                "Membership: " + type + "\n" +
                "Start of Membership: " + startOfMembership + "\n" +
                "Duration of Membership: " + duration;
    }
}