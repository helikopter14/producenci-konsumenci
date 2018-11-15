import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Producent implements Runnable{
	Random random = new Random();
	Buffer buffer;
	int M;
	int nr;
	
	public Producent(int M, Buffer buff, int nr) {
		this.M = M;
		this.buffer=buff;
		this.nr=nr;
	}
	
	public void run() {
		while(true) {
			int size = random.nextInt(M);
			long start = System.nanoTime();
			buffer.put(size, nr);
			long end = System.nanoTime();
			long time = (end-start)/1000;
			System.out.println("Czas dodawania " + size + " elementow to " + time + " us");
			PrintWriter prod;
			try {
				prod = new PrintWriter(new FileOutputStream(new File("producent.txt"), true));
				prod.println(time + "," + size);
				prod.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
		}
	}
}