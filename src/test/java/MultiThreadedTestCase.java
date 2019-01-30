
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by manisha.v on 30/01/19.
 */

public class MultiThreadedTestCase {

    /**
     * Generates sequential unique IDs starting with 1, 2, 3, and so on.
     * <p>
     * This class is NOT thread-safe.
     * </p>
     */
    static class BrokenUniqueIdGenerator {
        private long counter = 0;

        public long nextId() {
            return ++counter;
        }
    }

    /**
     * Generates sequential unique IDs starting with 1, 2, 3, and so on.
     * <p>
     * This class is thread-safe.
     * </p>
     */
    static class UniqueIdGenerator {
        private final AtomicLong counter = new AtomicLong();

        public long nextId() {
            return counter.incrementAndGet();
        }
    }

    private Callable<Long> toCallable(final Runnable runnable) {
        return new Callable<Long>() {
            public Long call() {
                runnable.run();
                return null;
            }
        };
    }

    private void test(final int threadCount) throws InterruptedException, ExecutionException {
        final UniqueIdGenerator domainObject = new UniqueIdGenerator();
        JavaHTTPServer task = new JavaHTTPServer();


        List<JavaHTTPServer> tasks = Collections.nCopies(threadCount, task);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        List<Callable<Long>> callables = new ArrayList();
        for (Runnable r : tasks) {
            callables.add(toCallable(r));
        }



        List<Future<Long>> futures = executorService.invokeAll(callables);
        List<Long> resultList = new ArrayList<Long>(futures.size());
        // Check for exceptions
        for (Future<Long> future : futures) {
            // Throws an exception if an exception was thrown by the task.
            resultList.add(future.get());
        }
        // Validate the IDs
        Assert.assertEquals(threadCount, futures.size());
        List<Long> expectedList = new ArrayList<Long>(threadCount);
        for (long i = 1; i <= threadCount; i++) {
            expectedList.add(i);
        }
        Collections.sort(resultList);
        Assert.assertEquals(expectedList, resultList);
    }

    @Test
    public void test01() throws InterruptedException, ExecutionException {
        test(1);
    }

    @Test
    public void test02() throws InterruptedException, ExecutionException {
        test(2);
    }

    @Test
    public void test04() throws InterruptedException, ExecutionException {
        test(4);
    }

    @Test
    public void test08() throws InterruptedException, ExecutionException {
        test(8);
    }

    @Test
    public void test16() throws InterruptedException, ExecutionException {
        test(16);
    }

    @Test
    public void test32() throws InterruptedException, ExecutionException {
        test(32);
    }
}
