package com.fadong.bot.service;

import com.fadong.bot.controller.request.MessageRequest;
import com.fadong.bot.domain.MessageButton;
import com.fadong.bot.domain.Photo;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;

/**
 * Created by bungubbang on 07/06/2017.
 */
@Service
public class MartService extends MessageService {


    public MartService() {
    }

    @Override
    public String text(MessageRequest request) {
        DateTime now = DateTime.now();
        Integer day = now.dayOfMonth().get();
        DateTime firstRestDay = firstRestDay(now);
        DateTime secondRestDay = secondRestDay(now);

        String text = "[동탄이마트, 코스트코공세점]\n"
                + "오늘(" + day + "일) 휴무일";

        if(day.equals(firstRestDay.dayOfMonth().get()) || day.equals(secondRestDay.dayOfMonth().get())) {
            text += "이예요.\n" + "오산이마트로 가시는건 어떨까요?\n";

        } else {
            text += "이 아니예요.\n";
        }
        text += restDay(now);
        return text;
    }

    @Override
    public Photo photo() {
        return null;
    }

    @Override
    public MessageButton messageButton() {
        return null;
    }

    private String restDay(DateTime now) {
        return "\n" + firstRestDay(now).toString("MM월 dd일 휴무")
                + "\n" + secondRestDay(now).toString("MM월 dd일 휴무");
    }

    private DateTime firstRestDay(DateTime now) {
        DateTime startDayOfMonth = DateTime.parse(now.toString("yyyy-MM-01"));
        int dayOfWeek = startDayOfMonth.getDayOfWeek();
        return startDayOfMonth.plusDays(14 - dayOfWeek);
    }

    private DateTime secondRestDay(DateTime now) {
        DateTime startDayOfMonth = DateTime.parse(now.toString("yyyy-MM-01"));
        int dayOfWeek = startDayOfMonth.getDayOfWeek();
        return startDayOfMonth.plusDays(28 - dayOfWeek);
    }
}
