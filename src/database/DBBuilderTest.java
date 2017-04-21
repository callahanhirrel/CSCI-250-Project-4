package database;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

public class DBBuilderTest {
	
	DBBuilder db;
	Statement stat;
	ResultSet resultSet;
	
	@Before
	public void setup() throws SQLException, ClassNotFoundException {
		db = new DBBuilder();
	}

	@Test
	public void createTableTest() throws SQLException {
		db.removeTable("TEST");
		db.removeTable("DTrump");
		db.addTable("test");
		db.addTable("DTrump");
		assertTrue(db.isTable("TEST"));
		assertFalse(db.isTable("NCage"));
		assertTrue(db.isTable("DTrump"));
		db.removeTable("TEST");
		db.removeTable("DTrump");
	}
	/*
	public void modifyScheduleTest() throws SQLException {
		createTableTest();
		db.modifySchedule("Monday", 8, "BUSY");
		resultSet = stat.executeQuery(arg0)
	}
	*/

}
