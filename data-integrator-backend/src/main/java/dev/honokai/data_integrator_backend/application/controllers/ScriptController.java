package dev.monitor.tasks.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.monitor.tasks.application.dtos.CodeEvaluateDto;
import dev.monitor.tasks.application.dtos.CodeEvaluateResponse;
import dev.monitor.tasks.application.services.ScriptService;

@RestController
@RequestMapping("/api/scripts")
public class ScriptController {
	@Autowired
	private ScriptService scriptService;

	@PostMapping("/evalute")
	public ResponseEntity<CodeEvaluateResponse> evaluateCode(@RequestBody CodeEvaluateDto codeEvaluate) {
		String syntaxEvaluationResult = scriptService.evaluateCodeSyntax(codeEvaluate.getCode());

		return ResponseEntity.ok(
				new CodeEvaluateResponse(syntaxEvaluationResult, syntaxEvaluationResult.length() > 0 ? false : true));
	}
}
