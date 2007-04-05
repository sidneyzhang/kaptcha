/*
 * Created on Sep 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.google.code.kaptcha.text;

import java.awt.image.BufferedImage;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface CaptchaRenderer {
	
	public BufferedImage renderCaptcha (String word, int width, int height) ;

}
