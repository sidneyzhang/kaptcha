package com.google.code.kaptcha.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.CaptchaProducer;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.text.WordRenederer;
import com.google.code.kaptcha.util.Helper;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author testvoogd@hotmail.com
 */
public class DefaultCaptcha implements CaptchaProducer
{
	private Properties props = null;
	private boolean bbox = true;
	private Color boxColor = Color.black;
	private int boxThick = 1;
	private WordRenederer wordRenderer = null;
	private GimpyEngine gimpy = null;
	private BackgroundProducer backGroundImp = null;
	private TextProducer textProducer = null;

	public DefaultCaptcha(Properties props)
	{
		this.props = props;
		if (this.props != null)
		{
			//doing some init stuff.
			String box = props.getProperty(Constants.CAPTCHA_BOX);
			if (box != null && !box.equals("no"))
			{
				this.bbox = true;
			}
			else
			{
				this.bbox = false;
			}

			if (bbox)
			{
				boxColor = Helper.getColor(this.props, Constants.CAPTCHA_BOX_C, Color.black);
				boxThick = Helper.getIntegerFromString(props, Constants.CAPTCHA_BOX_TH);
				if (boxThick == 0)
					boxThick = 1;
			}
//			String wordR = props.getProperty(Constants.SIMPLE_CAPTCHA_WORDRENERER);

			this.gimpy = (GimpyEngine)Helper.ThingFactory.loadImpl(Helper.ThingFactory.OBSIMP, props);
			this.backGroundImp = (BackgroundProducer)Helper.ThingFactory.loadImpl(Helper.ThingFactory.BGIMP, props);
			this.wordRenderer = (WordRenederer)Helper.ThingFactory.loadImpl(Helper.ThingFactory.WRDREN, props);
			this.textProducer = (TextProducer)Helper.ThingFactory.loadImpl(Helper.ThingFactory.TXTPRDO, props);
		}
	}

	/**
	 *  The width image in pixels. 
	 */
	private int w = 200;

	/**
	 * The heigth image in pixels. 
	 */
	private int h = 50;

	/**
	 * Create an image which have witten a distorted text, text given 
	 * as parameter. The result image is put on the output stream
	 * 
	 * @param stream the OutputStrea where the image is written
	 * @param text the distorted characters written on imagage
	 * @throws IOException if an error occurs during the image written on
	 * output stream.
	 */
	public void createImage(OutputStream stream, String text) throws IOException
	{

		//create an JPEG encoder
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(stream);

		//put the text on the image
		BufferedImage bi = wordRenderer.renderWord(text, w, h);

		//create a new distorted (wound version of) the image
		gimpy.setProperties(props);
		bi = gimpy.getDistortedImage(bi);

		//add a background to the image
		bi = this.backGroundImp.addBackground(bi);
		//bi = addBackground(bi);       	

		//get the graphics of the image
		Graphics2D graphics = bi.createGraphics();

		if (bbox)
			drawBox(graphics);

		//encode the image to jpeg format 

		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
		param.setQuality(1f, true);
		encoder.encode(bi, param);

		//encoder.encode(bi);		
	}

	/**
	 * Rotate an image from it's center. 
	 *
	 * @param The image to be rotated.
	 * @return The rotated image.
	 */
//	private static BufferedImage rotate(BufferedImage image)
//	{
//
//		int width = image.getWidth();
//		int height = image.getHeight();
//
//		//create a clean transparent image
//		BufferedImage transform = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//		Graphics2D g2Dx = (Graphics2D)transform.getGraphics();
//		AffineTransform xform = g2Dx.getTransform();
//		g2Dx.setBackground(Color.white);
//		g2Dx.setColor(Color.white);
//		int xRot = width / 2;
//		int yRot = height / 2;
//
//		Random rand = new Random();
//
//		// generate an angle between 5 and -5 degrees.
//		int angle = rand.nextInt(5) + 2;
//
//		int ori = rand.nextInt(2);
//
//		if (ori < 1)
//			angle = angle * -1;
//
//		//rotate the image
//		xform.rotate(Math.toRadians(angle), xRot, yRot);
//
//		g2Dx.setTransform(xform);
//		g2Dx.drawImage(image, 0, 0, null, null);
//
//		return transform;
//	}

	private void drawBox(Graphics2D graphics)
	{

		//#d0 a0 3f; 

		graphics.setColor(this.boxColor);

		if (this.boxThick != 1)
		{
			BasicStroke stroke = new BasicStroke((float)boxThick);
			graphics.setStroke(stroke);
		}

		Line2D d2 = new Line2D.Double((double)0, (double)0, (double)0, (double)w);
		graphics.draw(d2);

		Line2D d3 = new Line2D.Double((double)0, (double)0, (double)w, (double)0);
		graphics.draw(d3);

		d3 = new Line2D.Double((double)0, (double)h - 1, (double)w, (double)h - 1);
		graphics.draw(d3);

		d3 = new Line2D.Double((double)w - 1, (double)h - 1, (double)w - 1, (double)0);

		graphics.draw(d3);

	}

	public void setBackGroundImageProducer(BackgroundProducer background)
	{
		// TODO Auto-generated method stub

	}

	public void setObscurificator()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @return the properties
	 */
	public Properties getProperties()
	{
		return props;
	}

	/**
	 * @param properties
	 */
	public void setProperties(Properties properties)
	{
		props = properties;
	}

	/**
	 * 
	 */
	public void setObscurificator(GimpyEngine engine)
	{
		this.gimpy = engine;

	}

	/**
	 * 
	 */
	public void setTextProducer(TextProducer textP)
	{
		// TODO Auto-generated method stub
	}

	public String createText()
	{
		String capText = textProducer.getText();
		return capText;
	}

	/**
	 * @param renederer
	 */
	public void setWordRenderer(WordRenederer renederer)
	{
		wordRenderer = renederer;
	}

}
