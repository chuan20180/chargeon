package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.NotifyMessageRepository;
import com.obast.charer.data.business.INotifyMessageData;
import com.obast.charer.data.model.TbNotifyMessage;
import com.obast.charer.model.notify.NotifyMessage;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 石恒
 * @Date: 2023/5/13 18:35
 * @Description:
 */
@Primary
@Service
public class NotifyMessageDataImpl implements INotifyMessageData, IJPACommData<NotifyMessage, Long> {

    @Resource
    private NotifyMessageRepository notifyMessageRepository;


    @Override
    public JpaRepository getBaseRepository() {
        return notifyMessageRepository;
    }

    @Override
    public Class getJpaRepositoryClass() {
        return TbNotifyMessage.class;
    }

    @Override
    public Class getTClass() {
        return NotifyMessage.class;
    }

}
