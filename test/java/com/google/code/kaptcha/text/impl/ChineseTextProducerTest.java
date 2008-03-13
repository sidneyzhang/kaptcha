package com.google.code.kaptcha.text.impl;

import junit.framework.TestCase;

/**
 * @author cliffano
 */
public class ChineseTextProducerTest extends TestCase
{
	public void testGetTextReturnsTextWithoutAlphabetChars()
	{
		ChineseTextProducer producer = new ChineseTextProducer();
		String text = producer.getText();
		assertNotNull(text);
		for (int i = 0; i < text.length(); i++)
		{
			char character = text.charAt(i);
			assertTrue(character < 'A' || character > 'z');
		}
	}
}
