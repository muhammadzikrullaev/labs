package mzik.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.OutputStream;

@Configuration
public class IOConfig {

    @Bean
    public InputStream inputStream(){
        return System.in;
    }

    @Bean
    public OutputStream outputStream(){
        return System.out;
    }
}
