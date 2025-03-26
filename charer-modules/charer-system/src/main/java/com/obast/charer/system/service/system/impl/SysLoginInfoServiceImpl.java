package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.Constants;
import com.obast.charer.common.log.event.LogininforEvent;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.utils.ip.AddressUtils;
import com.obast.charer.common.web.utils.ServletUtils;
import com.obast.charer.data.system.ISysLoginInfoData;
import com.obast.charer.system.dto.bo.SysLoginInfoBo;
import com.obast.charer.system.dto.vo.SysLoginInfoVo;
import com.obast.charer.model.system.SysLoginInfo;
import com.obast.charer.system.service.system.ISysLoginInfoService;
import com.obast.charer.qo.SysLoginInfoQueryBo;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SysLoginInfoServiceImpl implements ISysLoginInfoService {

    private final ISysLoginInfoData sysLoginInfoData;


    /**
     * 查询系统登录日志集合
     */
    @Override
    public List<SysLoginInfoVo> queryList(SysLoginInfoQueryBo bo) {
        return  MapstructUtils.convert(sysLoginInfoData.findList(bo), SysLoginInfoVo.class);

    }

    /**
     * 记录登录信息
     */
    @Override
    public void recordLoginInfo(LogininforEvent logininforEvent) {
        HttpServletRequest request = logininforEvent.getRequest();
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));


        String ip = request.getRemoteAddr();


        String address = AddressUtils.getRealAddressByIP(ip);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(ip));
        s.append(address);
        s.append(getBlock(logininforEvent.getUsername()));
        s.append(getBlock(logininforEvent.getStatus()));
        s.append(getBlock(logininforEvent.getMessage()));
        // 打印信息到日志
        log.info(s.toString(), logininforEvent.getArgs());
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLoginInfoBo logininfor = new SysLoginInfoBo();
        logininfor.setTenantId(logininforEvent.getTenantId());
        logininfor.setUserName(logininforEvent.getUsername());
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(address);
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(logininforEvent.getMessage());
        // 日志状态
        if (StringUtils.equalsAny(logininforEvent.getStatus(), Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(logininforEvent.getStatus())) {
            logininfor.setStatus(Constants.FAIL);
        }
        // 插入数据
        insertLogininfor(logininfor);
    }

    private String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }



    /**
     * 新增系统登录日志
     *
     * @param bo 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLoginInfoBo bo) {
        SysLoginInfo sysLoginInfo = MapstructUtils.convert(bo, SysLoginInfo.class);
        if(sysLoginInfo != null) {
            sysLoginInfo.setLoginTime(new Date());
        }
        sysLoginInfoData.save(sysLoginInfo);
    }


    /**
     * 批量删除系统登录日志
     */
    @Override
    public void deleteLogininforByIds(Collection<String> infoIds) {
        sysLoginInfoData.deleteByIds(infoIds);
    }



    @Override
    public Paging<SysLoginInfoVo> findAll(PageRequest<SysLoginInfoBo> query) {
        return sysLoginInfoData.findAll(query.to(SysLoginInfo.class)).to(SysLoginInfoVo.class);
    }
}
