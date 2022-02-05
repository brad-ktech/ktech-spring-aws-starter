package com.ktech.starter.clio.security;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class UnsecureTrustManager implements X509TrustManager {

  public static TrustManager[] TrustManagerArray = new TrustManager[] { new UnsecureTrustManager() };

  public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
  }

  public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
  }

  public X509Certificate[] getAcceptedIssuers() {
    return null;
  }
}
