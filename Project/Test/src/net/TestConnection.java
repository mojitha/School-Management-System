package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TestConnection {
    public static void main(String[] args) {
        try {
            URL myURL = new URL("http://www.google.com");
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            System.out.println("Internet is connected");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(myURL.openStream()));
            String sysip = br.readLine().trim();
            
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("System IP address: "+localhost.getHostAddress().trim());
            
            System.out.println("Public IP address: "+sysip);
        }
        catch(MalformedURLException murle) {
            System.out.println(murle);
        } 
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}
