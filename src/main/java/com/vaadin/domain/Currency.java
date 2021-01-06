package com.vaadin.domain;

public enum Currency {
    USD("UnitedStatesDollar", 0),
    BTC("Bitcoin", 1),
    XMR("Monero", 2);
    private final String name;
    private final Integer value;

    Currency(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
