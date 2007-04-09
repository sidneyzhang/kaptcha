package com.google.code.kaptcha.text;

import java.awt.image.BufferedImage;

/**
 * 
 */
public interface CaptchaRenderer
{	
	public BufferedImage renderCaptcha (String word, int width, int height);
}
