package br.com.mimobella;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"br.*"}) /*Mapeamento dos pacotes*/
@EnableJpaRepositories(basePackages = {"br.com.mimobella.repositories"}) /*Mapeamento dos pacotes dos Repositorios*/
@EnableTransactionManagement /*Transações que serão feitas pelo projeto*/
public class LojaMimoBellaApplication implements AsyncConfigurer {
    public static void main(String[] args) {

//        System.out.println(new BCryptPasswordEncoder().encode("123456"));

        SpringApplication.run(LojaMimoBellaApplication.class, args);

    }

    @Override
    @Bean
    public Executor getAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new  ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Assyncrono Thread");
        executor.initialize();
        return executor;
    }

}
