package me.projecta.proxychecker.threads;

import me.projecta.proxychecker.Main;

import java.io.IOException;
import java.net.*;

public class CheckerThread extends Thread {

    private String URLLink;

    public void run() {
        while (Main.PROXY_CACHE.size() != 0) {

            String host;
            Integer port;

            //gathering the proxy

            synchronized (Main.PROXY_CACHE) {
                host = Main.PROXY_CACHE.get(0).split(":")[0];
                port = Integer.parseInt(Main.PROXY_CACHE.get(0).split(":")[1]);
                Main.PROXY_CACHE.remove(0);
            }

            try {
                URL url = new URL(URLLink);
                Proxy p = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(host, port));
                //now checking if the proxy works and connects to a given website
                URLConnection urlConnection = url.openConnection(p);
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();
                //now if it reaches this point, it successfully connected
                //now adding it to the working proxies list
                Main.working_proxies.add(host + ":" + port);
            } catch (IOException e) {
            }
        }
        //now to close if it done checking
        Thread.currentThread().interrupt();
    }

    public CheckerThread url(String URLLink){
        this.URLLink = URLLink;
        return this;
    }
}
