/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.util.Random;

/**
 *
 * @author oliver.guenther
 */
public class ThreadOne extends Thread {
    
    static Random R = new Random();
    
    static int a = 1;
    
    @Override
    public void run() {
        System.out.println("Start Therad" +  this);
        int temp = a;
        try {
            Thread.sleep(R.nextInt(1000));
        } catch (InterruptedException ex) {
           
        }
        a = temp + 1;
        System.out.println("End Therad" +  this);
    }
   
        
    public static void main(String[] args) throws InterruptedException {
        
        
        
        System.out.println("main start");
        System.out.println(a);
        for (int i = 0; i < 10; i++) {
            Thread.sleep(R.nextInt(1000));
            new ThreadOne().start();
            
        }
        System.out.println(a);
        System.out.println("main ende");
    }
    
}
