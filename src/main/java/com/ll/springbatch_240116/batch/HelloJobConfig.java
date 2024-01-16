package com.ll.springbatch_240116.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class HelloJobConfig {
    @Bean
    public Job  simpleJob1(JobRepository jobRepository, Step simpleStep1){
        return new JobBuilder("helloJobStep1", jobRepository)
                .start(simpleStep1)
                .incrementer(new RunIdIncrementer()) // job enabled가 true일 때만 실행된다. 따라서 메소드를 이용하여 실행할 때는 파라미터를 명시적으로 지정해야함.
                .build();
    }

    @Bean
    public Step helloStep1(JobRepository jobRepository, Tasklet helloStep1Tasklet1,
                           PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("helloStep1Tasklet1", jobRepository)
                .tasklet(helloStep1Tasklet1, platformTransactionManager).build();
    }

    @Bean
    public Tasklet helloStep1Tasklet1(){
        return ((contribution, chunkContext) -> {
           log.info("Hello world");
           System.out.println("Hello World");
           return RepeatStatus.FINISHED;
        });
    }
}
