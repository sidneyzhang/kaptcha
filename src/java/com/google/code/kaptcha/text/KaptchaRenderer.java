package com.google.code.kaptcha.text;

import java.awt.image.BufferedImage;

/**
 * 
 */
public interface KaptchaRenderer
{	
	public BufferedImage renderCaptcha (String word, int width, int height);
}
