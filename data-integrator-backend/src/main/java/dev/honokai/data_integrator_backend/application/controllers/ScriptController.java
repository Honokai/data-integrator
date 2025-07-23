package dev.honokai.data_integrator_backend.application.controllers;

import dev.honokai.data_integrator_backend.application.dtos.CodeEvaluateDto;
import dev.honokai.data_integrator_backend.application.dtos.CodeEvaluateResponse;
import dev.honokai.data_integrator_backend.application.dtos.TestCodeDto;
import dev.honokai.data_integrator_backend.application.dtos.script.ScriptPostDto;
import dev.honokai.data_integrator_backend.application.dtos.script.ScriptResponseDto;
import dev.honokai.data_integrator_backend.application.services.ScriptService;
import dev.honokai.data_integrator_backend.infrastructure.services.CodeEvaluateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scripts")
public class ScriptController {
    private final ScriptService scriptService;

    ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<CodeEvaluateResponse> evaluateCode(@RequestBody CodeEvaluateDto codeEvaluate) {
        String syntaxEvaluationResult = CodeEvaluateService.evaluateCodeSyntax(codeEvaluate.getCode());

        return ResponseEntity.ok(
                new CodeEvaluateResponse(syntaxEvaluationResult, syntaxEvaluationResult.length() <= 0));
    }

    @PostMapping("/test")
    public ResponseEntity<CodeEvaluateResponse> testCodeAgainstContent(@RequestBody TestCodeDto testCodeDto) {
        String syntaxEvaluationResult = CodeEvaluateService.testCodeAgainstContent(testCodeDto.getCode(), testCodeDto.getContentToTestAgainst());

        return ResponseEntity.ok(
                new CodeEvaluateResponse(syntaxEvaluationResult, true));
    }

    @GetMapping("/{scriptId}")
    public ResponseEntity<ScriptResponseDto> edit(@PathVariable String scriptId) {
        return scriptService.findScriptById(scriptId)
                .map(sc ->
                        ResponseEntity.ok(new ScriptResponseDto(sc))
                ).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ScriptResponseDto> create(@Valid @RequestBody ScriptPostDto scriptPostDto) {
        ScriptResponseDto scriptResponseDto = new ScriptResponseDto(scriptService.create(scriptPostDto));

        return ResponseEntity.ok(scriptResponseDto);
    }

    @PutMapping("/{scriptId}")
    public ResponseEntity<ScriptResponseDto> update(@PathVariable String scriptId, @Valid @RequestBody ScriptPostDto scriptPostDto) {
        return ResponseEntity.accepted().body(new ScriptResponseDto(scriptService.update(scriptId, scriptPostDto)));
    }

    @GetMapping("/integrator-check")
    public void testIntegrator() {
        System.out.println("Endpoint acessado");
    }
}
