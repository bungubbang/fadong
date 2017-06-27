package com.fadong.bot.service;

import com.fadong.bot.controller.request.MessageRequest;
import com.fadong.bot.controller.response.MessageResponse;
import com.fadong.bot.domain.MessageButton;
import com.fadong.bot.domain.Photo;
import org.springframework.stereotype.Service;

/**
 * Created by bungubbang on 07/06/2017.
 */
@Service
public class PlaceService extends MessageService {

    private static final String DORO = "경기도 화성시 동탄기흥로353번길 77";

    @Override
    public String text(MessageRequest request) {
        return "도로명 주소를 알려 드릴께요.\n"
                    + "우편번호 : 18480\n"
                    + DORO;
    }

    @Override
    public Photo photo() {
        return null;
    }

    @Override
    public MessageButton messageButton() {
        MessageButton messageButton = new MessageButton();
        messageButton.setLabel("자세히보기");
        messageButton.setUrl("https://m.land.naver.com/complex/info/109947?ptpNo=1");
        return messageButton;
    }
}
