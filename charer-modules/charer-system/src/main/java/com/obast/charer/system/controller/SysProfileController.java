package com.obast.charer.system.controller;

import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.utils.file.MimeTypeUtils;
import com.obast.charer.common.web.core.BaseController;

import com.obast.charer.system.dto.bo.SysChangePwdBo;
import com.obast.charer.system.dto.bo.SysUserBo;
import com.obast.charer.system.dto.bo.SysUserProfileBo;
import com.obast.charer.system.dto.vo.AvatarVo;
import com.obast.charer.system.dto.vo.ProfileVo;
import com.obast.charer.system.dto.vo.SysOssVo;
import com.obast.charer.system.dto.vo.SysUserVo;
import com.obast.charer.system.service.system.ISysOssService;
import com.obast.charer.system.service.system.ISysUserManageService;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * 个人信息 业务处理
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/user/profile")
public class SysProfileController extends BaseController {

    private final ISysUserManageService userService;
    private final ISysOssService ossService;

    /**
     * 个人信息
     */

    @ApiOperation(value = "个人信息", notes = "个人信息")
    @PostMapping("/getDetail")
    public ProfileVo profile() {
        SysUserVo user = userService.selectUserById(LoginHelper.getUserId());
        ProfileVo profileVo = new ProfileVo();
        profileVo.setUser(user);
//        profileVo.setRoleGroup(userService.selectUserRoleGroup(user.getUserName()));
//        profileVo.setPostGroup(userService.selectUserPostGroup(user.getUserName()));
        return profileVo;
    }

    /**
     * 修改用户
     */
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody Request<SysUserProfileBo> bo) {
        SysUserProfileBo profile = bo.getData();
        SysUserBo user = BeanUtil.toBean(profile, SysUserBo.class);

        user.setId(LoginHelper.getUserId());
        if (userService.updateUserProfile(user) > 0) {
            return;
        }
        fail("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     *
     */
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updatePwd")
    public void updatePwd(@RequestBody @Validated Request<SysChangePwdBo> bo) {
        SysChangePwdBo data = bo.getData();
        String newPassword = data.getNewPassword();
        String oldPassword = data.getOldPassword();
        SysUserVo user = userService.selectUserById(LoginHelper.getUserId());
        String password = user.getPassword();
        if (!BCrypt.checkpw(oldPassword, password)) {
            fail("修改密码失败，旧密码错误");
        }
        if (BCrypt.checkpw(newPassword, password)) {
            fail("新密码不能与旧密码相同");
        }

        if (userService.resetUserPwd(user.getId(), BCrypt.hashpw(newPassword)) > 0) {
            return;
        }
        fail("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     *
     * @param avatarfile 用户头像
     */
    @ApiOperation(value = "头像上传", notes = "头像上传")
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AvatarVo avatar(@RequestPart("avatarfile") MultipartFile avatarfile,
                           @RequestParam("requestId") String requestId) {
        if (!avatarfile.isEmpty()) {
            String extension = FileUtil.extName(avatarfile.getOriginalFilename());
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                fail("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
            SysOssVo oss = ossService.upload(avatarfile);
            String avatar = oss.getUrl();
            if (userService.updateUserAvatar(LoginHelper.getUserId(), oss.getId())) {
                AvatarVo avatarVo = new AvatarVo();
                avatarVo.setImgUrl(avatar);
                return avatarVo;
            }
        }
        fail("上传图片异常，请联系管理员");
        return null;
    }
}
