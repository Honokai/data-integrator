package dev.honokai.data_integrator_backend.infrastructure.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CodeRunnerService {
    public static String run(String code, String pathToFile) {
        List<String> cmd = Arrays.asList("python", "-c", code.replace("\"", "\"\""), pathToFile);
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);

        processBuilder.redirectErrorStream(true);
        processBuilder.command(cmd);

        try {
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            process.waitFor();


            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
//			throw new RuntimeException("Tester");
        }

        return "";
    }
}
