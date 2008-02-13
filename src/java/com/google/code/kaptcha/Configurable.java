package com.google.code.kaptcha;

import com.google.code.kaptcha.util.ConfigManager;

/**
 * This interface determines if a class can be configured by properties
 * handled by config manager.
 */
public interface Configurable
{
    public void setConfigManager(ConfigManager configManager);
}
