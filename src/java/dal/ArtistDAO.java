package dal;

import java.sql.*;
import java.util.*;
import model.Artist;

public class ArtistDAO extends DBContext {

    public List<Artist> getAllArtists() {
        List<Artist> list = new ArrayList<>();
        String sql = """
            SELECT ap.artist_id, ap.stage_name, ap.bio, ap.location,
                   ap.rating_avg, ap.total_reviews,
                   am.url AS image_url,
                   asv.price
            FROM artist_profiles ap
            LEFT JOIN artist_media am 
                   ON ap.artist_id = am.artist_id AND am.is_primary = 1
            LEFT JOIN artist_services asv 
                   ON ap.artist_id = asv.artist_id
            """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Artist a = new Artist(
                        rs.getInt("artist_id"),
                        rs.getString("stage_name"),
                        rs.getString("bio"),
                        rs.getString("location"),
                        rs.getDouble("rating_avg"),
                        rs.getInt("total_reviews"),
                        rs.getString("image_url"),
                        rs.getDouble("price") // ✅ thêm price
                );
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Artist> getArtistsByPage(int page, int pageSize) {
        List<Artist> list = new ArrayList<>();
        String sql = """
        SELECT ap.artist_id, ap.stage_name, ap.bio, ap.location,
               ap.rating_avg, ap.total_reviews,
               am.url AS image_url,
               asv.price
        FROM artist_profiles ap
        LEFT JOIN artist_media am 
               ON ap.artist_id = am.artist_id AND am.is_primary = 1
        LEFT JOIN artist_services asv 
               ON ap.artist_id = asv.artist_id
        ORDER BY ap.artist_id
        LIMIT ? OFFSET ?
        """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, pageSize);
            st.setInt(2, (page - 1) * pageSize);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Artist a = new Artist(
                        rs.getInt("artist_id"),
                        rs.getString("stage_name"),
                        rs.getString("bio"),
                        rs.getString("location"),
                        rs.getDouble("rating_avg"),
                        rs.getInt("total_reviews"),
                        rs.getString("image_url"),
                        rs.getDouble("price") // ✅ thêm price
                );
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalArtists() {
        String sql = "SELECT COUNT(*) FROM artist_profiles";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getPhoneByArtistId(int artistId) {
        String sql = "SELECT phone FROM users WHERE user_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, artistId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("phone");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getEmailByArtistId(int artistId) {
        String sql = "SELECT email FROM users WHERE user_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, artistId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //update artist bio
    public boolean updateArtistBio(int userId, String bio) {
        String sql = "UPDATE artist_profiles SET bio = ? WHERE artist_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, bio);
            st.setInt(2, userId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy Artist theo ID
    public Artist getArtistById(int id) {
        String sql = """
        SELECT ap.artist_id, ap.stage_name, ap.bio, ap.location,
               ap.rating_avg, ap.total_reviews,
               am.url AS image_url,
               asv.price
        FROM artist_profiles ap
        LEFT JOIN artist_media am 
               ON ap.artist_id = am.artist_id AND am.is_primary = 1
        LEFT JOIN artist_services asv 
               ON ap.artist_id = asv.artist_id
        WHERE ap.artist_id = ?
        """;

        Artist artist = null;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                artist = new Artist(
                        rs.getInt("artist_id"),
                        rs.getString("stage_name"),
                        rs.getString("bio"),
                        rs.getString("location"),
                        rs.getDouble("rating_avg"),
                        rs.getInt("total_reviews"),
                        rs.getString("image_url"),
                        rs.getDouble("price")
                );
            }

            // ✅ gọi ArtistMediaDAO để gắn mediaList
            if (artist != null) {
                ArtistMediaDAO mediaDAO = new ArtistMediaDAO();
                artist.setMediaList(mediaDAO.getMediaByArtistId(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artist;
    }
    // Lấy Artist theo userId

    public Artist getArtistByUserId(int userId) {
        String sql = """
    SELECT ap.artist_id, ap.stage_name, ap.bio, ap.location,
           ap.rating_avg, ap.total_reviews,
           am.url AS image_url,
           asv.price
    FROM artist_profiles ap
    LEFT JOIN artist_media am 
           ON ap.artist_id = am.artist_id AND am.is_primary = 1
    LEFT JOIN artist_services asv 
           ON ap.artist_id = asv.artist_id
    WHERE ap.artist_id = ?
    """;

        Artist artist = null;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                artist = new Artist(
                        rs.getInt("artist_id"),
                        rs.getString("stage_name"),
                        rs.getString("bio"),
                        rs.getString("location"),
                        rs.getDouble("rating_avg"),
                        rs.getInt("total_reviews"),
                        rs.getString("image_url"),
                        rs.getDouble("price")
                );
            }

            if (artist != null) {
                ArtistMediaDAO mediaDAO = new ArtistMediaDAO();
                artist.setMediaList(mediaDAO.getMediaByArtistId(artist.getId())); // ✅ đồng bộ tên method
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artist;
    }

    public boolean addArtist(Artist artist) {
        String sql = """
        INSERT INTO artist_profiles
        (artist_id, stage_name, bio, location, rating_avg, total_reviews, verification_status, verified_at)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try {
            connection.setAutoCommit(false); // bật transaction

            // Insert vào artist_profiles
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setInt(1, artist.getId());             // user_id từ bảng users
                st.setString(2, artist.getStageName());
                st.setString(3, artist.getBio());
                st.setString(4, artist.getLocation());
                st.setBigDecimal(5, new java.math.BigDecimal("0.00")); // rating_avg mặc định
                st.setInt(6, 0);                          // total_reviews = 0
                st.setString(7, "pending");               // trạng thái xác minh mặc định
                st.setTimestamp(8, null);                 // chưa xác minh
                st.executeUpdate();
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateVerificationStatus(int artistId, String status) {
        String sql = "UPDATE artist_profiles SET verification_status = ?, verified_at = NOW() WHERE artist_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, status); // "approved" hoặc "rejected"
            st.setInt(2, artistId);
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateArtist(Artist artist) {
        String sql = """
        UPDATE artist_profiles
        SET stage_name = ?, bio = ?, location = ?
        WHERE artist_id = ?
    """;
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, artist.getStageName());
            st.setString(2, artist.getBio());
            st.setString(3, artist.getLocation());
            st.setInt(4, artist.getId());
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsArtist(int userId) {
        String sql = "SELECT 1 FROM artist_profiles WHERE artist_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            return rs.next(); // nếu có record → đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {

        ArtistDAO dao = new ArtistDAO();

        Artist artist = new Artist();
        String bio = "My name is Tan";
        dao.updateArtistBio(6, bio);
        String real = dao.getEmailByArtistId(6);
        System.out.println(real);
    }

}
