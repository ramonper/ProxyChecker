package me.projecta.proxychecker;

import me.projecta.proxychecker.threads.CheckerThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<String> PROXY_CACHE = new ArrayList<>();
    public static ArrayList<String> working_proxies = new ArrayList<>();

    public void process() {
        Scanner informationGrabber = new Scanner(System.in);

        //now asking for the file read the proxies
        System.out.println("What file contains the proxies? Insert with .txt/file extension");

        String fileName = informationGrabber.next();

        //now to read the proxies into an ArrayList
        try {
            Scanner proxiesReader = new Scanner(new File(fileName));
            while (proxiesReader.hasNext()) {
                PROXY_CACHE.add(proxiesReader.next());
            }
            proxiesReader.close();
        } catch (FileNotFoundException e) {}
        System.out.println("Gathered " + PROXY_CACHE.size() + " proxies.");

        //now to work with multithreading
        System.out.println("How many functions would you like to run?");
        int threadCount = informationGrabber.nextInt();
        //loading threads
        CheckerThread thread = new CheckerThread().url("http://google.com");
        thread.start();
        System.out.println("All threads have began running.");

        //now to save working proxies into a text file
        try {
            PrintWriter writer = new PrintWriter(fileName + ".working.text");
            for (int i = 0; i < working_proxies.size(); i++){
                writer.println(working_proxies.get(i));
            }
            writer.close();
            System.out.println("File successfuly saved in folder");
        } catch (FileNotFoundException e) {}
    }

    public static void main(String strings[]){
        System.out.println("Starting the process.");
        new Main().process();
    }
}
