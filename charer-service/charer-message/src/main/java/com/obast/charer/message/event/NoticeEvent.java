package com.obast.charer.message.event;

import com.obast.charer.model.system.Notice;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * author: 石恒
 * date: 2023-05-11 14:14
 * description:
 **/

@Setter
@Getter
public class NoticeEvent extends ApplicationEvent {

    private Notice notice;

    public NoticeEvent(Notice notice) {
        super(notice);
        this.notice = notice;
    }

}
