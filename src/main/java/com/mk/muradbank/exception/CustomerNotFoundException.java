package com.mk.muradbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus-->uygulammın herhangi biyerinde CustomerNotFoundException türetildiğinde eğer http türünden dönderirilcekse
//-->statüsü herzaman  HttpStatus.BAD_REQUEST olmalıdır
@ResponseStatus(value = HttpStatus.BAD_REQUEST) //-->>Http=400
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

}
