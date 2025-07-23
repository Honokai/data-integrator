package dev.honokai.data_integrator_backend.infrastructure.filesources;

import dev.honokai.data_integrator_backend.domain.entities.Task;
import dev.honokai.data_integrator_backend.domain.enums.SourceType;
import dev.honokai.data_integrator_backend.domain.interfaces.FileSourceStrategy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;

@Component
public class NetworkShareScanner implements FileSourceStrategy {
    @Override
    public String getStrategyName() {
        return SourceType.NETWORK_SHARE.name();
    }

    @Override
    public List<File> findFiles(Task task) {
        String machineName = task.getMachine().getName();

        String fullPath = String.format("\\\\%s\\%s", machineName, task.getNetworkPath());

        System.out.printf("Machine: %s | Path: %s | RUN: %s%n", machineName, fullPath, new Date());

        File[] files = new File(fullPath).listFiles();

        return files != null ? List.of(files) : null;
    }
}
