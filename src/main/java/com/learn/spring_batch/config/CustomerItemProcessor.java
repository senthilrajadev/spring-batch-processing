package com.learn.spring_batch.config;

import com.learn.spring_batch.entity.Customer;
import jakarta.annotation.Nullable;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public @Nullable Customer process(Customer item) throws Exception {
        return item;
    }
}
