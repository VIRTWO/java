package web;

import general.ConsoleLogger;
import general.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlScrapper {

    private static Logger logger = new ConsoleLogger(UrlScrapper.class, 1);

    public Collection<String> scrapeHyperlinks(String baseUrl, int maximumBackwardDepth, int maximumForwardDepth) {
        Set<String> linksSeenAlready = new HashSet<String>();
        Set<String> links = new TreeSet<String>();
        Queue<String> linksToBeProcessed = new LinkedList<String>();

        linksToBeProcessed.add(baseUrl);

        int baseDepth = getUrlDepth(baseUrl);

        while (!linksToBeProcessed.isEmpty()) {
            // we have links to be processed
            String url = linksToBeProcessed.remove();
            logger.debug("processing: " + url);
            if (linksSeenAlready.contains(url)) {
                continue;
            }
            linksSeenAlready.add(url);
            try {
                Response response = Jsoup.connect(url).userAgent("Mozilla").execute();
                Document document = response.parse();
                Elements elements = document.select("a[href]");
                for (Element element : elements) {
                    String scrappedUrl = cleanUrl(element.attr("abs:href").trim());
                    if (!linksToBeProcessed.contains(scrappedUrl) // not queued already
                            && !links.contains(scrappedUrl) //
                            && !linksSeenAlready.contains(scrappedUrl) // already seen
                            && hasValidDepth(scrappedUrl, baseDepth, maximumBackwardDepth, maximumForwardDepth) // has a
                                                                                                                // valid
                                                                                                                // depth
                            && scrappedUrl.length() != 0 // has some length
                    ) {
                        // queue for processing
                        logger.debug("Process [" + url + "] " + scrappedUrl);
                        linksToBeProcessed.add(scrappedUrl);
                    }
                    // whatever link we go, we will have it
                    if (scrappedUrl.length() > 0) {
                        links.add(scrappedUrl);
                    }
                    // links.add("[" + url + "] " + scrappedUrl);
                    // once a link is seen, we never process it again
                }
            } catch (IOException e) {
                logger.error("Failed: " + url + " - " + e.getMessage());
                // e.printStackTrace();
            }
        }

        return links;
    }

    private String cleanUrl(String url) {
        if (url == null || url.trim().length() < 1) {
            return "";
        }
        String[] tkn = url.split("#");
        return tkn[0];
    }

    private boolean hasValidDepth(String url, int baseDepth, int maximumBackwardDepth, int maximumForwardDepth) {
        int relativeDepth = getUrlDepth(url) - baseDepth;

        if (relativeDepth > 0) {
            // we moved forward
            if (relativeDepth <= maximumForwardDepth) {
                return true;
            }
        } else if (relativeDepth < 0) {
            // we moved backward
            if (Math.abs(relativeDepth) <= maximumBackwardDepth) {
                return true;
            }
        } else {
            // same depth
            return true;
        }

        return false;
    }

    private int getUrlDepth(String urlString) {
        // assumption: trailing / means directory, otherwise file
        try {
            URL url = new URL(urlString);
            String path = url.getPath();
            String[] pathTkns = path.split("/");
            int depth = 0;
            for (String tkn : pathTkns) {
                if (tkn.trim().length() > 0) {
                    depth++;
                }
            }
            // folders and files in the folder are considered at same depth
            // so if we have depth > 0 and path is a file then subtract 1
            if (depth > 0 && !path.endsWith("/")) {
                depth--;
            }
            return depth;
        } catch (MalformedURLException e) {
            return Integer.MAX_VALUE;
        }
    }

}
