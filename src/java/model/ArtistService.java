/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author VU MINH TAN
 */
import java.sql.Timestamp;

public class ArtistService {
    private int serviceId;
    private int artistId;
    private String serviceName;
    private String description;
    private double price;
    private int durationMinutes;
    private boolean isActive;
    private Timestamp createdAt;

    // Constructor mặc định
    public ArtistService() {
    }

    // Constructor đầy đủ
    public ArtistService(int serviceId, int artistId, String serviceName, String description,
                         double price, int durationMinutes, boolean isActive, Timestamp createdAt) {
        this.serviceId = serviceId;
        this.artistId = artistId;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.durationMinutes = durationMinutes;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
