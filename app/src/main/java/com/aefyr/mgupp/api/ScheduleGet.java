package com.aefyr.mgupp.api;

import android.util.Log;

import com.aefyr.mgupp.api.model.Day;
import com.aefyr.mgupp.api.model.Lesson;
import com.aefyr.mgupp.api.model.Schedule;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

/**
 * Created by Aefyr on 06.10.2018.
 */
public class ScheduleGet extends ApiRequest<Schedule> {
    private Pattern p1 = Pattern.compile(".*?<section id=\"content-tab1\">([\\w\\W]*?)</section>.*?");//Work area
    private Pattern p2 = Pattern.compile(".*?<p>.*?Учебная группа:(.*?)</br>.*?</p>.*?");//Header
    private Pattern p3 = Pattern.compile(".*?<H2>(.*?)</H2>(.*?)</table>.*?", Pattern.DOTALL);//Day
    private Pattern p4 = Pattern.compile(".*?<tr>(.*?)</tr>.*?", Pattern.DOTALL);//Lesson in Day
    private Pattern p5 = Pattern.compile(".*?<div.*?>(.*?)</div>.*?", Pattern.DOTALL);//Column in Lesson
    private Pattern p6 = Pattern.compile(".*?<a href=\"(.+?)\">(.+?)</a>.*?", Pattern.DOTALL);//Teacher data

    public ScheduleGet(String scheduleId) {
        super("http://www.mgupp.ru/obuchayushchimsya/raspisanie/GetShedule.php?MyRes=" + scheduleId);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected Schedule parse(Response response) throws Exception {
        String raw = response.body().string();
        response.close();

        long start = System.currentTimeMillis();

        Matcher m1 = p1.matcher(raw);
        m1.find();
        raw = m1.group(1);

        Matcher m2 = p2.matcher(raw);
        m2.find();
        String groupName = m2.group(1);

        Schedule.Builder scheduleBuilder = new Schedule.Builder().groupName(groupName);

        ArrayList<Day> days = new ArrayList<>();
        Matcher m3 = p3.matcher(raw);
        while (m3.find()) {
            String dayName = m3.group(1);
            String rawDay = m3.group(2);

            Matcher m4 = p4.matcher(rawDay);

            ArrayList<Lesson> lessons = new ArrayList<>();
            m4.find();//Skipping header
            while (m4.find()) {
                Matcher m5 = p5.matcher(m4.group(1));
                m5.find();
                String lessonTimes = m5.group(1);

                m5.find();
                String lessonName = m5.group(1);

                m5.find();
                String lessonRoom = m5.group(1);

                m5.find();
                String rawTeacher = m5.group(1);
                Matcher m6 = p6.matcher(rawTeacher);
                m6.find();
                String lessonTeacher = m6.group(2);

                lessons.add(new Lesson(lessonName, lessonRoom, lessonTimes, lessonTeacher));
            }

            days.add(new Day(dayName, lessons));
        }

        scheduleBuilder.days(days);
        Log.d("MGUPP", "regex schedule in " + (System.currentTimeMillis() - start));
        return scheduleBuilder.build();
    }
}
