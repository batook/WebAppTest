package com.batook.ex2.jersey

import org.testng.annotations.Test

/**
 * Project WebAppTest
 * Created by batook on 24.05.2019 23:02
 *
 */
class RestServiceTest {
    @Test
    void testHello() {
        RestService service = new RestService()
        assert Objects.nonNull(service)
    }
}
