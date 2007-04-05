/*
 * Created on Sep 15, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.google.code.kaptcha.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.Random;

import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.servlet.Constants;
import com.google.code.kaptcha.util.Helper;

/**
 *
 */
public class DefaultNoiseImp implements NoiseProducer
{

	private Properties props = null;

	public DefaultNoiseImp(Properties props)
	{
		this.props = props;
	}

	public void setProperties(Properties props)
	{
		this.props = props;
	}

	public void makeNoise(BufferedImage image, float factorOne, float factorTwo, float factorThree, float factorFour)
	{
		//image size
		int width = image.getWidth();
		int height = image.getHeight();

		//the points where the line changes the stroke and direction
		Point2D[] pts = null;
		Random rand = new Random();

		//the curve from where the points are taken
		CubicCurve2D cc =
			new CubicCurve2D.Float(width * factorOne, height * rand.nextFloat(), width * factorTwo, height
							* rand.nextFloat(), width * factorThree, height * rand.nextFloat(), width * factorFour,
									height * rand.nextFloat());

		// creates an iterator to define the boundary of the flattened curve 
		PathIterator pi = cc.getPathIterator(null, 2);
		Point2D tmp[] = new Point2D[200];
		int i = 0;

		// while pi is iterating the curve, adds points to tmp array 
		while (!pi.isDone())
		{
			float[] coords = new float[6];
			switch (pi.currentSegment(coords))
			{
				case PathIterator.SEG_MOVETO:
				case PathIterator.SEG_LINETO:
					tmp[i] = new Point2D.Float(coords[0], coords[1]);
			}
			i++;
			pi.next();
		}

		pts = new Point2D[i];
		// copies points from tmp to pts 
		System.arraycopy(tmp, 0, pts, 0, i);

		Graphics2D graph = (Graphics2D)image.getGraphics();
		graph.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

		Color mycol = Helper.getColor(props, Constants.SIMPLE_CAPTCHA_NOISE_COLOR, Color.black);

		graph.setColor(mycol);

		//for the maximum 3 point change the stroke and direction 
		for (i = 0; i < pts.length - 1; i++)
		{
			if (i < 3)
				graph.setStroke(new BasicStroke(0.9f * (4 - i)));
			graph.drawLine((int)pts[i].getX(), (int)pts[i].getY(), (int)pts[i + 1].getX(), (int)pts[i + 1].getY());
		}

		graph.dispose();
	}
}
