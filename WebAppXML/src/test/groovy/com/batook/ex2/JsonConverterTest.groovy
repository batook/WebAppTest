package com.batook.ex2

import com.batook.ex2.data.entity.Banner
import org.junit.Test

/**
 * Project WebAppTest
 * Created by batook on 25.05.2019 09:36
 *
 */
class JsonConverterTest {
    @Test
    void testFromJsonString() {
        def jsonString = '{"line":"1"}'
        Banner data = JsonConverter.fromJsonString(jsonString, Banner.class)
        assert Objects.nonNull(data)
        assert '1' == data.getLine()
    }

    @Test
    void testToJsonString() {
        Banner data = new Banner()
        data.setLine('1')
        def jsonString = JsonConverter.toJsonString(data)
        assert Objects.nonNull(data)
        assert '{"line":"1"}' == jsonString
    }
}
