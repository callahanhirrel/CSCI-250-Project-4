package database;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class DBBuilderTest {
	
	DBBuilder db;
	
	@Before
	public void setup() throws SQLException, ClassNotFoundException {
		db = new DBBuilder();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
