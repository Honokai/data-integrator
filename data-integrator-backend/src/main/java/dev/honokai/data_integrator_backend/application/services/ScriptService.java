package dev.honokai.data_integrator_backend.application.services;

import dev.honokai.data_integrator_backend.domain.entities.Script;
import dev.honokai.data_integrator_backend.infrastructure.repositories.ScriptRepository;
import dev.honokai.data_integrator_backend.infrastructure.services.CodeEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScriptService {
    @Autowired
    private ScriptRepository scriptRepository;

    public String evaluateCodeSyntax(String codeToEvaluate) {
        return CodeEvaluateService.evaluateCodeSyntax(codeToEvaluate);
    }

    public Optional<Script> getActiveScriptForTask(String task) {
        return scriptRepository.findByTaskIdAndActiveTrue(task);
    }
}
