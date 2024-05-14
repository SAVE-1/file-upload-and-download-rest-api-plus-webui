package com.filesharing.filebin.helper;

import jakarta.xml.bind.DatatypeConverter;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class FileHelper {
    public static String generateFingerprint(String name) {
        Date date = new java.util.Date();
        final String rand = "!!#Better three hours too soon than a minute too late.!!#";
        String input = name + rand + date.toString();

        return DigestUtils.md5Hex(input);
    }
}
