package com.michaelwillemse.mwrestuser.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Michael on 22/08/14.
 */
public class MD5Hash {
    public static String getMD5Hash(String source){
        return DigestUtils.md5Hex(source);
    }
}
