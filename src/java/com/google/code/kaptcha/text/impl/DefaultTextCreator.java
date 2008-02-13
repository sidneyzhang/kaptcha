package com.google.code.kaptcha.text.impl;

import java.util.Random;

import com.google.code.kaptcha.Configurable;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.ConfigManager;

/**
 * {@link DefaultTextCreator} creates random text from an array of characters
 * with specified length.
 */
public class DefaultTextCreator implements TextProducer, Configurable
{
	private ConfigManager configManager;

	/**
	 * @return the random text
	 */
	public String getText()
	{
		int length = configManager.getTextProducerCharLength();
		char[] chars = configManager.getTextProducerCharString();
		int randomContext = chars.length - 1;
		Random rand = new Random();
		StringBuffer text = new StringBuffer();
		for (int i = 0; i < length; i++)
		{
			text.append(chars[rand.nextInt(randomContext) + 1]);
		}

		return text.toString();
	}

	public void setConfigManager(ConfigManager configManager)
	{
		this.configManager = configManager;
	}
}
