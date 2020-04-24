package com.otus.hw04;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

/**
-Xms256m
-Xmx256m
-XX:+UseG1GC

 -Xms256m
 -Xmx256m
 -XX:+UseSerialGC

 -Xms256m
 -Xmx256m
 -XX:+UseParallelGC

 -Xms4096m
 -Xmx4096m
 -XX:+UseG1GC

 -Xms4096m
 -Xmx4096m
 -XX:+UseSerialGC

 -Xms4096m
 -Xmx4096m
 -XX:+UseParallelGC
*/

public class Main {
    public static void main(String[] args) {
        switchOnMonitoring();
        Runnable r = () -> {
            try {
                int initialValue = 100_000;
                int sizeOfAddingElements = 1;
                var list = new ArrayList<Integer>();
                while (true) {
                    for (int i = initialValue; i < sizeOfAddingElements; i++) {
                        list.add(i);
                    }

                    for (int i = initialValue / 2; i < sizeOfAddingElements / 2; i++) {
                        list.set(i, null);
                    }
                    initialValue = 100_000;
                    sizeOfAddingElements += initialValue;
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        var t = new Thread(r);
        t.start();
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}


