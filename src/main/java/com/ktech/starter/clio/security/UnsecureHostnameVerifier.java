package com.ktech.starter.clio.security;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class UnsecureHostnameVerifier implements HostnameVerifier {
  
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }  
  
}
