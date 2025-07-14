package dev.monitor.tasks.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.monitor.tasks.domain.entities.Script;
import dev.monitor.tasks.infrastructure.repositories.ScriptRepository;
import dev.monitor.tasks.infrastructure.services.CodeEvaluateService;

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
