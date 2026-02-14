import java.io.*;
import java.net.*;

public class muly_user{

	public static int counter = 0;
	public static void main(String args[]){
		
		try(ServerSocket ss = new ServerSocket(8080)){
			System.out.println("Wating for Request");
			while(true){
				Socket s = ss.accept();
				counter++;
				MyRunner r = new MyRunner(s);
				Thread thread = new Thread(r);
				thread.start();	
			}

		}
		catch(IOException e){System.out.println(e);}
	}
}

class MyRunner implements Runnable{

	private Socket s;

	public MyRunner(Socket socket){
		this.s = socket;
			
	}
	@Override
	public void run(){
		try(Socket s1 = this.s){
		DataInputStream input = new DataInputStream(s1.getInputStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		String m;

		while((m=reader.readLine()) != null && !m.isEmpty()){
			System.out.println(m);
		}
		System.out.println(new muly_user().counter);

		PrintWriter writer = new PrintWriter(s1.getOutputStream());
		writer.println("HTTP/1.1 200 OK");
		writer.println("Content-Type: text/html");
		writer.println();
		writer.println("<h1>Connection made</h1>");
		writer.flush();
		}
		catch(IOException e){System.out.println(e);}
	}
}
