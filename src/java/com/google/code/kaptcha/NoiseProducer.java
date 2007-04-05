package com.google.code.kaptcha;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * 
 */
public interface NoiseProducer
{
	public abstract void setProperties(Properties props);

	public abstract void makeNoise(BufferedImage image, float factorOne, float factorTwo, float factorThree,
									float factorFour);
}