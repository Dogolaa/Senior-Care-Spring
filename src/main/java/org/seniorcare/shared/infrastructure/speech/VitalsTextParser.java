package org.seniorcare.shared.infrastructure.speech;

import org.seniorcare.shared.api.rest.dto.speech.VitalsTranscriptionResponse;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VitalsTextParser {

    private static final String SEP = "\\s*[-:–,]?\\s*";

    private static final Pattern BLOOD_PRESSURE = Pattern.compile(
            "(?i)(?:press[ãa]o\\s*arterial|press[ãa]o|\\bpa\\b)" + SEP + "(\\d+)\\s*(?:por|/)\\s*(\\d+)"
    );

    private static final Pattern WEIGHT = Pattern.compile(
            "(?i)\\bpeso" + SEP + "(\\d+(?:[,.]\\d+)?)"
    );

    private static final Pattern TEMPERATURE = Pattern.compile(
            "(?i)(?:temperatura|febre)" + SEP + "(\\d+(?:[,.]\\d+)?)"
    );

    private static final Pattern HEART_RATE = Pattern.compile(
            "(?i)(?:freq[uü][eê]ncia\\s*card[ií]aca|batimentos\\s*card[ií]acos|batimentos|pulso|\\bfc\\b|\\bbpm\\b)" + SEP + "(\\d+)"
    );

    private static final Pattern SATURATION = Pattern.compile(
            "(?i)(?:satura[çc][ãa]o|spo2|\\bsat\\b)" + SEP + "(\\d+(?:[,.]\\d+)?)"
    );

    // Captura a parte inteira e, opcionalmente, tudo após "e"/"vírgula" como a parte decimal
    private static final Pattern HEIGHT = Pattern.compile(
            "(?i)\\baltura" + SEP +
            "(um|dois|\\d+)" +
            "(?:\\s*(?:e|vírgula|virgula|,)\\s*(.+?))?" +
            "(?=[.,!?;\\n]|$)"
    );

    // Ordem importa: compostos antes dos simples
    private static final Map<String, Integer> PT_NUMBERS = new LinkedHashMap<>();
    static {
        PT_NUMBERS.put("noventa e nove", 99); PT_NUMBERS.put("noventa e oito", 98);
        PT_NUMBERS.put("noventa e sete", 97); PT_NUMBERS.put("noventa e seis", 96);
        PT_NUMBERS.put("noventa e cinco", 95); PT_NUMBERS.put("noventa e quatro", 94);
        PT_NUMBERS.put("noventa e três", 93); PT_NUMBERS.put("noventa e tres", 93);
        PT_NUMBERS.put("noventa e dois", 92); PT_NUMBERS.put("noventa e um", 91);
        PT_NUMBERS.put("noventa", 90);
        PT_NUMBERS.put("oitenta e nove", 89); PT_NUMBERS.put("oitenta e oito", 88);
        PT_NUMBERS.put("oitenta e sete", 87); PT_NUMBERS.put("oitenta e seis", 86);
        PT_NUMBERS.put("oitenta e cinco", 85); PT_NUMBERS.put("oitenta e quatro", 84);
        PT_NUMBERS.put("oitenta e três", 83); PT_NUMBERS.put("oitenta e tres", 83);
        PT_NUMBERS.put("oitenta e dois", 82); PT_NUMBERS.put("oitenta e um", 81);
        PT_NUMBERS.put("oitenta", 80);
        PT_NUMBERS.put("setenta e nove", 79); PT_NUMBERS.put("setenta e oito", 78);
        PT_NUMBERS.put("setenta e sete", 77); PT_NUMBERS.put("setenta e seis", 76);
        PT_NUMBERS.put("setenta e cinco", 75); PT_NUMBERS.put("setenta e quatro", 74);
        PT_NUMBERS.put("setenta e três", 73); PT_NUMBERS.put("setenta e tres", 73);
        PT_NUMBERS.put("setenta e dois", 72); PT_NUMBERS.put("setenta e um", 71);
        PT_NUMBERS.put("setenta", 70);
        PT_NUMBERS.put("sessenta e nove", 69); PT_NUMBERS.put("sessenta e oito", 68);
        PT_NUMBERS.put("sessenta e sete", 67); PT_NUMBERS.put("sessenta e seis", 66);
        PT_NUMBERS.put("sessenta e cinco", 65); PT_NUMBERS.put("sessenta e quatro", 64);
        PT_NUMBERS.put("sessenta e três", 63); PT_NUMBERS.put("sessenta e tres", 63);
        PT_NUMBERS.put("sessenta e dois", 62); PT_NUMBERS.put("sessenta e um", 61);
        PT_NUMBERS.put("sessenta", 60);
        PT_NUMBERS.put("cinquenta e nove", 59); PT_NUMBERS.put("cinquenta e oito", 58);
        PT_NUMBERS.put("cinquenta e sete", 57); PT_NUMBERS.put("cinquenta e seis", 56);
        PT_NUMBERS.put("cinquenta e cinco", 55); PT_NUMBERS.put("cinquenta e quatro", 54);
        PT_NUMBERS.put("cinquenta e três", 53); PT_NUMBERS.put("cinquenta e tres", 53);
        PT_NUMBERS.put("cinquenta e dois", 52); PT_NUMBERS.put("cinquenta e um", 51);
        PT_NUMBERS.put("cinquenta", 50);
        PT_NUMBERS.put("quarenta e nove", 49); PT_NUMBERS.put("quarenta e oito", 48);
        PT_NUMBERS.put("quarenta e sete", 47); PT_NUMBERS.put("quarenta e seis", 46);
        PT_NUMBERS.put("quarenta e cinco", 45); PT_NUMBERS.put("quarenta e quatro", 44);
        PT_NUMBERS.put("quarenta e três", 43); PT_NUMBERS.put("quarenta e tres", 43);
        PT_NUMBERS.put("quarenta e dois", 42); PT_NUMBERS.put("quarenta e um", 41);
        PT_NUMBERS.put("quarenta", 40);
    }

    public VitalsTranscriptionResponse parse(String text) {
        if (text == null || text.isBlank()) {
            return new VitalsTranscriptionResponse(text, null, null, null, null, null, null);
        }

        return new VitalsTranscriptionResponse(
                text,
                parseBloodPressure(text),
                parseFloat(text, WEIGHT),
                parseHeight(text),
                parseFloat(text, TEMPERATURE),
                parseInteger(text, HEART_RATE),
                parseFloat(text, SATURATION)
        );
    }

    private String parseBloodPressure(String text) {
        Matcher m = BLOOD_PRESSURE.matcher(text);
        if (!m.find()) return null;
        int systolic = expandBloodPressure(Integer.parseInt(m.group(1)));
        int diastolic = expandBloodPressure(Integer.parseInt(m.group(2)));
        return systolic + "/" + diastolic;
    }

    // Em pt-BR a pressão é falada na forma curta: "12 por 8" = 120/80, "14 por 9" = 140/90.
    // Valores < 30 são a forma reduzida e precisam ser multiplicados por 10.
    private int expandBloodPressure(int value) {
        return value < 30 ? value * 10 : value;
    }

    private Float parseFloat(String text, Pattern pattern) {
        Matcher m = pattern.matcher(text);
        if (!m.find()) return null;
        try {
            return Float.parseFloat(m.group(1).replace(',', '.'));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(String text, Pattern pattern) {
        Matcher m = pattern.matcher(text);
        if (!m.find()) return null;
        try {
            return Integer.parseInt(m.group(1));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Float parseHeight(String text) {
        Matcher m = HEIGHT.matcher(text);
        if (!m.find()) return null;

        String intStr = m.group(1).trim().toLowerCase();
        String decStr = m.group(2);

        int intPart;
        try {
            intPart = switch (intStr) {
                case "um" -> 1;
                case "dois" -> 2;
                default -> Integer.parseInt(intStr);
            };
        } catch (NumberFormatException e) {
            return null;
        }

        // Sem parte decimal: pode ser cm direto (ex: "altura, 176")
        if (decStr == null) {
            float h = intPart;
            return h > 3 ? h / 100f : h;
        }

        Integer decPart = parseDecimalPart(decStr.trim().toLowerCase());
        if (decPart == null) return null;

        return Float.parseFloat(intPart + "." + String.format("%02d", decPart));
    }

    // Converte a parte decimal da altura: "76", "noventa", "setenta e seis", etc.
    private Integer parseDecimalPart(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ignored) {}

        for (Map.Entry<String, Integer> entry : PT_NUMBERS.entrySet()) {
            if (text.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
