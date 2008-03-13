package com.google.code.kaptcha.util;

import junit.framework.TestCase;

/**
 * @author cliffano
 */
public class ConfigExceptionTest extends TestCase
{
	public void testConstructorWithThrowableCause()
	{
		ConfigException ce = new ConfigException("createdby",
				"David E. Kelley", new IllegalArgumentException());
		assertEquals(
				"Invalid value 'David E. Kelley' for config parameter 'createdby'.",
				ce.getMessage());
		assertTrue(ce.getCause() instanceof IllegalArgumentException);
	}

	public void testConstructorWithMessage()
	{
		ConfigException ce = new ConfigException("createdby",
				"David E. Kelley", "Roster injury list.");
		assertEquals(
				"Invalid value 'David E. Kelley' for config parameter 'createdby'. Roster injury list.",
				ce.getMessage());
	}
}
