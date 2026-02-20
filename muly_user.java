import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class muly_user{

	public static int counter = 0;
	public static void main(String args[]){
		
		try (ServerSocket ss = new ServerSocket(8080)){
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
			int contentLength = 0;

			while (!(m = reader.readLine()).isEmpty()) {
				System.out.println(m);
				if (m.startsWith("Content-Length:")) {
					contentLength = Integer.parseInt(m.split(":")[1].trim());
				}
			}
			
			char[] body = new char[contentLength];
			reader.read(body, 0, contentLength);
			String[] content = new String(body).split("&");

			System.out.println("Body:\n" + Arrays.toString(content));

			System.out.println(muly_user.counter);

			PrintWriter writer = new PrintWriter(s1.getOutputStream());
			writer.println("HTTP/1.1 200 OK");
			writer.println("Content-Type: text/html");
			writer.println();
			writer.println("<h1>Connection made</h1>");
			try (BufferedReader f = new BufferedReader(new FileReader("marathonform.html"))) {
				String line = f.readLine();
				while (line != null) {
					writer.println(line);
					line = f.readLine();
				}
			}
			writer.flush();
			

			// System.out.print("\nYour order: ");
			// Scanner sc = new Scanner(System.in);
			// writer.println("<h3>" + sc.nextLine() + "</h3>");
			// writer.flush();
		}
		catch (IOException e) {
			System.out.println(e);
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
}
