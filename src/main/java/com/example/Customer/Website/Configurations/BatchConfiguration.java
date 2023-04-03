package com.example.Customer.Website.Configurations;

import com.example.Customer.Website.models.Customer;
import com.example.Customer.Website.repositories.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step firstStep) {
        return jobBuilderFactory.get("employee-loader-job")
                .incrementer(new RunIdIncrementer())
                .start(firstStep)
                .build();
    }

    @Bean
    public Step firstStep(StepBuilderFactory stepBuilderFactory, ItemReader<Customer> csvReader, NameProcessor processor, EmployeeWriter writer) {
        // This step just reads the csv file and then writes the entries into the database
        return stepBuilderFactory.get("name-step")
                .<Customer, Customer>chunk(100)
                .reader(csvReader)
                .processor(processor)
                .writer(writer)
                .allowStartIfComplete(false)
                .build();
    }

    @Bean
    public FlatFileItemReader<Customer> csvReader(@Value("${inputFile}") String inputFile) {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("csv-reader")
                .resource(new ClassPathResource(inputFile))
                .delimited()
                .names("id", "fullName", "emailAddress", "age", "address")
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{setTargetType(Customer.class);}})
                .build();
    }


    @Component
    public static class NameProcessor implements ItemProcessor<Customer, Customer> {
        // This helps you to process the names of the employee at a set time
        @Override
        public Customer process(Customer customer) {

            return customer;
        }
    }


    @Component
    public static class EmployeeWriter implements ItemWriter<Customer> {

        @Autowired
        private CustomerRepository customerRepository;

        @Value("${sleepTime}")
        private Integer SLEEP_TIME;

        @Override
        public void write(List<? extends Customer> customers) throws InterruptedException {
            customerRepository.saveAll(customers);
            Thread.sleep(SLEEP_TIME);
            System.out.println("Saved customers: " + customers);
        }
    }
}
