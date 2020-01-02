package org.cny.yurayura;

import org.cny.yurayura.entity.sys.manager.Manager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 多线程测试（Runnable，Callable，线程池的使用）
 *
 * @author CNY
 * @date 2019年12月27日 15:38
 */
public class ThreadTest {

    @Test
    public void testRunnable() {
        Thread thread1 = new Thread(new RunnableImpl1());
        Thread thread2 = new Thread(new RunnableImpl2());
        thread1.start();
        thread2.start();
        System.out.println(Thread.currentThread().getName() + "--main"); // 这个是方法的主线程，所以第一个执行
    }

    static class RunnableImpl1 implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "--first Runnable");
        }
    }

    static class RunnableImpl2 implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "--second Runnable");
        }
    }

    // 1. 创建一个实现Callable的实现类
    static class GetManagerListCallable implements Callable {
        // 2. 实现call方法，将此线程需要执行的操作声明在call中
        @Override
        public Object call() {
            List<Manager> managerList = new ArrayList<>();
            try {
                Manager manager1 = new Manager().setId(66).setManagerName("777").setManagerPassword("888");
                Manager manager2 = new Manager().setId(99).setManagerName("1010").setManagerPassword("1111");
                managerList.add(manager1);
                managerList.add(manager2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "--first Callable");
            return managerList;
        }
    }

    // 1. 创建一个实现Callable的实现类
    static class GetManagerListCallable2 implements Callable {
        // 2. 实现call方法，将此线程需要执行的操作声明在call中
        @Override
        public Object call() {
            List<Manager> managerList = new ArrayList<>();
            try {
                Manager manager1 = new Manager().setId(77).setManagerName("dqwdwq").setManagerPassword("fwfw");
                managerList.add(manager1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "--second Callable");
            return managerList;
        }
    }

    @Test
    public void testCallable() {
        // 3. 创建Callable接口实现类的对象
        GetManagerListCallable getManagerListCallable = new GetManagerListCallable();
        GetManagerListCallable2 getManagerListCallable2 = new GetManagerListCallable2();
        // 4. 将此Callable接口实现类的对象传递到FutureTask构造器中，创建FutureTask的对象
        FutureTask futureTask = new FutureTask(getManagerListCallable);
        FutureTask futureTask2 = new FutureTask(getManagerListCallable2);
        Thread thread = new Thread(futureTask);
        Thread thread2 = new Thread(futureTask2);
        thread.start();
        thread2.start();
        // 6. 获取Callable中call方法的返回值
        // get() 返回值即为FutureTask构造器参数Callable实现类重写的call()的返回值
        try {
            Object managerList = futureTask.get();
            Object managerList2 = futureTask2.get();
            System.out.println(Thread.currentThread().getName() + "--" + managerList.toString());
            System.out.println(Thread.currentThread().getName() + "--" + managerList2.toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testThreadPool() {
        // 1. 提供指定线程数量的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 2. 执行指定的线程的操作，需要提供实现Runnable接口或Callable接口实现类的对象
        executorService.execute(new RunnableImpl1()); // Runnable使用线程池
        Future getManagerListfuture = executorService.submit(new GetManagerListCallable()); //Callable使用线程池
        try {
            Object managerList = getManagerListfuture.get();
            System.out.println(Thread.currentThread().getName() + "--" + managerList.toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //3. 关闭连接池
        executorService.shutdown();
    }

}
