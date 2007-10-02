package com.google.code.kaptcha;

import java.awt.image.BufferedImage;

/**
 * 
 */
public interface Engine
{
	public BufferedImage getDistortedImage(BufferedImage image);
}
