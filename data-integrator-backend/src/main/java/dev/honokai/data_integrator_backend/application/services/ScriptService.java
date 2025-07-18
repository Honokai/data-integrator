package dev.honokai.data_integrator_backend.application.services;

import dev.honokai.data_integrator_backend.application.dtos.script.ScriptPostDto;
import dev.honokai.data_integrator_backend.domain.entities.Script;
import dev.honokai.data_integrator_backend.domain.entities.Task;
import dev.honokai.data_integrator_backend.infrastructure.repositories.ScriptRepository;
import dev.honokai.data_integrator_backend.infrastructure.repositories.TaskRepository;
import dev.honokai.data_integrator_backend.infrastructure.services.CodeEvaluateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScriptService {
    private final ScriptRepository scriptRepository;
    private final TaskRepository taskRepository;

    ScriptService(ScriptRepository scriptRepository, TaskRepository taskRepository) {
        this.scriptRepository = scriptRepository;
        this.taskRepository = taskRepository;
    }

    public String evaluateCodeSyntax(String codeToEvaluate) {
        return CodeEvaluateService.evaluateCodeSyntax(codeToEvaluate);
    }

    public Optional<Script> getActiveScriptForTask(String task) {
        return scriptRepository.findByTaskIdAndActiveTrue(task);
    }

    public List<Script> findScriptsFromTask(String task) {
        return scriptRepository.findByTaskId(task);
    }

    public Optional<Script> findScriptById(String scriptId) {
        return scriptRepository.findById(scriptId);
    }

    public Script create(ScriptPostDto scriptPostDto) {
        Optional<Task> task = taskRepository.findById(scriptPostDto.getTask());

        if (task.isEmpty()) {
            throw new RuntimeException("Task Not Found");
        }

        Script script = new Script(scriptPostDto.getVersion(), scriptPostDto.getInstruction(), task.get(), scriptPostDto.isActive());

        return scriptRepository.save(script);
    }

    public Script update(String scriptId, ScriptPostDto scriptPostDto) {
        Script script = scriptRepository.findById(scriptId).orElseThrow(EntityNotFoundException::new);

        script.setInstruction(scriptPostDto.getInstruction());
        script.setActive(scriptPostDto.isActive());

        return scriptRepository.save(script);
    }
}
