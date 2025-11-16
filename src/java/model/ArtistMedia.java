package model;

import java.sql.Timestamp;

public class ArtistMedia {
    private int mediaId;
    private int artistId;
    private String mediaType;   // enum trong DB -> String trong Java
    private String url;
    private boolean isPrimary;  // tinyint(1) -> boolean
    private Timestamp uploadedAt;

    public ArtistMedia() {
    }

    public ArtistMedia(int mediaId, int artistId, String mediaType, String url, boolean isPrimary, Timestamp uploadedAt) {
        this.mediaId = mediaId;
        this.artistId = artistId;
        this.mediaType = mediaType;
        this.url = url;
        this.isPrimary = isPrimary;
        this.uploadedAt = uploadedAt;
    }
    public ArtistMedia(int mediaId, String url, String mediaType) {
        this.mediaId = mediaId;
        this.url = url;
        this.mediaType = mediaType;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
