package com.learn.spring_batch.config;

import com.learn.spring_batch.entity.Customer;
import com.learn.spring_batch.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CustomerItemWriter implements ItemWriter<Customer> {

    private final CustomerRepository customerRepository;


    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        customerRepository.saveAll(chunk.getItems());
    }
}
