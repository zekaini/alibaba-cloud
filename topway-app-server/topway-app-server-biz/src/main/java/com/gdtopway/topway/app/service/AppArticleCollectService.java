package com.gdtopway.topway.app.service;

import com.github.yulichang.base.MPJBaseService;
import com.gdtopway.topway.app.api.entity.AppArticleCollectEntity;

public interface AppArticleCollectService extends MPJBaseService<AppArticleCollectEntity> {

	Boolean saveArticleCollect(AppArticleCollectEntity appArticleCollect);

}
