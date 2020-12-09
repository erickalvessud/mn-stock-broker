package com.erick.udemy.broker;

import com.erick.udemy.broker.model.Quote;
import com.erick.udemy.broker.model.Symbol;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class InMemoryStore {
    private List<Symbol> symbols;
    private final Map<String, Quote> cachedQuotes = new HashMap<>();
    private ThreadLocalRandom current = ThreadLocalRandom.current();

    public InMemoryStore(List<Symbol> symbols) {
        this.symbols = Stream.of("AAPL", "AMZN", "FB", "GOOG", "MSFT", "NFLX", "TSLA")
                .map(Symbol::new)
                .collect(Collectors.toList());

        this.symbols.forEach(symbol ->
            cachedQuotes.put(symbol.getValue(), randomQuote(symbol))
        );
    }

    public Optional<Quote> fetchQuote(String symbol) {
        return Optional.ofNullable(cachedQuotes.get(symbol)) ;
    }

    public List<Symbol> getAllSymbols() {
        return symbols;
    }

    private Quote randomQuote(Symbol symbol) {
        return Quote.builder().symbol(symbol)
                .bid(randomValue())
                .ask(randomValue())
                .lastPrice(randomValue())
                .volume(randomValue())
                .build();
    }

    private BigDecimal randomValue() {
        return BigDecimal.valueOf(current.nextDouble(1, 100));
    }

    public void update(Quote quote) {
        cachedQuotes.put(quote.getSymbol().getValue(), quote);
    }
}
