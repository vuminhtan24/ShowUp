package dal;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ArtistMedia;

public class ArtistMediaDAO extends DBContext {

    // Lấy tất cả media của một Artist
    public List<ArtistMedia> getMediaByArtistId(int artistId) {
        List<ArtistMedia> list = new ArrayList<>();
        String sql = "SELECT media_id, url, media_type FROM artist_media WHERE artist_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, artistId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new ArtistMedia(
                        rs.getInt("media_id"),
                        rs.getString("url"),
                        rs.getString("media_type")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    // Thêm media mới cho nghệ sĩ

    public boolean insertMedia(int artistId, String url, String mediaType) {
        String sql = "INSERT INTO artist_media (artist_id, url, media_type) VALUES (?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, artistId);
            st.setString(2, url);
            st.setString(3, mediaType);
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMedia(int mediaId) {
        String sql = "DELETE FROM artist_media WHERE media_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, mediaId);
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        ArtistMediaDAO dao = new ArtistMediaDAO();
        System.out.println(dao.getMediaByArtistId(6));
    }

}
