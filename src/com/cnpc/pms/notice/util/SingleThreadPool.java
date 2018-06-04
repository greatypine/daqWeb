package com.cnpc.pms.notice.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池类
 * @author gbl
 *
 */
public class SingleThreadPool {
	
	private ExecutorService exe;  
    private static final int POOL_SIZE = 4;
    
    private SingleThreadPool(){
    	exe = Executors.newFixedThreadPool(POOL_SIZE);  
    }
    

    private static class LazyHolder {    
        private static final SingleThreadPool INSTANCE = new SingleThreadPool();    
    }    
    
     public static final SingleThreadPool getInstance() {    
        return LazyHolder.INSTANCE;    
     }

	public ExecutorService getExe() {
		return exe;
	}
    
	
}
