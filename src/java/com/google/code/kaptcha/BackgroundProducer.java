package com.google.code.kaptcha;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * 
 */
public interface BackgroundProducer
{
	public static final String SIMPLE_CAPCHA_BCKGRND_CLR_FRM = "cap.background.c.from";
	public static final String SIMPLE_CAPCHA_BCKGRND_CLR_T = "cap.background.c.to";
	public static final String SIMPLE_CAPCHA_BCKGRND_CLR = "cap.background.c";
	public abstract void setProperties(Properties props);
	public abstract BufferedImage addBackground(BufferedImage image);
}