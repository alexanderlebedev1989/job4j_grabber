package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {


    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements values = doc.select("tr");
        for (Element tr : values) {
            if (tr.children().hasClass("postslisttopic")) {
                Elements td = tr.children();
                Element href = td.get(1).child(0);
                Element date = td.get(td.size() - 1);
                System.out.println(href.attr("href"));
                System.out.println(href.text() + " " + date.text());
            }
        }
    }
}

