/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import model.Performance;

/**
 *
 * @author VU MINH TAN
 */
public class PerformanceDAO extends DBContext{
    public List<Performance> getPerformancesByArtist(int artistId) {
    List<Performance> list = new ArrayList<>();
    String sql = "SELECT * FROM artist_performances WHERE artist_id = ? AND is_active = 1";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        ResultSet rs = st.executeQuery();
        st.setInt(1, artistId);
        while (rs.next()) {
            Performance p = new Performance();
            p.setPerformanceId(rs.getInt("performance_id"));
            p.setArtistId(rs.getInt("artist_id"));
            p.setTitle(rs.getString("title"));
            p.setDescription(rs.getString("description"));
            p.setVideoUrl(rs.getString("video_url"));
            p.setThumbnailUrl(rs.getString("thumbnail_url"));
            p.setPrice(rs.getDouble("price"));
            list.add(p);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
    public static void main(String[] args) {
        PerformanceDAO per = new PerformanceDAO();
        System.out.println(per);
    }
}
