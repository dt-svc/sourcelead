package com.bis.sourcelead.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;


/**
 * Naresh Bolisetty
 * Date : 14-April-2017
 *  @call back controller advice
 *
 */
@ControllerAdvice
@RequestMapping("/app")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
    public JsonpAdvice() {
        super("callback");
    }
}
