package com.google.code.kaptcha;

import java.awt.image.BufferedImage;

/**
 * 
 */
public interface CaptchaEngine
{
	public abstract BufferedImage getDistortedImage(BufferedImage image);
}
