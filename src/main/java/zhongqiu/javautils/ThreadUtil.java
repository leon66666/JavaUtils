package zhongqiu.javautils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangzhongqiu on 2017/9/28.
 */
public class ThreadUtil {
    private static Log log = LogFactory.getLog(ThreadUtil.class);

    public static void fixedThreadPool(int poolSize, List<Runnable> runnables) {
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize, new CommonThreadFactory());
        for (Runnable runnable : runnables) {
            try {
                executorService.execute(runnable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                log.info("Waiting batchRecharge thread pool close..."); // Waiting thread pool close...
            }
        } catch (InterruptedException ex) { // should not reach here
            ex.printStackTrace();
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        } catch (Exception ex) { // should not reach here
            ex.printStackTrace();
            executorService.shutdownNow();
        }
    }
}

class CommonThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    CommonThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread()
                .getThreadGroup();
        namePrefix = "pool-recharge-task" + poolNumber.getAndIncrement()
                + "-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix
                + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
