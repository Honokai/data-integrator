package dev.honokai.data_integrator_backend.application.services;

import dev.honokai.data_integrator_backend.domain.enums.SourceType;
import dev.honokai.data_integrator_backend.domain.interfaces.FileSourceStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FileSourceStrategyResolver {

    private final Map<String, FileSourceStrategy> strategies;

    public FileSourceStrategyResolver(List<FileSourceStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(FileSourceStrategy::getStrategyName, Function.identity()));
    }

    public FileSourceStrategy getStrategy(SourceType sourceType) {
        return Optional.ofNullable(strategies.get(sourceType.name()))
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma estratégia encontrada para o tipo: " + sourceType));
    }
}
