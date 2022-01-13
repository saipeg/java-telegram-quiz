package com.sogomonian.spring.security.spring_security.service.impl;

import com.sogomonian.spring.security.spring_security.entity.Currency;
import com.sogomonian.spring.security.spring_security.service.CurrencyModeService;

import java.util.HashMap;
import java.util.Map;

public class HashMapCurrencyModeService implements CurrencyModeService {
    private final Map<Long, Currency> originalCurrency = new HashMap<>();
    private final Map<Long, Currency> targetCurrency = new HashMap<>();

    public HashMapCurrencyModeService() {
        System.out.println("HASHMAP MODE is created");
    }

    @Override
    public Currency getOriginalCurrency(long chatId) {
        return originalCurrency.getOrDefault(chatId, Currency.YES);
    }

    @Override
    public Currency getTargetCurrency(long chatId) {
        return targetCurrency.getOrDefault(chatId, Currency.NOT_NOW);
    }

    @Override
    public void setOriginalCurrency(long chatId, Currency currency) {
        originalCurrency.put(chatId, currency);
    }

    @Override
    public void setTargetCurrency(long chatId, Currency currency) {
        targetCurrency.put(chatId, currency);
    }
}
