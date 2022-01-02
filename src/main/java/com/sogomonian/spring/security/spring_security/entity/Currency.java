package com.sogomonian.spring.security.spring_security.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {

    USD(431),
    EUR(451),
    RUB(456);

    private final int id;
}
