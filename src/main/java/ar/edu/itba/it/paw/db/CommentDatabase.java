package ar.edu.itba.it.paw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.itba.it.paw.model.Comment;

public class CommentDatabase extends Database<Comment> {
	
	private static final int USER_NAME = 1;
	private static final int USER_ID = 1;
	private static final int RESTAURANT_ID = 2;
	private static final int RATING = 3;
	private static final int TEXT = 4;
	
	public void addComment(Comment comment) {
		insert("insert into comments values (?, ?, ?, ?)", comment);
	}
	
	public List<Comment> getRestaurantComments(long restaurantId) {
		return doListQuery("select firstname, restaurantid, rating, text from comments, users "
				+ "where restaurantid=" + restaurantId
				+ " and userid=users.id");
	}
	
	public Comment getUserComment(long userId, long restaurantId) {
		return doQuery("select firstname, restaurantid, rating, text from comments, users "
				+ "where userid=" + userId 
				+ " and restaurantid=" + restaurantId
				+ " and userid=users.id");
	}
	
	public void deleteComment(long userId, long restaurantId) {
		delete("delete from comments "
				+ "where userid=" + userId 
				+ " and restaurantid=" + restaurantId);
	}
	
	public float getRatingAverage(long restaurantId) {
		Float avg = doGetFloatQuery("select avg(rating) from comments "
				+ "where restaurantid=" + restaurantId);
		return avg == null ? 0 : avg;
	}
	
	@Override
	protected Comment generate(ResultSet rs) throws SQLException {
		return new Comment(
				rs.getString(USER_NAME),
				rs.getLong(RESTAURANT_ID),
				rs.getInt(RATING),
				rs.getString(TEXT));
	}

	@Override
	protected void storeData(PreparedStatement pst, Comment comment) throws SQLException {
		pst.setLong(USER_ID, comment.getUserId());
		pst.setLong(RESTAURANT_ID, comment.getRestaurantId());
		pst.setInt(RATING, comment.getRating());
		pst.setString(TEXT, comment.getText());
	}
}
