package geoloc;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

/**
 * GeoLocation grep.
 * @author Infern0
 */
public class GeoLocation
{
	private static String[] russianSpeakingCountries = { "Russia", "Belarus", "Kazakhstan", "Moldova", "Kyrgyzstan", "Tajikistan", "Ukraine" };
	public static LookupService lookup = null;
	
	public static void load(File file)
	{
		try
		{
			lookup = new LookupService(file, LookupService.GEOIP_MEMORY_CACHE);
			lookup.close(); // Everything is cached, close the file.
		}
		catch (NullPointerException | IOException e)
		{
			JOptionPane.showMessageDialog(null, "I cannot find GeoDB.dat", "Missing database file.", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static boolean isFromRussia(String ip)
	{
		if (ip == null)
			return false;
		
		for (String count : russianSpeakingCountries)
		{
			if (count.equalsIgnoreCase(getCountry(ip)))
				return true;
		}
		
		return false;
		
	}
	
	public static String getCountryCode(String ip)
	{
		if (lookup == null)
			return "NULL";
		
		try
		{			
			
			return lookup.getLocation(ip) == null ? "Null" : lookup.getLocation(ip).countryCode;
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "getCountryCode: Error while getting GeoLocation data for IP: " + ip, "Error", JOptionPane.ERROR_MESSAGE);
			return "NULL";
		}
	}
	
	public static String getCountry(String ip)
	{
		if (lookup == null)
			return "NULL";
		
		try
		{			
			return lookup.getLocation(ip) == null ? "Null" : lookup.getLocation(ip).countryName;
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "getCountry: Error while getting GeoLocation data for IP: " + ip, "Error", JOptionPane.ERROR_MESSAGE);
			return "NULL";
		}
	}
	
	public static String getCity(String ip)
	{
		if (lookup == null)
			return "NULL";
		
		try
		{
			return lookup.getLocation(ip) == null ? "Null" : lookup.getLocation(ip).city;
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "getCity: Error while getting GeoLocation data for IP: " + ip, "Error", JOptionPane.ERROR_MESSAGE);
			return "NULL";
		}
	}
	
	
	public static String getCityRegion(String ip)
	{
		if (lookup == null)
			return "NULL";
		
		try
		{			
			return lookup.getLocation(ip) == null ? "Null" : lookup.getLocation(ip).region;
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "getCityRegion: Error while getting GeoLocation data for IP: " + ip, "Error", JOptionPane.ERROR_MESSAGE);
			return "NULL";
		}
	}
	
	public static Location getLocation(String ip)
	{
		if (lookup == null)
			return null;
		
		try
		{			
			return lookup.getLocation(ip);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "getLocation: Error while getting GeoLocation data for IP: " + ip, "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
}