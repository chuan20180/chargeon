package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbNotifyMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 石恒
 * @Date: 2023/5/13 18:36
 * @Description:
 */
public interface NotifyMessageRepository extends JpaRepository<TbNotifyMessage, String> {

}
