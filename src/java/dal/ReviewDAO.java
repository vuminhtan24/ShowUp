/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Review;

/**
 *
 * @author VU MINH TAN
 */
public class ReviewDAO extends DBContext {

    public List<Review> getReviewsByArtist(int artistId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE artist_id = ? ORDER BY created_at DESC";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            st.setInt(1, artistId);
            while (rs.next()) {
                Review r = new Review();
                r.setReviewId(rs.getInt("review_id"));
                r.setArtistId(rs.getInt("artist_id"));
                r.setRating(rs.getInt("rating"));
                r.setComment(rs.getString("comment"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        ReviewDAO review = new ReviewDAO();
        System.out.println(review);
    }
}
