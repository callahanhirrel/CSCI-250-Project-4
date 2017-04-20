package database;

import static org.junit.Assert.*;

//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

//import java.util.ArrayList;
//import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class DBConstructorTest {
	
	DBConstructor db;
	
	@Before
	public void setup() throws ClassNotFoundException {
		db = new DBConstructor();
		db.init();
	}
	
	@Test
	public void test() {
		String[] fields = {"ID"};
		String[] types = {"integer"};
		assertEquals("ID integer", db.buildFields(fields, types));
	}
	
	@Test
	public void test2() {
		String[] fields = {"ID", "Project"};
		String[] types = {"integer", "string"};
		System.out.println(db.buildFields(fields, types));
		assertEquals("ID integer, Project string", db.buildFields(fields, types));
	}
	
	@Test
	public void test3() {
		String[] data = {"1"};
		List<?> ls = Arrays.asList(data);
		assertEquals("1", db.buildData(ls));
	}
	
	@Test
	public void test4() {
		String[] data = {"1", "'DataBase'"};
		List<?> ls = Arrays.asList(data);
		assertEquals("1, 'DataBase'", db.buildData(ls));
	}
	
//	@Test
//	public void test5() throws SQLException {
//		String[] fields = {"ID", "Project"};
//		String[] types = {"integer", "string"};
//		String[] data = {"1", "'GuitarTab'"};
//		String[] data1 = {"2", "'PeerToPeer'"};
//		String[] data2 = {"3", "'DataBase'"};
//		List<?> ls = Arrays.asList(data2);
//		System.out.println(ls);
// 		String tablename = "test";
//		Connection con = db.connectDB();
//		Statement stat = db.editDB(con);
//		db.createTable(stat, tablename, fields, types);
//		db.insertData(stat, tablename, data);
//		db.insertData(stat, tablename, data1);
//		db.insertData(stat, tablename, data2);
//		db.closeDB(con);
//	}
	
	@Test
	public void test6() {
		String[] list = {"1", "2", "3"};
		List<?> ls = Arrays.asList(list);
//		String[] x = (String[]) ls.toArray();
		System.out.println(ls);
	}
	
}
