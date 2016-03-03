package by.bsu.hostel.domain;

/**
 * Created by Kate on 13.02.2016.
 */
public class Client extends Entity{
    private Long id;
    private String name;
    private String surname;
    private String country;
//    private Long authorizationId;
    private String login;
    private String password;
    private String role;
    private Integer visitsAmount;
    private int banned;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override

    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", country='" + country + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", visitsAmount=" + visitsAmount +
                ", banned=" + banned +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }

    public Integer getVisitsAmount() {
        return visitsAmount;
    }

    public void setVisitsAmount(Integer visitsAmount) {
        this.visitsAmount = visitsAmount;
    }

//    public Long getAuthorizationId() {
//        return authorizationId;
//    }
//
//    public void setAuthorizationId(Long authorizationId) {
//        this.authorizationId = authorizationId;
//    }
}
