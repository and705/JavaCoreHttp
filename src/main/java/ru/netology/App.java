package ru.netology;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class App {
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {


        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        //создание запроса
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");


        //отправка запроса
        CloseableHttpResponse response = httpClient.execute(request);

        //mapper
        List<Cat> cats = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {
        });
        //вывод отфильтрованного списка в консоль
        cats.stream().filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0)
                .forEach(System.out::println);


    }
}
