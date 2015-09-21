package ar.edu.itba.it.paw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.itba.it.paw.model.Comment;

public class CommentDatabase extends Database<Comment> {
	
	private static final int USER_ID = 1;
	private static final int USER_NAME = 2;
	private static final int USER_LAST_NAME = 3;
	private static final int RESTAURANT_ID = 4;
	private static final int RATING = 5;
	private static final int TEXT = 6;
	
	public void addComment(Comment comment) {
		insert("insert into comments values (?, ?, ?, ?)", comment);
	}
	
	public List<Comment> getRestaurantComments(long restaurantId) {
		return doListQuery("select userid, firstname, lastname, restaurantid, rating, text from comments, users "
				+ "where restaurantid=" + restaurantId
				+ " and userid=users.id");
	}
	
	public Comment getUserComment(long userId, long restaurantId) {
		return doQuery("select userid, firstname, lastname, restaurantid, rating, text from comments, users "
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
				rs.getLong(USER_ID),
				rs.getString(USER_NAME)+" "+rs.getString(USER_LAST_NAME),
				rs.getLong(RESTAURANT_ID),
				rs.getInt(RATING),
				rs.getString(TEXT));
	}

	@Override
	protected void storeData(PreparedStatement pst, Comment comment) throws SQLException {
		pst.setLong(USER_ID, comment.getUserId());
		pst.setLong(RESTAURANT_ID - 1, comment.getRestaurantId());
		pst.setInt(RATING - 1, comment.getRating());
		pst.setString(TEXT - 1, comment.getText());
	}
}
