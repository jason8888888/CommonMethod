package com.plf.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionCommunication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
final Business business= new Business();
		
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(int i=1;i<=50;i++){
						business.sub(i);
					}
				}
		}).start();
	
		for(int i=1;i<=50;i++){
			business.main(i);
		}
	}

	static class Business{
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		private boolean bShouldSub = true;
		public void sub(int i){
			lock.lock();
			try{
				while(!bShouldSub){
					try{
						condition.await();
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				for(int j = 1;j<=10;j++){
					System.out.println("sub thread sequece of "+j+",loop of "+i);
				}
				bShouldSub = false;
				condition.signal();
			}finally {
				// TODO: handle finally clause
				lock.unlock();
			}
		}
		
		public void main(int i){
			lock.lock();
			try{
				while(bShouldSub){
					try{
						 condition.await();
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				for(int j = 1;j<=100;j++){
					System.out.println("main thread sequece of "+j+",loop of "+i);
				}
				bShouldSub = true;
				condition.signal();
			}finally {
				// TODO: handle finally clause
				lock.unlock();
			}
		}
		
	}
}

