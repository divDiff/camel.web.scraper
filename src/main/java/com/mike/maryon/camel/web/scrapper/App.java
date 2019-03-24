package com.mike.maryon.camel.web.scrapper;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import com.mike.maryon.camel.web.scrapper.route.ScrapingRoute;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        CamelContext context = new DefaultCamelContext();
        try {
            context.addRoutes(new ScrapingRoute());
            context.start();
            Thread.sleep(5 * 60 * 1000);
            context.stop();
        } finally {
            context.stop();
        }
    }
}
