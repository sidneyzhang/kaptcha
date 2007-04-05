package com.google.code.kaptcha.text;

import java.util.Properties;

/**
 * 
 */
public interface TextProducer
{
	public void setProperties(Properties props);
	public abstract String getText();
}