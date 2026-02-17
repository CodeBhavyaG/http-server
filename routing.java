import java.net.*;
import java.io.*;
import java.util.*;


class routing{
	public static void main(String[] args){
		try(ServerSocket ss = new ServerSocket(8080)){

			String m;
			List<String> list = new ArrayList<>();
			String[] header;
			while(true){
				Socket s = ss.accept();
				try{
					
					DataInputStream input = new DataInputStream(s.getInputStream());
					BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					m = reader.readLine();
					header = m.split(" ");
					while((m = reader.readLine()) != null && !m.isEmpty()){
						list.add(m); 
					}
					System.out.println(header[0] + " on path " + header[1]);
					System.out.println(list.get(0));
					if(header[0].equals("GET")){
						System.out.println("i am here");
						redirect(s,header[1]);
					}

				
				}
				catch(IOException i ){System.out.println(i);}
				finally{s.close();}
			}	
			
		}
		catch(IOException i){ System.out.println(i);}		
	}

	public static void redirect(Socket s,String str){
		
		try{
			PrintWriter writer = new PrintWriter(s.getOutputStream());
		if(str.equals("/")){
			writer.println("HTTP/1.2 200 OK");
			writer.println("Content-Type: text/html");
			writer.println();
			writer.println("<h1>Connected to server main page</h1>");
			writer.flush();
			
		}
		else if(str.equals("/thankyou")){
			writer.println("HTTP/1.2 200 OK");
			writer.println("Content-Type: text/html");
			writer.println();
			writer.println("<h1>Connected to thank you tab of the server</h1>");
			writer.flush();
		}
		}
		catch(IOException i ){System.out.println(i);}

	}
}
