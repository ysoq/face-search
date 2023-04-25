package com.visual.face.search.core.common.enums;

public enum CollectionStatue {

    UNDEFINED(-1),

    NORMAL(0);

    private int value;

    CollectionStatue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static CollectionStatue valueOf(int value) {
        switch (value) {
            case 0:
                return NORMAL;
            default:
                return UNDEFINED;
        }
    }

}
