package com.gdtopway.topway.app.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdtopway.topway.app.api.entity.AppArticleCollectEntity;
import com.gdtopway.topway.app.mapper.AppArticleCollectMapper;
import com.gdtopway.topway.app.service.AppArticleCollectService;
import com.gdtopway.topway.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 文章收藏表
 *
 * @author pig
 * @date 2023-06-16 14:33:41
 */
@Service
public class AppArticleCollectServiceImpl extends ServiceImpl<AppArticleCollectMapper, AppArticleCollectEntity>
		implements AppArticleCollectService {

	@Override
	public Boolean saveArticleCollect(AppArticleCollectEntity appArticleCollect) {
		Long id = SecurityUtils.getUser().getId();
		appArticleCollect.setUserId(id);

		this.saveOrUpdate(appArticleCollect,
				Wrappers.<AppArticleCollectEntity>lambdaQuery().eq(AppArticleCollectEntity::getUserId, id)
						.eq(AppArticleCollectEntity::getArticleId, appArticleCollect.getArticleId()));

		return Boolean.TRUE;
	}

}
