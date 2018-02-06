package com.unicode.test.rest.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by baybora on 2/3/18.
 *
 */
@RestController
public class EchoApiImpl implements EchoApi{


    @GetMapping(ECHO_TEST_URL)
    public String test() {
        return ECHO_TEST_SUCCESS_MESSAGE;
    }

}
