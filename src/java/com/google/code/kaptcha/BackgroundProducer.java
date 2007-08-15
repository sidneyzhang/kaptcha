package com.google.code.kaptcha;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * 
 */
public interface BackgroundProducer
{
	public abstract void setProperties(Properties props);
	public abstract BufferedImage addBackground(BufferedImage image);
}
