    package com.learn.spring_batch.config;

    import com.learn.spring_batch.entity.Customer;
    import com.learn.spring_batch.repository.CustomerRepository;
    import io.micrometer.core.instrument.distribution.StepBucketHistogram;
    import lombok.RequiredArgsConstructor;
    import org.springframework.batch.core.Job;
    import org.springframework.batch.core.Step;
    import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
    import org.springframework.batch.core.job.builder.JobBuilder;
    import org.springframework.batch.core.launch.support.RunIdIncrementer;
    import org.springframework.batch.core.repository.JobRepository;
    import org.springframework.batch.core.step.builder.StepBuilder;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.task.SimpleAsyncTaskExecutor;
    import org.springframework.core.task.TaskExecutor;
    import org.springframework.transaction.PlatformTransactionManager;

    @Configuration
    @RequiredArgsConstructor
    public class SpringBatchConfig {

        private final CustomerItemReader reader;
        private final CustomerItemProcessor processor;
        private final CustomerItemWriter writer;

    @Bean
    public Step customerStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){

            return  new StepBuilder("employeeStep", jobRepository)
                    .<Customer, Customer>chunk(10, platformTransactionManager)
                    .reader(reader.reader())
                    .processor(processor)
                    .writer(writer)
                    .taskExecutor(taskExecutor())
                    .build();
    }

    @Bean
    public Job customerJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new JobBuilder("employeeJob", jobRepository).incrementer(new RunIdIncrementer())
                .start(customerStep(jobRepository, platformTransactionManager)).build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }
}
