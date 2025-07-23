package dev.honokai.data_integrator_backend.domain.interfaces;

import dev.honokai.data_integrator_backend.domain.entities.Task;

import java.io.File;
import java.util.List;

public interface FileSourceStrategy {
    List<File> findFiles(Task task);

    String getStrategyName();
}
