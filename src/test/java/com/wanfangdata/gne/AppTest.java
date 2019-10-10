package com.wanfangdata.gne;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    
    
   
    public void testPattern() {
    	String result = "上观";
    	if(Pattern.matches("[\\u4E00-\\u9FA5]{2,6}", result)) {
			System.out.println(result);
		}
    }
    @Test
    public void testURI() {
    	String url = "http://www.baidu.com/aaa/";
    	URI uri;
		try {
			uri = new URI(url);
			String domain = uri.getScheme() +"://"+ uri.getHost() + (uri.getPort()==-1?"":uri.getPort());
			System.out.println(domain);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
