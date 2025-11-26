/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author VU MINH TAN
 */
public class ArtistVerificationDTO {

    int artistId;
    String stageName;
    String avatarUrl;
    String username;
    String email;
    String phone;
    List<String> genres;
    Timestamp verified_at;
    String verificationStatus;

    public ArtistVerificationDTO(int artistId, String stageName, String avatarUrl, String username, String email, String phone, List<String> genres, Timestamp verified_at, String verificationStatus) {
        this.artistId = artistId;
        this.stageName = stageName;
        this.avatarUrl = avatarUrl;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.genres = genres;
        this.verified_at = verified_at;
        this.verificationStatus = verificationStatus;
    }

    public ArtistVerificationDTO() {
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Timestamp getVerified_at() {
        return verified_at;
    }

    public void setVerified_at(Timestamp verified_at) {
        this.verified_at = verified_at;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

}
