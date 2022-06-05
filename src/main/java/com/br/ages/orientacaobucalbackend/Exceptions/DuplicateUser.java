package com.br.ages.orientacaobucalbackend.Exceptions;

public class DuplicateUser extends RuntimeException {
    public DuplicateUser(String msg) {
        super(msg);
    }
}
