package com.wootech.dropthecode.repository.replication;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CircularListTest {

    @Test
    @DisplayName("slave가 번갈아 호출되는지 확인")
    void getOne() {
        // given
        List<String> slaves = Arrays.asList("slave1", "slave2");
        CircularList<String> circularList = new CircularList<>(slaves);

        // when
        String first = circularList.getOne();
        String second = circularList.getOne();
        String third = circularList.getOne();

        // then
        assertThat(first).isEqualTo("slave2");
        assertThat(second).isEqualTo("slave1");
        assertThat(third).isEqualTo("slave2");
    }

}
