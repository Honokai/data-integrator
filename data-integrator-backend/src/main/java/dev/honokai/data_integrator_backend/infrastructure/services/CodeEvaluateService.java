package dev.honokai.data_integrator_backend.infrastructure.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CodeEvaluateService {
    public static String evaluateCodeSyntax(String code) {
        String baseCodeTemplateToTestCode = "compile('''USER_CODE''', 'placeHolderSystemArgs', 'exec')";
        List<String> cmd = Arrays.asList("python", "-c", baseCodeTemplateToTestCode.replace("USER_CODE", code.replace("\"", "\"\"")));
        return run(cmd);
    }

    public static String testCodeAgainstContent(String code, String content) {
        String escapedContent = content.replace("\"", "\"\"");
        List<String> cmd = Arrays.asList("python", "-c", code.replace("\"", "\"\""), escapedContent);
        System.out.println(String.join(" ", cmd));
        return run(cmd);
    }

    private static String run(List<String> command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        processBuilder.command(command);

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
            throw new RuntimeException("Tester");
        }
    }
}
