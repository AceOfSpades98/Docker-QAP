package members;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String address;
    private String email;
    private String phoneNum;
    private String startOfMembership;
    private double duration;

    public Member() {
    }

    public Member(String address, String email, String phoneNum, String startOfMembership, double duration) {
        this.address = address;
        this.email = email;
        this.phoneNum = phoneNum;
        this.startOfMembership = startOfMembership;
        this.duration = duration;
    }

    public Long getMemberId() {
        return memberId;
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

    public String getStartOfMembership() {
        return startOfMembership;
    }

    public double getDuration() {
        return duration;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public void setStartOfMembership(String startOfMembership) {
        this.startOfMembership = startOfMembership;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ID: " + memberId + "\n" +
                "Address: " + address + "\n" +
                "Email: " + email + "\n" +
                "Phone #: " + phoneNum + "\n" +
                "Start of Membership: " + startOfMembership + "\n" +
                "Duration of Membership: " + duration;
    }

}