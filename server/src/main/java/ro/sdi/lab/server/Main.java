package ro.sdi.lab.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ro.sdi.lab.server.config.Config;

public class Main
{
    public static void main(String[] args)
    {
        new AnnotationConfigApplicationContext(Config.class.getPackageName());
        System.out.println("Server started...");
    }
}
