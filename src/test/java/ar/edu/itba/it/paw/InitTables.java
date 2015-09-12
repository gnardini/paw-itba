package ar.edu.itba.it.paw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ar.edu.itba.it.paw.db.Database;

public class InitTables extends Database<String> {

	public InitTables() {
		try {
			Scanner scanner = new Scanner(System.in);
			StringBuilder sb = new StringBuilder();
			String next = scanner.nextLine();
			while (!"end".equals(next)) {
				sb.append(next);
				next = scanner.nextLine();
			}
			scanner.close();
			System.out.println(sb.toString());
			mDbConnection.createStatement().execute(sb.toString());
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new InitTables();
	}

	@Override protected String generate(ResultSet rs) throws SQLException { return null; }
	@Override protected void storeData(PreparedStatement pst, String elem) throws SQLException { }
	@Override protected void updateData(PreparedStatement pst, String elem) throws SQLException { }
}
