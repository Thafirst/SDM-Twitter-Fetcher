/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.twitterapp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Kezides
 */
public class TwitterApp {

    public static void main(String[] args) {

        TwitterApp SDM = new TwitterApp();
        SDM.init();
//        SDM.saveToFile(5, "this is a test", "vennekilde", "demokrat");
//        SDM.saveToFile(2, "cirkler", "patrick", "liberal");
//        SDM.saveToFile(2, "er", "patrick", "liberal");
//        SDM.saveToFile(2, "amazing", "patrick", "liberal");
//        SDM.saveToFile(2, "4-leif", "patrick", "liberal");
    }

    private Twitter twitter;
    
    private HashMap<String,Orientation> listOfPeople = new HashMap<>();

    private String tweetSearchWord = "Trump";
    private String tweetSearchPerson = "realDonaldTrump";
    private int tweetPage = 1;
    
    private String tweetFileName = "Tweets.csv";
    private String userFileName = "Users.csv";
    
    
    private void init() {
        buildConfig();
        
//        Map<String ,RateLimitStatus> rateLimitStatus;
//        try {
//            rateLimitStatus = twitter.getRateLimitStatus();
//        } catch (TwitterException ex) {
//            Logger.getLogger(TwitterApp.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }
//        for (String endpoint : rateLimitStatus.keySet()) {
//            RateLimitStatus status = rateLimitStatus.get(endpoint);
//            System.out.println("Endpoint: " + endpoint);
//            System.out.println(" Limit: " + status.getLimit());
//            System.out.println(" Remaining: " + status.getRemaining());
//            System.out.println(" ResetTimeInSeconds: " + status.getResetTimeInSeconds());
//            System.out.println(" SecondsUntilReset: " + status.getSecondsUntilReset());
//        }
        //Poli people
        listOfPeople.put("realDonaldTrump",Orientation.REPUBLICAN);
        listOfPeople.put("mike_pence",Orientation.REPUBLICAN);
////        listOfPeople.put("HiIaryCIinton",Orientation.DEMOCRATIC);
//        listOfPeople.put("timkaine",Orientation.DEMOCRATIC);
//        listOfPeople.put("TedCruiseIs",Orientation.REPUBLICAN);
////        listOfPeople.put("GovGaryJohnson",Orientation.LIBERTARIAN);
////        listOfPeople.put("GovBillWeld",Orientation.LIBERTARIAN);
////        listOfPeople.put("DrJillStein",Orientation.GREEN);
////        listOfPeople.put("ajamubaraka",Orientation.GREEN);
////        listOfPeople.put("DarrellCastle",Orientation.CONSTITION);
//        
//        //randoms 
//        listOfPeople.put("phil200269",Orientation.REPUBLICAN);
//        listOfPeople.put("JrcheneyJohn",Orientation.REPUBLICAN);
//        listOfPeople.put("tHeMeRvAr",Orientation.REPUBLICAN);
//        listOfPeople.put("bunchboys26",Orientation.REPUBLICAN);
//        listOfPeople.put("Uozob",Orientation.DEMOCRATIC);
//        listOfPeople.put("BluestateGranny",Orientation.DEMOCRATIC);
//        listOfPeople.put("katvicente2",Orientation.DEMOCRATIC);
////        listOfPeople.put("RyanSpratt4",Orientation.GREEN);
////        listOfPeople.put("JohnMark_IB",Orientation.CONSTITION);
////        listOfPeople.put("dondickerson0",Orientation.CONSTITION);
////        listOfPeople.put("DaniRHansen",Orientation.CONSTITION);
//        listOfPeople.put("MeAnAmerican",Orientation.REPUBLICAN);
//        listOfPeople.put("ksbans1",Orientation.REPUBLICAN);
//        listOfPeople.put("riclove87",Orientation.REPUBLICAN);
//        listOfPeople.put("dawngpsalm63",Orientation.REPUBLICAN);
//        listOfPeople.put("DianaBialkowski",Orientation.REPUBLICAN);
//        listOfPeople.put("loisroy72",Orientation.REPUBLICAN);
//        listOfPeople.put("crazybody6926",Orientation.REPUBLICAN);
//        listOfPeople.put("imagiowomen",Orientation.REPUBLICAN);
//        listOfPeople.put("JerriKestner",Orientation.REPUBLICAN);
//        listOfPeople.put("held_ira",Orientation.REPUBLICAN);
//        listOfPeople.put("rinosiconolfi1",Orientation.REPUBLICAN);
//        listOfPeople.put("KelleyKarla",Orientation.REPUBLICAN);
//        listOfPeople.put("TinaTefera",Orientation.DEMOCRATIC);
//        listOfPeople.put("GeoFarenthold",Orientation.DEMOCRATIC);
//        listOfPeople.put("silmaravolpi",Orientation.DEMOCRATIC);
//        listOfPeople.put("lecrin",Orientation.DEMOCRATIC);
//        listOfPeople.put("DavikaStaar",Orientation.DEMOCRATIC);
//        listOfPeople.put("baekhyunseulgi",Orientation.DEMOCRATIC);
//        listOfPeople.put("RheaLynnTurner",Orientation.DEMOCRATIC);
//        listOfPeople.put("RedDawn45918634",Orientation.DEMOCRATIC);
//        listOfPeople.put("CameronAtfield",Orientation.DEMOCRATIC);
//        listOfPeople.put("Hill4America",Orientation.DEMOCRATIC);
//        listOfPeople.put("TerryMatthew01",Orientation.DEMOCRATIC);
//        listOfPeople.put("cadigal94",Orientation.DEMOCRATIC);
//        listOfPeople.put("odctovessel",Orientation.DEMOCRATIC);
//        listOfPeople.put("WhatRUTinkin",Orientation.DEMOCRATIC);
//        listOfPeople.put("SatuRuna",Orientation.DEMOCRATIC);
//        listOfPeople.put("truthaddict76",Orientation.DEMOCRATIC);
//        listOfPeople.put("PaulBrandfass",Orientation.DEMOCRATIC);
//        listOfPeople.put("JimCramptonWPG",Orientation.DEMOCRATIC);
//        
        
        getTimeline();
    }
    

    public void getTimeline() {
        Set<String> keySet = listOfPeople.keySet();
        for(String key : keySet){
            System.out.println("Debug Person - " + key);
            try {
                List<Status> statuses = null;
                for(int i = 1; i<16; i++){
                    statuses = twitter.getUserTimeline(key, new Paging(i));
                    System.out.println("Debug - filling statuses = " + i + " - Size: " + statuses.size());

                    if (statuses.size() < 1 || statuses == null) {
                        System.out.println("List Empty.");
                        break;
                    }
                    //System.out.println("Showing @" + statuses.get(0).getUser().getScreenName() + "'s home timeline.");
                    for (Status status : statuses) {
//                        System.out.println("Debug - text: " + status.getText());
                        saveToFile(status.getUser().getId(), status.getText().replaceAll("\n", "\\\\n"), status.getUser().getScreenName(), listOfPeople.get(key));
//                        System.out.println("debug - truncated: " + status.isTruncated());
                    }
                }
                if (statuses.size() < 1 || statuses == null) {
                    continue;
                }
                Status temp = statuses.get(0);
                saveToFile(temp.getUser().getId(),temp.getUser().getScreenName(),listOfPeople.get(key));
            } catch (TwitterException te) {
                te.printStackTrace();
                System.out.println("Failed to get timeline: " + te.getMessage());
                System.exit(-1);
            }
        }
        
    }

    public void getTweet() {

        Query query = new Query(tweetSearchWord);
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException ex) {
            java.util.logging.Logger.getLogger(TwitterApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result == null) {
            System.out.println("nope");
            return;
        }
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }
    }

    public void buildConfig() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("")
                .setOAuthConsumerSecret("")
                .setOAuthAccessToken("")
                .setOAuthAccessTokenSecret("");
        TwitterFactory tf = new TwitterFactory(cb.build());
        
        twitter = tf.getInstance();
    }
    
    public void saveToFile(long id, String user, Orientation orientation){
        saveToFile(id, null, user, orientation);
    }

    public void saveToFile(long id, String tweet, String user, Orientation orientation) {
        OutputStreamWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT;
        
        if(tweet != null){
            try {
                fileWriter = new OutputStreamWriter(new FileOutputStream(tweetFileName, true),"UTF-8");

                csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
                csvFilePrinter.printRecord(id, orientation.name().toLowerCase(), tweet);

            } catch (IOException ex) {
                Logger.getLogger(TwitterApp.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    fileWriter.flush();
                    fileWriter.close();
                    csvFilePrinter.close();
                } catch (IOException e) {
                    System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                    e.printStackTrace();
                }

            }
        }else{
            try {
                fileWriter = new OutputStreamWriter(new FileOutputStream(userFileName, true),"UTF-8");
                csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
                csvFilePrinter.printRecord(id, user, orientation.name().toLowerCase());

            } catch (IOException ex) {
                Logger.getLogger(TwitterApp.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    fileWriter.flush();
                    fileWriter.close();
                    csvFilePrinter.close();
                } catch (IOException e) {
                    System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                    e.printStackTrace();
                }

            }
        }

        
    }

}
