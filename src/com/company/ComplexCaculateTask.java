package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ysy
 * @version v1.0
 * @description ComplexCaculateTask
 * @date 2018-12-24 17:29
 */
public class ComplexCaculateTask extends RecursiveTask<Integer> {
    private Integer max_limit = 3;

    private Integer start;
    private Integer end;

    public ComplexCaculateTask(Integer start,Integer end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        //任务足够小（类似递归）
        if(end-start<=max_limit){

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=start;i<=end;i++){
                sum =sum+i;
            }
        }else{
            //任务一分为2
            int middle=(end+start)/2;
            ComplexCaculateTask leftTask = new ComplexCaculateTask(start, middle);
            ComplexCaculateTask rightTask = new ComplexCaculateTask(middle+1,end);
            //执行任务
            leftTask.fork();
            Integer leftResult = leftTask.join();
            System.out.println(String.format("%d>%d:left>%d",start, end, leftResult));

            rightTask.fork();

            //汇总各个分任务节点

            Integer rightResult = rightTask.join();
            System.out.println(String.format("%d>%d:right>%d", start, end, rightResult));
            sum = leftResult+rightResult;
        }
        System.out.println(String.format("%d>%d", start, end));
        return sum;
    }
    public static void main(String[] args) {
        DateTimeFormatter DATE_FORMATER_FOR_FILE_EXP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate.from(DATE_FORMATER_FOR_FILE_EXP.parse("2018-12-01 00:00:00"));
        LocalDate.parse("2018-12-01");
        System.out.println("**********");
        int dataSize = 100;
        int taskSize = dataSize / 200;
        int restSize = dataSize % 200;
        System.out.println(taskSize);
        System.out.println(restSize);
        System.out.println("**********");
        List<Integer> tmp = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            tmp.add(i);
            System.out.println(String.format("%d,%d",200 * i, 200 + 200 * i - 1));
        }
        System.out.println(tmp.size());
        tmp.subList(5, 10);
        System.out.println("**********");
        ForkJoinPool forkjoinPool = new ForkJoinPool();

        //生成一个计算任务
        ComplexCaculateTask task = new ComplexCaculateTask(1, 100);

        //执行一个任务 （这个是有等待返回的调用方式）
        Future<Integer> result = forkjoinPool.submit(task);

        try {
            System.out.println(result.get());
        } catch (Exception e) {
            System.out.println(e);
        }
        forkjoinPool.shutdown();

    }
}
