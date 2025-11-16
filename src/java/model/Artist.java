package model;

import java.util.List;

public class Artist {
    private int id;
    private String stageName;
    private String bio;
    private String location;
    private double ratingAvg;
    private int totalReviews;
    private String imageUrl;     // lấy từ artist_media (is_primary = 1)
    private double price;        // lấy từ artist_services (giá min)
    private String genre;        // custom field, map từ bảng genres
    private String experience;   // custom
    private String status;       // trạng thái hoạt động
    private String instruments;  // custom
    private String achievements; // custom
    private List<ArtistMedia> mediaList;

    // No-arg constructor
    public Artist() {}

    // Constructor cơ bản
    public Artist(int id, String stageName, String bio, String location,
                  double ratingAvg, int totalReviews, String imageUrl, double price) {
        this(id, stageName, bio, location, ratingAvg, totalReviews, imageUrl, price, null);
    }

    // Constructor có mediaList
    public Artist(int id, String stageName, String bio, String location,
                  double ratingAvg, int totalReviews, String imageUrl,
                  double price, List<ArtistMedia> mediaList) {
        this.id = id;
        this.stageName = stageName;
        this.bio = bio;
        this.location = location;
        this.ratingAvg = ratingAvg;
        this.totalReviews = totalReviews;
        this.imageUrl = imageUrl;
        this.price = price;
        this.mediaList = mediaList;
    }

    // Constructor đầy đủ
    public Artist(int id, String stageName, String bio, String location,
                  double ratingAvg, int totalReviews, String imageUrl, double price,
                  String genre, String experience, String status,
                  String instruments, String achievements, List<ArtistMedia> mediaList) {
        this.id = id;
        this.stageName = stageName;
        this.bio = bio;
        this.location = location;
        this.ratingAvg = ratingAvg;
        this.totalReviews = totalReviews;
        this.imageUrl = imageUrl;
        this.price = price;
        this.genre = genre;
        this.experience = experience;
        this.status = status;
        this.instruments = instruments;
        this.achievements = achievements;
        this.mediaList = mediaList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstruments() {
        return instruments;
    }

    public void setInstruments(String instruments) {
        this.instruments = instruments;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public List<ArtistMedia> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<ArtistMedia> mediaList) {
        this.mediaList = mediaList;
    }

    
}
