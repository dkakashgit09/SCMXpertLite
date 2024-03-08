package com.kafka.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("application.properties")
public class SendMessage
{
	private Socket socket;
	private BufferedReader input;

	@Value("${socket.host}")
	private String host;

	@Value("${socket.port}")
	private String port;

	@Autowired
	private DataProducer  producer;

	public void sendMessageToTopic()
	{
		try 
		{
			System.out.println(host+"     "+port);
			socket = new Socket(host, Integer.parseInt(port));
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
	        while ((line = input.readLine()) != null) 
	        {
	            producer.sendMessage(line);
	        }
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
            System.out.println("Unknown host: " + host);
		}
		catch (IOException  e) 
		{
			e.printStackTrace();
            System.out.println("Error reading from socket");
		}
		finally
		{
			try 
			{
                if (input != null)
                {
                	input.close();
                }
                
                if (socket != null)
                {
                	socket.close();
                }
            } 
			catch (IOException e) 
			{
                e.printStackTrace();
            }
		}
	}
}
