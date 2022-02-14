package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AppTest {
	/**
	 * Static method run before everything else
	 */
	@BeforeAll
	static void setUpBeforeAll() {
	}

	/**
	 * Setup method called before each of the test methods
	 */
	@BeforeEach
	void setUpBeforeEach() {
	}

	/**
	 * Simple test case
	 */
	@Test
	void dummy1() {
		// Expected result is the first argument, value to be tested is the second.
		// The message is optional.
		assertEquals("foo", "f".concat("oo"), "fooo?");
	}

	/**
	 * Simple test case
	 */
	@Test
	void dummy2() {
		// For floats and doubles it's best to use assertEquals with a delta, since
		// floating-point numbers are imprecise
		float a = 100000;
		a = a + 0.1f;
		assertEquals(100000.1, a, 0.01);
	}

	/**
	 * Parameterized test case, reading arguments from comma-separated strings
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	@CsvSource(value = { "1,1,2", "1,2,3", "2,3,5", "3,5,8", "5,8,13", "8,13,21" })
	@ParameterizedTest(name = "{0}+{1} == {2}")
	void addTest(int a, int b, int c) {
		assertEquals(c, a + b);
	}
}