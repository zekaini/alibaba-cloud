/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.gdtopway.topway.act.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author felixu
 * @Date 2018.08.14
 */
@Data
public class StartTaskForm {

	@NotEmpty
	public String procDefKey;

	public String entityId;

	@NotEmpty
	public String title;

	public String comment;

}
