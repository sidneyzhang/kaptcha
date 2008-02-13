package com.google.code.kaptcha.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.ConfigManager;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * This servlet uses the settings passed into it via the Producer api.
 * 
 * @author testvoogd@hotmail.com
 */
@SuppressWarnings("serial")
public class KaptchaServlet extends HttpServlet implements Servlet
{
	private Properties props = new Properties();

	private Producer captchaProducer = null;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{

		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache, no-store");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");

		// this key can be read from any controller to check whether user
		// is a computer or human..
		String capText = captchaProducer.createText();
		req.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

		// notice we don't store the captext in the producer. This is because
		// the thing is not thread safe and we do use the producer as an
		// instance
		// variable in the servlet.
		BufferedImage bi = captchaProducer.createImage(capText);

		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(resp
				.getOutputStream());
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
		param.setQuality(1f, true);
		encoder.encode(bi, param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);

		Enumeration<?> initParams = conf.getInitParameterNames();
		while (initParams.hasMoreElements())
		{
			String key = (String) initParams.nextElement();
			String value = conf.getInitParameter(key);
			props.put(key, value);
		}

		ConfigManager configManager = new ConfigManager(props);
		this.captchaProducer = (Producer) configManager.getProducerImpl();
	}
}
