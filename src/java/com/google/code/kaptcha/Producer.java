package com.google.code.kaptcha;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.text.WordRenderer;

/**
 * 
 */
public interface Producer
{
	/**
	 * Create an image which will have written a distorted text with text given 
	 * as parameter. The resulting image is put out on the output stream
	 * 
	 * @param stream the OutputStream where the image is written
	 * @param text the distorted characters written on image
	 * @throws IOException if an error occurs during the image written on
	 * output stream.
	 */
	public abstract void createImage(OutputStream stream, String text) throws IOException;

	public abstract void setBackGroundImageProducer(BackgroundProducer background);

	public abstract void setObscurificator(GimpyEngine engine);

	/**
	 * @param properties
	 */
	public abstract void setProperties(Properties properties);

	public abstract void setTextProducer(TextProducer textP);

	public abstract String createText();

	public abstract void setWordRenderer(WordRenderer renederer);
}