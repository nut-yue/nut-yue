package com.mall.clinicutil.exception;

/**
 * @ClassName : UnauthorizedException
 * @Date ：2020/3/3 17:05
 * @author：nut-yue
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
    }
}
