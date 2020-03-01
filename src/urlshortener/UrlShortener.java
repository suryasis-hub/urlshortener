package urlshortener;

import hashalgorithm.IHashStrategy;

import java.util.*;

public class UrlShortener {
    private IHashStrategy iHashStrategy;
    private Map<String, List<Url>> userToUrl;
    private List<String> registeredUser;
    static final int DEFAULT_TIME_TO_LIVE_IN_MILLIS = 500000;
    public UrlShortener(IHashStrategy instanceOfConcreteHashStrategy) {
        iHashStrategy = instanceOfConcreteHashStrategy;
        userToUrl = new HashMap<>();
        registeredUser = new ArrayList<>();
        registeredUser.add("abc");
        registeredUser.add("bad");
        registeredUser.add("bac");
        registeredUser.add("lom");
        registeredUser.add("DEFAULT");
    }

    public List<Url> getListOfShortenedUrlForUser(String userId) {
        List<Url> urlList = getUrls(userId);
        userToUrl.put(userId,urlList);
        return urlList;
    }

    public void addNewEntryForUser(String url,String userId) {
       addNewEntryForUserWithTTL(url,userId,DEFAULT_TIME_TO_LIVE_IN_MILLIS);
    }

    private boolean checkExistenceForShortenedUrl(String shortenedUrl) {
        for(Map.Entry<String,List<Url>> entry : userToUrl.entrySet()) {
            List<Url> urlList = entry.getValue();
            for(int i = 0 ; i < urlList.size() ; i++) {
                if(urlList.get(i).getShortenedUrl().equalsIgnoreCase(shortenedUrl))
                    return true;
            }
        }
        return false;
    }

    public void addNewEntryForUserWithTTL(String url,String userId,int timeToLive) {
        if(!registeredUser.contains(userId)) {
            System.out.println("userId is not registered. Treated as default!");
            userId = "DEFAULT";
        }
        List<Url> urlList = userToUrl.get(userId);
        String shortenedUrl = iHashStrategy.getHashFromUrl(url);
        while(checkExistenceForShortenedUrl(shortenedUrl)) {
            shortenedUrl = iHashStrategy.getHashFromUrl(url);
        }
        System.out.println(shortenedUrl);
        Url urlToInsert = new Url(url,shortenedUrl,timeToLive,new Date());
        if(urlList == null)
            urlList = new ArrayList<>();
        urlList.add(urlToInsert);
        userToUrl.put(userId,urlList);
    }

    public String getShortenedUrlForUser(String url,String userId) {
        if(!registeredUser.contains(userId)) {
            System.out.println("userId is not registered. Treated as default!");
            userId = "DEFAULT";
        }
        List<Url> urlList = getUrls(userId);
        for(int i = 0 ; i < urlList.size(); i++) {
            if(urlList.get(i).getOriginalUrl().equalsIgnoreCase(url))
                return urlList.get(i).getShortenedUrl();
        }
        return null;
    }

    private List<Url> getUrls(String userId) {
        List<Url> urlList = userToUrl.get(userId);
        for (int i = 0; i < urlList.size(); i++) {
            Url currentUrl = urlList.get(i);
            if (!currentUrl.isValid()) {
                urlList.remove(i);
            }
        }
        return urlList;
    }

    public String getLongUrlForUser(String shortUrl,String userId) {
        List<Url> urlList = getUrls(userId);
        for(int i = 0 ; i < urlList.size(); i++) {
            if(urlList.get(i).getShortenedUrl().equalsIgnoreCase(shortUrl));
                return urlList.get(i).getOriginalUrl();
        }
        return null;
    }

}
