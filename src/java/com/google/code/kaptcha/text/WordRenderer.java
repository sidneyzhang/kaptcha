package com.google.code.kaptcha.text;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 *
 */
public interface WordRenderer
{
	/** 
	 * Render a word to a BufferedImage.
	 * 
	 * @param word The word to be rendered.
	 * @param width The width of the image to be created.
	 * @param height The height of the image to be created.
	 * @return The BufferedImage created from the word,
	 */
	public abstract BufferedImage renderWord(String word, int width, int height);

	/**
	 * @param properties
	 */
	public abstract void setProperties(Properties properties);
}
