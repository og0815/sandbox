/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrent;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author oliver.guenther
 */
public class ShowAtomicBoolean {

    private static int i = 0;
    
    {
        i++;
        System.out.println("i" + i);
    }
    
    private static boolean inited = false;

    private static int initcount = 0;

    private static AtomicBoolean ainited = new AtomicBoolean(false);

    public static void initAtomic() {
        if (ainited.getAndSet(true)) return;
        initcount++;
        try {
            Thread.sleep(100);
            inited = true;
        } catch (InterruptedException ex) {

        }
    }

    public static void initClassic() {
        if (inited) return;
        initcount++;
        try {
            Thread.sleep(100);
            inited = true;
        } catch (InterruptedException ex) {

        }
    }

    public static class RunFive implements Callable<Void> {

        @Override
        public Void call() {
//            initClassic();
            initAtomic();
            return null;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        //     ainited.
        ExecutorService es = Executors.newFixedThreadPool(20);
        List<RunFive> works = IntStream.range(0, 100).mapToObj(i -> new RunFive()).collect(Collectors.toList());
        es.invokeAll(works);

        System.out.println("Initcount = " + initcount);
        es.shutdown();
    }

}
