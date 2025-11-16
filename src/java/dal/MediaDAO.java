package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ArtistMedia;

public class MediaDAO extends DBContext {

    // Lấy toàn bộ media của một artist
    public List<ArtistMedia> getMediaByArtistId(int artistId) {
        List<ArtistMedia> list = new ArrayList<>();
        String sql = "SELECT * FROM artist_media WHERE artist_id = ? ORDER BY uploaded_at DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, artistId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArtistMedia m = new ArtistMedia();
                m.setMediaId(rs.getInt("media_id"));
                m.setArtistId(rs.getInt("artist_id"));
                m.setMediaType(rs.getString("media_type"));
                m.setUrl(rs.getString("url"));
                m.setPrimary(rs.getBoolean("is_primary"));
                m.setUploadedAt(rs.getTimestamp("uploaded_at"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // Lưu hoặc cập nhật avatar

    public void saveOrUpdateAvatar(int artistId, String url) {
        String sqlCheck = "SELECT * FROM artist_media WHERE artist_id = ? AND is_primary = 1";
        String sqlInsert = "INSERT INTO artist_media (artist_id, media_type, url, is_primary, uploaded_at) VALUES (?, 'image', ?, 1, NOW())";
        String sqlUpdate = "UPDATE artist_media SET url = ?, uploaded_at = NOW() WHERE artist_id = ? AND is_primary = 1";

        try (PreparedStatement psCheck = connection.prepareStatement(sqlCheck)) {
            psCheck.setInt(1, artistId);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                try (PreparedStatement psUpdate = connection.prepareStatement(sqlUpdate)) {
                    psUpdate.setString(1, url);
                    psUpdate.setInt(2, artistId);
                    psUpdate.executeUpdate();
                }
            } else {
                try (PreparedStatement psInsert = connection.prepareStatement(sqlInsert)) {
                    psInsert.setInt(1, artistId);
                    psInsert.setString(2, url);
                    psInsert.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy ảnh chính (is_primary = 1) của artist
    public ArtistMedia getPrimaryMedia(int artistId) {
        String sql = "SELECT * FROM artist_media WHERE artist_id = ? AND is_primary = 1 LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, artistId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ArtistMedia m = new ArtistMedia();
                m.setMediaId(rs.getInt("media_id"));
                m.setArtistId(rs.getInt("artist_id"));
                m.setMediaType(rs.getString("media_type"));
                m.setUrl(rs.getString("url"));
                m.setPrimary(rs.getBoolean("is_primary"));
                m.setUploadedAt(rs.getTimestamp("uploaded_at"));
                return m;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm media mới
    public boolean addMedia(ArtistMedia media) {
        String sql = "INSERT INTO artist_media (artist_id, media_type, url, is_primary, uploaded_at) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, media.getArtistId());
            ps.setString(2, media.getMediaType());
            ps.setString(3, media.getUrl());
            ps.setBoolean(4, media.isPrimary());
            ps.setTimestamp(5, media.getUploadedAt());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa media theo ID
    public void deleteByArtistId(int artistId) {
        String sql = "DELETE FROM artist_media WHERE artist_id = ? AND is_primary = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, artistId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa avatar
    public void deleteByUserId(int userId) {
        String sql = "DELETE FROM artist_media WHERE user_id = ? AND type = 'avatar'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MediaDAO dao = new MediaDAO();

        int artistId = 6; // ✅ Thay bằng ID thật có trong bảng artist_media
        String newAvatarUrl = "uploads/avatar_test_2.jpg";

        System.out.println("=== TEST: saveOrUpdateAvatar ===");
        System.out.println("Trước khi cập nhật:");
        List<ArtistMedia> before = dao.getMediaByArtistId(artistId);
        for (ArtistMedia m : before) {
            System.out.println("MediaID: " + m.getMediaId()
                    + " | URL: " + m.getUrl()
                    + " | Primary: " + m.isPrimary());
        }

        // ✅ Gọi hàm cần test
        dao.saveOrUpdateAvatar(artistId, newAvatarUrl);

        System.out.println("\nSau khi gọi saveOrUpdateAvatar:");
        List<ArtistMedia> after = dao.getMediaByArtistId(artistId);
        for (ArtistMedia m : after) {
            System.out.println("MediaID: " + m.getMediaId()
                    + " | URL: " + m.getUrl()
                    + " | Primary: " + m.isPrimary());
        }

        // ✅ Kiểm tra ảnh chính
        ArtistMedia primary = dao.getPrimaryMedia(artistId);
        if (primary != null) {
            System.out.println("\nẢnh chính hiện tại:");
            System.out.println("MediaID: " + primary.getMediaId()
                    + " | URL: " + primary.getUrl()
                    + " | UploadedAt: " + primary.getUploadedAt());
        } else {
            System.out.println("\nKhông tìm thấy ảnh chính cho artist_id = " + artistId);
        }
    }

}
