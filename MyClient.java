package latest.udp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
 
public class MyClient extends Thread{
	

	Scanner sc = null;
	String input = null;
	String output = null;
    BufferedReader reader = null;
    DatagramSocket clientSocket = null;
    
	public void run(){
		System.out.println("enter port number to connect servers");
    	int ports[]=new int[4];
    	Scanner sc=new Scanner(System.in);
    	for(int i=0;i<4;i++){
    		ports[i]=sc.nextInt();
    	}
            
        try
        {
        for(int i=0;i<4;i++){
    	     clientSocket = new DatagramSocket();
    	        reader = new BufferedReader(new InputStreamReader(System.in));
    	        System.out.print("Enter Message to Server"+(i+1) +":");
 		while(!(input = reader.readLine()).equals("stop")){   	    	
      			byte[] inputData = input.getBytes();
    	        DatagramPacket  inPacket = new DatagramPacket(inputData , inputData.length ,InetAddress.getByName("localhost") ,ports[i] );
    	        clientSocket.send(inPacket);
    	        
    	        byte[] bufferedData = new byte[1024];
    	        DatagramPacket response = new DatagramPacket(bufferedData, bufferedData.length);
    	        clientSocket.receive(response);
    	        byte[] outputData = response.getData();
    	        output = new String(outputData,0,response.getLength());
    	        System.out.println(response.getAddress().getHostAddress() + " : " + response.getPort() + " - " + output);
    	    	}
		}
        }catch(IOException e)
        {
            System.out.println("connection terminated in client");
        }finally{
        	sc.close();
		for(int i=0;i<4;i++){
    			try {
					clientSocket.send(new DatagramPacket(new String("stop").getBytes() , new String("stop").getBytes().length ,InetAddress.getByName("localhost") ,ports[i] ));
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
        }
            
    }
 
    public void client(int port){
    	
    	try {

    	clientSocket = new DatagramSocket();
        reader = new BufferedReader(new InputStreamReader(System.in));
    	input = reader.readLine();
    	System.out.println("---"+input);
		byte[] inputData = input.getBytes();
        DatagramPacket  inPacket = new DatagramPacket(inputData , inputData.length ,InetAddress.getByName("localhost") ,port );
        clientSocket.send(inPacket);
        
        byte[] bufferedData = new byte[1024];
        DatagramPacket response = new DatagramPacket(bufferedData, bufferedData.length);
        clientSocket.receive(response);
        
        
        byte[] outputData = response.getData();
        output = new String(outputData);
        System.out.println(response.getAddress().getHostAddress() + " : " + response.getPort() + " - " + output);
    	}catch(IOException e)
        {
            System.out.println("connection terminated in client");
        }finally{
        	clientSocket.close();
        }
    }
    
    public static void main(String[] args){
       new MyClient().start();	   
    }
}