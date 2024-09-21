package com.api.gbookpdf.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    MASTER(1), ADMIN(2), NORMAL(3);

    private final int value;

    RoleEnum(int value) {
        this.value = value;
    }
}
