import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class Consumer implements Runnable{
	public Random random = new Random();
	public Buffer buffer;
	public int M;
	public int nr;
	
	public Consumer(int M, Buffer buff, int nr) {
		this.M = M;
		this.buffer=buff;
		this.nr = nr;
	}
	
	public void run() {
		while(true) {
			int size = random.nextInt(M);
			long start = System.nanoTime();
			buffer.get(size, nr);
			long end = System.nanoTime();
			long time = (end-start)/1000;
			System.out.println("Czas pobierania " + size + " elementow to " + time + " us");
			PrintWriter cons;
			try {
				cons = new PrintWriter(new FileOutputStream(new File("konsument.txt"), true));
				cons.println(time + "," + size);
				cons.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
		}
	}
}
