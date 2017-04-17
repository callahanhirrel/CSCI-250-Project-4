package database;

import static org.junit.Assert.*;

import java.util.ArrayList;
//import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class DBConstructorTest {
	
	DBConstructor db;
	ArrayList<String> fields;
	ArrayList<String> types;
	
	@Before
	public void setup() {
		db = new DBConstructor();
		fields = new ArrayList<String>();
		types = new ArrayList<String>();
	}
	
	@Test
	public void test() {
		fields.add("ID");
		types.add("integer");
		assertEquals("ID integer", db.buildFields(fields, types));
	}
	
	@Test
	public void test2() {
		fields.add("ID");
		fields.add("Project");
		types.add("integer");
		types.add("string");
		System.out.println(db.buildFields(fields, types));
		assertEquals("ID integer, Project string", db.buildFields(fields, types));
	}
	
//	@Test
//	public void test3() {
//		fields = {"1", "2"};
//		System.out.println(fields);
//	}
}
