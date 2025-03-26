package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.bo.NoticeBo;
import com.obast.charer.system.dto.vo.notice.NoticeVo;
import com.obast.charer.system.service.business.INoticeManagerService;
import com.obast.charer.qo.NoticeQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：通知公告管理
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/notice")
public class NoticeController extends BaseController {

    private final INoticeManagerService noticeManagerService;

    @ApiOperation(value = "通知公告列表", notes = "通知公告列表")
    @SaCheckPermission("business:notice:list")
    @PostMapping("/list")
    public Paging<NoticeVo> list(@Validated @RequestBody PageRequest<NoticeQueryBo> pageRequest) {
        return noticeManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "通知公告详情", notes = "通知公告详情")
    @SaCheckPermission("business:notice:query")
    @PostMapping(value = "/detail")
    public NoticeVo detail(@Validated @RequestBody Request<String> bo) {
        return noticeManagerService.queryDetail(bo.getData());
    }

    @ApiOperation(value = "新增通知公告", notes = "新增通知公告")
    @SaCheckPermission("business:notice:add")
    @Log(title = "新增通知公告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public void add(@Validated @RequestBody Request<NoticeBo> bo) {
        noticeManagerService.add(bo.getData());
    }

    @SaCheckPermission("business:notice:edit")
    @Log(title = "修改通知公告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public void edit(@Validated @RequestBody Request<NoticeBo> bo) {
        noticeManagerService.update(bo.getData());
    }

    @ApiOperation(value = "发布")
    @SaCheckPermission("business:station:edit")
    @Log(title = "发布通知公告", businessType = BusinessType.UPDATE)
    @PostMapping("/publish")
    public void publish(@RequestBody Request<String> bo) {
        noticeManagerService.publish(bo.getData());
    }

    @ApiOperation("删除通知公告")
    @SaCheckPermission("business:notice:delete")
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return noticeManagerService.delete(request.getData());
    }

    @ApiOperation("批量删除通知公告")
    @SaCheckPermission("business:notice:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return noticeManagerService.batchDelete(request.getData());
    }
}
