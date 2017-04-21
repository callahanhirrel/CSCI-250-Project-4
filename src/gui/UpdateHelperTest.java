package gui;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

//import database.DBConstructor;

public class UpdateHelperTest {

	UpdateHelper helper;
	
	@Before
	public void setup() {
		helper = new UpdateHelper();
	}
	
	@Test
	public void test() {
		List<String> x = helper.hourPickerCreator();
//		x = Arrays.asList("");
		List<String> y = helper.minPickerCreator();
		System.out.println(y);
		assertEquals(x, helper.hourPickerCreator());
	}

}
