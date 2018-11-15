import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
	public final int n;
	public boolean buff[];
	public final Lock lock = new ReentrantLock();
	public final Condition notEnoughElements = lock.newCondition();
	public final Condition notEnoughSpace = lock.newCondition();
	public int actual = 0; // wskazuje na pierwsze wolne miejsce
	public List<Integer> waitingProducents = new ArrayList<Integer>();
	public List<Integer> waitingConsumers = new ArrayList<Integer>();
	
	public Buffer(int n) {
		this.n=n;
		buff = new boolean[n];
		for(int i = 0; i < n; i++)
			buff[i] = false;
	}
	
	
	public void put(int num, int nr) {
		try {
			lock.lock();
			if(num>(n-actual)) {
				waitingProducents.add(new Integer(nr));
				while(num>(n-actual) || nr!=waitingProducents.get(0)) {
					notEnoughSpace.await();
				}
				waitingProducents.remove(0);
			}
			int last = actual+num;
			while(actual<last) {
				buff[actual] = true;
				actual++;
			}
			System.out.println("Watek nr " + nr + " dodal "+ num + " elementow. Zajete " + actual + " elementow");
			notEnoughElements.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void get(int num, int nr) {
		try {
			lock.lock();
			if(num>actual) {
				waitingConsumers.add(new Integer(nr));
				while(num>actual || nr!=waitingConsumers.get(0)) {
					notEnoughElements.await();
				}
				waitingConsumers.remove(0);
			}
			int first = actual-num;
			while(actual>first) {
				actual--;
				buff[actual] = false;
			}
			System.out.println("Watek nr " + nr + " pobral "+ num + " elementow. Pozostalo " + actual + " elementow");
			notEnoughSpace.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
