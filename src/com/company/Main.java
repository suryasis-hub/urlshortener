package com.company;

import hashalgorithm.UUIDStrategy;
import urlshortener.Url;
import urlshortener.UrlShortener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        UrlShortener urlShortener = new UrlShortener(new UUIDStrategy());
        final String DEFAULT_USER_NAME = "DEFAULT";
        while(true) {
            System.out.println("Enter command type");
            int commandType = Integer.parseInt(reader.readLine());
            switch (commandType) {
                case 0:
                    System.out.println("Registered user enters a url without ttl");
                    System.out.println("Enter website and userId");
                    String website = reader.readLine();
                    String userId = reader.readLine();
                    urlShortener.addNewEntryForUser(website, userId);
                    break;
                case 1:
                    System.out.println("Registered user enters a url without ttl");
                    System.out.println("Enter website and userId and ttl");
                    website = reader.readLine();
                    userId = reader.readLine();
                    int timeToLive = Integer.parseInt(reader.readLine());
                    urlShortener.addNewEntryForUserWithTTL(website, userId,timeToLive);
                    break;
                case 2 :
                    System.out.println("Registered user wants to check his list of websites");
                    System.out.println("Enter user id");
                    userId = reader.readLine();
                    List<Url> listOfWebsites = urlShortener.getListOfShortenedUrlForUser(userId);
                    if(listOfWebsites.size() == 0)
                        System.out.println("No websites present");
                    for(Url websiteElement:listOfWebsites) {
                        String websiteLong = websiteElement.getOriginalUrl();
                        String websiteShort = websiteElement.getShortenedUrl();
                        System.out.print(websiteLong + " " + websiteShort);
                        System.out.println();
                    }
                    break;
                case 3:
                    System.out.println("UnRegistered user wants to enter a website");
                    website = reader.readLine();
                    urlShortener.addNewEntryForUser(website,DEFAULT_USER_NAME);
                    break;
                case 4:
                    System.out.println("UnRegistered user wants to get a website");
                    website = reader.readLine();
                    String shortenedUrl = urlShortener.getShortenedUrlForUser(website,DEFAULT_USER_NAME);
                    System.out.println(shortenedUrl);
                    break;
                case 5:
                    System.out.println("Registered user wants to get long website");
                    String shortenedWebsite = reader.readLine();
                    userId = reader.readLine();
                    String longenedUrl = urlShortener.getLongUrlForUser(shortenedWebsite,userId);
                    System.out.println(longenedUrl);
                    break;
                case 6:
                    System.out.println("UnRegistered user wants to get long website");
                    shortenedWebsite = reader.readLine();
                    longenedUrl = urlShortener.getLongUrlForUser(shortenedWebsite,DEFAULT_USER_NAME);
                    System.out.println(longenedUrl);
                    break;
                case -1:
                    break;

            }
        }
    }
}
