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

package com.gdtopway.topway.act.controller;

import com.gdtopway.topway.act.service.EditorService;
import com.gdtopway.topway.common.xss.core.XssCleanIgnore;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author lengleng
 * @date 2018/4/13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/service")
public class EditorController {

	private final EditorService editorService;

	@GetMapping("/editor/stencilset")
	public Object getStencilset() {
		return editorService.getStencilset();
	}

	@GetMapping(value = "/model/{modelId}/json")
	public Object getEditorJson(@PathVariable(value = "modelId") String modelId) {
		return editorService.getEditorJson(modelId);
	}

	@XssCleanIgnore
	@PutMapping("/model/{modelId}/save")
	public void saveModel(@PathVariable(value = "modelId") String modelId, String name, String description,
			@RequestParam("json_xml") String jsonXml, @RequestParam("svg_xml") String svgXml) {
		editorService.saveModel(modelId, name, description, jsonXml, svgXml);
	}

}
