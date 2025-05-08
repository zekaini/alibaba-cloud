package com.gdtopway.topway.act.mapper;

import com.gdtopway.topway.common.data.datascope.TopwayBaseMapper;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 请假流程
 *
 * @author 冷冷
 * @date 2018-09-27 15:20:44
 */
@Mapper
public interface ActTaskMapper extends TopwayBaseMapper<ModelEntity> {

	void delTasks(@Param("ids") String[] ids);

}
