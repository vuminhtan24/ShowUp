/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author VU MINH TAN
 */
import java.sql.*;
import java.util.*;
import model.ArtistVerificationDTO;
import java.sql.Timestamp;

public class ArtistVerificationDAO extends DBContext {

    // Lấy danh sách nghệ sĩ chờ duyệt
    public List<ArtistVerificationDTO> getPendingArtists() {
        List<ArtistVerificationDTO> list = new ArrayList<>();

        String sql = """
            SELECT 
                ap.artist_id,
                ap.stage_name,
                ap.verification_status,
                ap.verified_at,
                u.username,
                u.email,
                u.phone,
                (
                    SELECT url 
                    FROM artist_media 
                    WHERE artist_id = ap.artist_id AND is_primary = 1 
                    LIMIT 1
                ) AS avatar_url
            FROM artist_profiles ap
            JOIN users u ON ap.artist_id = u.u
            ORDER BY ap.verified_at DESC;
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ArtistVerificationDTO dto = new ArtistVerificationDTO();

                dto.setArtistId(rs.getInt("artist_id"));
                dto.setStageName(rs.getString("stage_name"));
                dto.setUsername(rs.getString("username"));
                dto.setEmail(rs.getString("email"));
                dto.setPhone(rs.getString("phone"));
                dto.setAvatarUrl(rs.getString("avatar_url"));
                dto.setVerificationStatus(rs.getString("verification_status"));
                dto.setVerified_at(rs.getTimestamp("verified_at"));

                // Gắn genres
                dto.setGenres(getGenresByArtistId(dto.getArtistId()));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy danh sách genres của nghệ sĩ
    public List<String> getGenresByArtistId(int artistId) {
        List<String> genres = new ArrayList<>();

        String sql = """
            SELECT g.name FROM genres g
            JOIN artist_genres ag ON g.genre_id = ag.genre_id
            WHERE ag.artist_id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, artistId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    genres.add(rs.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genres;
    }

    // Approve nghệ sĩ
    public boolean approveArtist(int artistId) {
        String sql = """
            UPDATE artist_profiles
            SET verification_status = 'approved',
                verified_at = NOW()
            WHERE artist_id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, artistId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Reject nghệ sĩ
    public boolean rejectArtist(int artistId) {
        String sql = """
            UPDATE artist_profiles
            SET verification_status = 'rejected'
            WHERE artist_id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, artistId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Lấy chi tiết đầy đủ 1 nghệ sĩ
    public ArtistVerificationDTO getArtistById(int artistId) {
        ArtistVerificationDTO dto = null;

        String sql = """
            SELECT 
                ap.artist_id,
                ap.stage_name,
                ap.verification_status,
                ap.verified_at,
                u.username,
                u.email,
                u.phone,
                (
                    SELECT url 
                    FROM artist_media 
                    WHERE artist_id = ap.artist_id AND is_primary = 1 
                    LIMIT 1
                ) AS avatar_url
            FROM artist_profiles ap
            JOIN users u ON ap.artist_id = u.user_id
            WHERE ap.artist_id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, artistId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new ArtistVerificationDTO();

                    dto.setArtistId(rs.getInt("artist_id"));
                    dto.setStageName(rs.getString("stage_name"));
                    dto.setUsername(rs.getString("username"));
                    dto.setEmail(rs.getString("email"));
                    dto.setPhone(rs.getString("phone"));
                    dto.setAvatarUrl(rs.getString("avatar_url"));
                    dto.setVerificationStatus(rs.getString("verification_status"));
                    dto.setVerified_at(rs.getTimestamp("verified_at"));
                    dto.setGenres(getGenresByArtistId(artistId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    public List<ArtistVerificationDTO> searchArtists(String keyword) {
        List<ArtistVerificationDTO> list = new ArrayList<>();

        String sql = """
        SELECT ap.artist_id, ap.stage_name, u.username, u.email, u.phone,
               ap.verification_status, ap.verified_at
        FROM artist_profiles ap
        JOIN users u ON ap.artist_id = u.user_id
        WHERE 
            ap.stage_name LIKE ? 
            OR u.username LIKE ?
            OR u.email LIKE ?
    """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            String searchKey = "%" + keyword + "%";
            stm.setString(1, searchKey);
            stm.setString(2, searchKey);
            stm.setString(3, searchKey);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                ArtistVerificationDTO a = new ArtistVerificationDTO();
                a.setArtistId(rs.getInt("artist_id"));
                a.setStageName(rs.getString("stage_name"));
                a.setUsername(rs.getString("username"));
                a.setEmail(rs.getString("email"));
                a.setPhone(rs.getString("phone"));

                a.setVerificationStatus(rs.getString("verification_status"));
                a.setVerified_at(rs.getTimestamp("verified_at"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {

        ArtistVerificationDAO dao = new ArtistVerificationDAO();

        System.out.println(dao.searchArtists("a"));
    }
}
