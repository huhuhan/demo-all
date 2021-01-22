package com.yh.demo.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yanghan
 * @date 2021/1/19
 */
public class Message {

    private final Lock lock = new ReentrantLock();

    private final Condition producedMsg = lock.newCondition();

    private final Condition consumedMsg = lock.newCondition();

    private String message;

    private boolean state;

    private boolean end;

    public void consume() {
        //lock
        lock.lock();
        try {
            // no new message wait for new message
            while (!state) {
                producedMsg.await();
            }

            System.out.println("consume message : " + message);
            state = false;
            // message consumed, notify waiting thread
            consumedMsg.signal();
        } catch (InterruptedException ie) {
            System.out.println("Thread interrupted - viewMessage");
        } finally {
            lock.unlock();
        }
    }

    public void produce(String message) {
        lock.lock();
        try {
            // last message not consumed, wait for it be consumed
            while (state) {
                consumedMsg.await();
            }

            System.out.println("produce msg: " + message);
            this.message = message;
            state = true;
            // new message added, notify waiting thread
            producedMsg.signal();
        } catch (InterruptedException ie) {
            System.out.println("Thread interrupted - publishMessage");
        } finally {
            lock.unlock();
        }
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

}