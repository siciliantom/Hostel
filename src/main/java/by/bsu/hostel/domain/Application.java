package by.bsu.hostel.domain;

import java.util.Date;

/**
 * Created by Kate on 13.02.2016.
 */
public class Application {
    private Long id;
    private Integer placesAmount;
    private int bookOrPay;
    private Date arrivalDate;
    private Date departureDate;
    private Long clientId;
    private int confirmed;
    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlacesAmount() {
        return placesAmount;
    }

    public void setPlacesAmount(Integer placesAmount) {
        this.placesAmount = placesAmount;
    }

    public Integer getBookOrPay() {
        return bookOrPay;
    }

    public void setBookOrPay(Integer bookOrPay) {
        this.bookOrPay = bookOrPay;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
