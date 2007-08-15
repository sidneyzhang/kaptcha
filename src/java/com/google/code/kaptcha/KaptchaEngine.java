package com.google.code.kaptcha;

import java.awt.image.BufferedImage;

/**
 * 
 */
public interface KaptchaEngine
{
	public abstract BufferedImage getDistortedImage(BufferedImage image);
}
