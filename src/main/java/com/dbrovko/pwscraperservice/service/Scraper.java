package com.dbrovko.pwscraperservice.service;

import com.dbrovko.pwscraperservice.exception.LogNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

@Slf4j
abstract class Scraper<T> {

    abstract void scrape() throws IOException, LogNotFoundException, InterruptedException;

    abstract void update() throws IOException, InterruptedException;

    CloseableHttpResponse getRequest(final String URL) throws IOException {
        return HttpClients.createDefault().execute(new HttpGet(URL));
    }

    CloseableHttpResponse postRequest(final String URL, StringEntity entity, Header[] headers) throws IOException {

        HttpPost post = new HttpPost(URL);
        post.setEntity(entity);
        post.setHeaders(headers);

        return HttpClients.createDefault().execute(post);
    }
}