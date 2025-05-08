package com.gdtopway.topway.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdtopway.topway.admin.api.entity.SysTenantMenu;

public interface SysTenantMenuService extends IService<SysTenantMenu> {

	Boolean saveTenant(SysTenantMenu sysTenantMenu);

}
