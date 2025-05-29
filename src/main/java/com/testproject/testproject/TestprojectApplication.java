package com.testproject.testproject;

import com.testproject.testproject.user.User;
import com.testproject.testproject.user.UserHttpClient;
import com.testproject.testproject.user.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@SpringBootApplication
public class TestprojectApplication {

    private static final Logger log = LoggerFactory.getLogger(TestprojectApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TestprojectApplication.class, args);
    }

    @Bean
    UserHttpClient userHttpClient() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(UserHttpClient.class);
    }

    @Bean
    CommandLineRunner runner(UserRestClient userRestClient) {
        return args -> {
            List<User> users = userRestClient.listUsers();
            System.out.println(users);
        };
    }
}