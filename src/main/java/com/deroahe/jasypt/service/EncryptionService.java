package com.deroahe.jasypt.service;

public interface EncryptionService {

    String encrypt(String value);

    String decrypt(String value);
}
