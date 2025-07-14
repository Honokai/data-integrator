package dev.honokai.data_integrator_backend.application.controllers;

import dev.honokai.data_integrator_backend.application.dtos.CodeEvaluateDto;
import dev.honokai.data_integrator_backend.application.dtos.CodeEvaluateResponse;
import dev.honokai.data_integrator_backend.application.services.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
