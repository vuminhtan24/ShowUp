package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int eventId;
    private int artistId;
    private int serviceId;
    private BigDecimal priceEstimated;
    private BigDecimal priceFinal;
    private String status;
    private Timestamp createdAt;

    public Booking() {
    }

    public Booking(int bookingId, int eventId, int artistId, int serviceId, BigDecimal priceEstimated, BigDecimal priceFinal, String status, Timestamp createdAt) {
        this.bookingId = bookingId;
        this.eventId = eventId;
        this.artistId = artistId;
        this.serviceId = serviceId;
        this.priceEstimated = priceEstimated;
        this.priceFinal = priceFinal;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getPriceEstimated() {
        return priceEstimated;
    }

    public void setPriceEstimated(BigDecimal priceEstimated) {
        this.priceEstimated = priceEstimated;
    }

    public BigDecimal getPriceFinal() {
        return priceFinal;
    }

    public void setPriceFinal(BigDecimal priceFinal) {
        this.priceFinal = priceFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    
}
