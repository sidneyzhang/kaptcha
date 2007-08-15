<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Kaptcha Example</title>
	</head>
	<body>
	
		<br><br><br><br>
		
		<%
			String c= (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			String parm = (String) request.getParameter("kaptchafield");
			
			out.println(parm + " ? " + c + ":");
			
			if (c != null && parm != null) {
				if (c.equals(parm)) {
					out.println("<b>true</b>");
				} else {
					out.println("<b>false</b>");
				}
			}
		%>
		
		<br>
		
		<table>
			<tr>
				<td><img src="Kaptcha.jpg"></td>
				<td valign="top">
			
					<form>
						<br>sec code:<input type="text" name="kaptchafield"><br>
						<input type="submit" name="submit">
					</form>
				</td>
			</tr>
		</table>	

	</body>
</html>
