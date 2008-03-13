package com.google.code.kaptcha.text.impl;

import junit.framework.TestCase;

/**
 * @author cliffano
 */
public class FiveLetterFirstNameTextCreatorTest extends TestCase
{
	public void testGetTextGivesTextWithFiveLetters()
	{
		FiveLetterFirstNameTextCreator creator = new FiveLetterFirstNameTextCreator();
		assertEquals(5, creator.getText().length());
	}
}
