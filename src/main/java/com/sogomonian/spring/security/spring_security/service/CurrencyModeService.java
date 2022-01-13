package com.sogomonian.spring.security.spring_security.service;

import com.sogomonian.spring.security.spring_security.entity.Currency;
import com.sogomonian.spring.security.spring_security.service.impl.HashMapCurrencyModeService;

public interface CurrencyModeService {

    static CurrencyModeService getInstance() {
        return new HashMapCurrencyModeService();
    }

    Currency getOriginalCurrency(long chatId);

    Currency getTargetCurrency(long chatId);

    void setOriginalCurrency(long chatId, Currency currency);

    void setTargetCurrency(long chatId, Currency currency);
}


