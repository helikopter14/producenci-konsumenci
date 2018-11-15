import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String Args[]) {
		int M = 1000;
		Buffer buff = new Buffer(2*M);
		
		List<Thread> producents = new ArrayList<Thread>();
		List<Thread> consumers = new ArrayList<Thread>();
		
		for(int i = 1; i <= 10; i++)
			producents.add(new Thread(new Producent(M, buff, i)));
		
		for(int i = 1; i <= 10; i++)
			consumers.add(new Thread(new Consumer(M, buff, i)));
		
		for(Thread t : producents)
			t.start();
		
		for(Thread t : consumers)
			t.start();
	}
}
