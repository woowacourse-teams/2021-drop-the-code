package com.wootech.dropthecode.repository.replication;

import java.util.List;

public class CircularList<T> {
    private final List<T> list;
    private Integer counter = 0;

    public CircularList(List<T> list) {
        this.list = list;
    }

    public T getOne() {
        return list.get(++counter % list.size());
    }
}
