package com.kingroad.pulsar.controller.uo;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingroad.pulsar.audit.Audit;
import com.kingroad.pulsar.auth.bo.LoginUser;
import com.kingroad.pulsar.constant.OperateType;
import com.kingroad.pulsar.entity.uo.SysUser;
import com.kingroad.pulsar.res.Result;
import com.kingroad.pulsar.service.mr.SysRoleService;
import com.kingroad.pulsar.service.uo.SysUserService;
import com.kingroad.pulsar.util.PermissionUtil;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:50
 * @Version: v1.0
 * @Description: 用户管理
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private SysUserService userService;

    @Resource
    private PasswordEncoder encoder;

    @Resource
    private SysRoleService roleService;

    /**
     * 用户列表页面
     */
    @PreAuthorize("@permissionUtil.hasPerm('user:list')")
    @GetMapping("/list")
    public String listPage() {
        return "user/list";
    }

    /**
     * 分页接口
     */
    @Audit(module = "分页查询用户", operationType = OperateType.QUERY)
    @PreAuthorize("@permissionUtil.hasPerm('user:list')") // 注解需要list权限
    @GetMapping("/page")
    @ResponseBody
    public Result<Page<SysUser>> page(@RequestParam(defaultValue = "1") Long pageNum, @RequestParam(defaultValue = "10") Long pageSize, String username, String chineseName) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        if(StrUtil.isNotBlank(username)) wrapper.like(SysUser::getUsername, username);
        if(StrUtil.isNotBlank(chineseName)) wrapper.like(SysUser::getChineseName, chineseName);

        Page<SysUser> data = userService.page(page, wrapper);
        return Result.success(data);
    }

    /**
     * 删除
     */
    @Audit(module = "删除用户", operationType = OperateType.DELETE, saveOldData = true)
    @PreAuthorize("@permissionUtil.hasPerm('user:delete')")
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Result<?> delete(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }

    /**
     * 新增：编辑用户时回显已选角色ID
     */
    @Audit(module = "编辑用户时回显已选角色", operationType = OperateType.QUERY, saveOldData = true)
    @GetMapping("/roleIds/{userId}")
    @ResponseBody
    public Result<List<Long>> getRoleIds(@PathVariable Long userId) {
        List<Long> ids = roleService.getRoleIdsByUserId(userId);
        return Result.success(ids);
    }

    /**
     * 修改用户保存接口，新增角色批量绑定
     */
    @Audit(module = "修改用户信息", operationType = OperateType.UPDATE, saveOldData = true)
    @PostMapping("/update")
    @ResponseBody
    @PreAuthorize("@permissionUtil.hasPerm('user:update')")
    public Result<?> update(SysUser user, @RequestParam(required = false) List<Long> roleIdList) {
        if (StrUtil.isBlank(user.getPasswordHash())) {
            user.setPasswordHash(null);
        } else {
            user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        }
        userService.updateById(user);
        // 保存用户角色关联
        if(user.getId() != null){
            roleService.saveUserRole(user.getId(), roleIdList);
        }
        return Result.success();
    }

    /**
     * 新增用户同步绑定角色
     */
    @Audit(module = "新增用户信息", operationType = OperateType.CREATE, saveOldData = true)
    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("@permissionUtil.hasPerm('user:add')")
    public Result<?> add(SysUser user, @RequestParam(required = false) List<Long> roleIdList) {
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        user.setLastLoginTime(null);
        userService.save(user);
        // 绑定角色
        roleService.saveUserRole(user.getId(), roleIdList);
        return Result.success();
    }

    /**
     * 获取当前登录用户自身信息（个人中心弹窗使用）
     */
    @Audit(module = "查询个人信息", operationType = OperateType.QUERY)
    @GetMapping("/getSelfInfo")
    @ResponseBody
    public Result<SysUser> getSelfInfo(){
        LoginUser loginUser = PermissionUtil.getLoginUser();
        if(loginUser == null){
            return Result.fail("未登录");
        }
        SysUser user = loginUser.getUser();
        // 清空密码返回前端，避免泄露密文
        user.setPasswordHash(null);
        return Result.success(user);
    }

    /**
     * 当前登录用户修改个人信息（仅可修改中文名、手机、邮箱、密码）
     */
    @Audit(module = "更新个人信息", operationType = OperateType.UPDATE, saveOldData = true)
    @PostMapping("/updateSelf")
    @ResponseBody
    public Result<?> updateSelf(SysUser user){
        LoginUser loginUser = PermissionUtil.getLoginUser();
        if(loginUser == null){
            return Result.fail("未登录");
        }
        Long loginUserId = loginUser.getUser().getId();
        // 只能修改自己，禁止篡改别人ID
        if(!loginUserId.toString().equals(user.getId().toString())){
            return Result.fail("无权修改他人信息");
        }
        // 登录名、是否超管禁止修改
        user.setUsername(null);
        user.setIsSuperAdmin(null);
        if(StrUtil.isNotBlank(user.getPasswordHash())){
            user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        }else{
            user.setPasswordHash(null);
        }
        userService.updateById(user);
        return Result.success("修改成功");
    }
}
