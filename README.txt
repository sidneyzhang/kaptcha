kaptcha - A kaptcha generation engine clone of http://code.google.com/p/kaptcha/.

Goals:

  - Make it Maven based
  - Allow use it from Spring

I'v tried to use:

  * JCaptcha (http://jcaptcha.sourceforge.net/)
    - Bad community responds (bad API).
    
  * SimpleCaptcha (http://simplecaptcha.sourceforge.net/)
    - Not maven based
    - Binary instead of source code (imaging.jar represents jhlabs of 2000, Internet gives me only latest
      com.jhlabs.filters:2.0.235 version)
      
  * ReCaptcha (http://www.google.com/recaptcha)
    - Hard to read

and decided to make my own fork...
