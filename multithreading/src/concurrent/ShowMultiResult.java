/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrent;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author oliver.guenther
 */
public class ShowMultiResult {
    
    public static Random R = new Random();
    
    public static class ComplexWork implements Callable<Integer> {

        private final int id;

        public ComplexWork(int id) {
            this.id = id;
        }

        
        @Override
        public Integer call() throws Exception {
            System.out.println("Started Thread: " + id);
            Thread.sleep(R.nextInt(3000));
            System.out.println("Completed Thread: " + id);
            return R.nextInt(20);
        }
    
}
    

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(5);

        List<ComplexWork> works = IntStream.range(0, 100).mapToObj(i -> new ComplexWork(i)).collect(Collectors.toList());
        final List<Future<Integer>> results = es.invokeAll(works);

        Future<Integer> summe = es.submit(() -> {
            int sum = 0;
            for (Future<Integer> fi : results) {
                sum += fi.get();
            }
            return sum;
        });
        
        System.out.println("Summe: " + summe.get());
        
        es.shutdown();
        
    }
    
}
