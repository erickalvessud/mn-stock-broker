package com.erick.udemy.broker;

import com.erick.udemy.broker.model.Quote;
import com.erick.udemy.broker.model.Symbol;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.micronaut.http.HttpRequest.GET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class QuotesControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(QuotesControllerTest.class);
    @Inject
    EmbeddedApplication application;

    @Inject
    InMemoryStore store;

    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    void returnsQuotePErSymbol() {
        Quote apple = initRandomQuote("AAPL");
        store.update(apple);

        Quote amazon = initRandomQuote("AMZN");
        store.update(amazon);

        Quote appleResult = client.toBlocking().retrieve(GET("/quotes/AAPL"), Quote.class);
        LOG.debug("Result: {}", appleResult);
        assertThat(apple).isEqualToComparingFieldByField(appleResult);

        Quote amazonResult = client.toBlocking().retrieve(GET("/quotes/AMZN"), Quote.class);
        LOG.debug("Result: {}", amazonResult);
        assertThat(amazon).isEqualToComparingFieldByField(amazonResult);
    }

    private Quote initRandomQuote(String aapl) {
        return Quote.builder().symbol(new Symbol("AAPL"))
                .bid(randomValue())
                .ask(randomValue())
                .lastPrice(randomValue())
                .volume(randomValue())
                .build();
    }

    private BigDecimal randomValue() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100));
    }

}
