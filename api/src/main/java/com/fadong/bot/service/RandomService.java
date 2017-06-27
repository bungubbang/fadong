package com.fadong.bot.service;

import com.fadong.bot.controller.request.MessageRequest;
import com.fadong.bot.domain.MessageButton;
import com.fadong.bot.domain.Photo;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by bungubbang on 06/06/2017.
 */
@Service
public class RandomService extends MessageService {

    List<String> lists = Lists.newArrayList("하이", "무엇을 도와드릴까요?", "ㅠㅠ 제가 그리 똑똑하지 않아요.");

    @Override
    public String text(MessageRequest request) {
        return lists.get(new Random().nextInt(lists.size()));
    }

    @Override
    public Photo photo() {
        return null;
    }

    @Override
    public MessageButton messageButton() {
        return null;
    }
}
