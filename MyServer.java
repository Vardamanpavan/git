package latest.udp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;
import java.util.Scanner;
 
public class MyServer extends Thread
{
	Scanner sc = null;
	
	public void run(){
		System.out.print("Enter port to connect server:");
    	sc=new Scanner(System.in);
    	int port=sc.nextInt();
    	System.out.print("Enter server location:");
    	String location = sc.next(); 
    	server(port,location);
	}
	public static void server(int portNumber, String location){
		DatagramSocket serverSocket = null;
	try{
	   serverSocket = new DatagramSocket(portNumber);
	   byte[] bufData = new byte[1024];
	   DatagramPacket request = new DatagramPacket(bufData, bufData.length);
	             
	   System.out.println("Server socket created. Waiting for request");
	            
	   while(true){
	   serverSocket.receive(request);
	                
	   byte[] data = request.getData();
	   
	   String inputData = new String(data, 0, request.getLength());
	                 
	   if(inputData.equals("request")){
		   inputData = "Server location is "+location+" and temparature is " + new Random().nextInt(100);
		   DatagramPacket dp = new DatagramPacket(inputData.getBytes() , inputData.getBytes().length , request.getAddress() , request.getPort());
		   serverSocket.send(dp);
	   }
	   if(inputData.equals("stop")){
		   DatagramPacket dp = new DatagramPacket("bye".getBytes() , "bye".getBytes().length , request.getAddress() , request.getPort());
		   serverSocket.send(dp);
		   serverSocket.close();
	   }
	   }
	}
	         
	        
	catch(IOException e){
		System.out.println("connection terminated in server");
	}
	  
	}
    public static void main(String[] args){
    	new MyServer().start();
    }
}