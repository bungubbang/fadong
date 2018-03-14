package com.fadong.bot.service;

import com.fadong.bot.controller.request.MessageRequest;
import com.fadong.bot.domain.MessageButton;
import com.fadong.bot.domain.Photo;
import org.springframework.stereotype.Service;

/**
 * Created by bungubbang on 07/06/2017.
 */
@Service
public class ManageService extends MessageService {

    @Override
    public String text(MessageRequest request) {
        return "관리사무소 전화번호를 알려 드릴께요.\n"
                + "031-377-3905";
    }

    @Override
    public Photo photo() {
        return null;
    }

    @Override
    public MessageButton messageButton() {
        MessageButton messageButton = new MessageButton();
        messageButton.setLabel("공식까페 바로가기");
        messageButton.setUrl("http://m.cafe.naver.com/eileengarden1");
        return messageButton;
    }
}
