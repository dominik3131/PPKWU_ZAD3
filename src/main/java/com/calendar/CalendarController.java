package com.calendar;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.UidGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;



@Controller
public class CalendarController {

    @GetMapping("/calendar/{year}/{month}")
    @ResponseBody
    public String getICal(@PathVariable int year, @PathVariable int month) {
        Elements activeTD = getCalendarFromWeeia(year, month);
        Calendar calendar = new Calendar();



        java.util.Calendar calc = java.util.Calendar.getInstance();



        UidGenerator ug;
        try {
            ug = new UidGenerator("1");
        } catch (SocketException e) {
            e.printStackTrace();
        }


        activeTD.forEach((activeTD) => {
                calc.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
                calc.set(java.util.Calendar.DAY_OF_MONTH, 25);
                VEvent event = new VEvent(new Date(calc.getTime()), "Christmas Day");
                event.getProperties().add(ug.generateUid());
                calendar.getComponents().add(event);
        }));
    }

    private Elements getCalendarFromWeeia(Integer year, Integer month) {
        String content = null;
        URLConnection connection = null;
        StringBuilder urlString = new StringBuilder("http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?");
        urlString.append("rok=")
                .append(year.toString())
                .append("&miesiac=")
                .append(month.toString())
                .append("&lang=1");


        try {
            connection = new URL(urlString.toString()).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Document doc = Jsoup.parse(content, "UTF-8");

        Elements activeTD = doc.select("td.active");

        Elements resultLinks = doc.select("h3.r > a"); // direct a after h3
        return activeTD;
    }
}
