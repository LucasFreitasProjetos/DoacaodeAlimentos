package br.com.doacaoalimentos.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;

import br.com.doacaoalimentos.model.Doacao;
import br.com.doacaoalimentos.model.Doador;
import br.com.doacaoalimentos.model.Instituicao;

public class JsonUtil {
    private static final Pattern STRING_FIELD = Pattern.compile("\"%s\"\\s*:\\s*\"([^\"]*)\"");
    private static final Pattern INT_FIELD = Pattern.compile("\"%s\"\\s*:\\s*(\\d+)");

    public static String readBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    public static String getString(String json, String key) {
        if (json == null) {
            return null;
        }
        Matcher matcher = Pattern.compile(String.format(STRING_FIELD.pattern(), Pattern.quote(key))).matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }

    public static Integer getInt(String json, String key) {
        if (json == null) {
            return null;
        }
        Matcher matcher = Pattern.compile(String.format(INT_FIELD.pattern(), Pattern.quote(key))).matcher(json);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException ex) {
                return null;
            }
        }
        return null;
    }

    public static String toJson(Doador doador) {
        if (doador == null) {
            return "null";
        }
        return "{" +
                "\"id\":" + doador.getId() +
                ",\"nome\":\"" + escape(doador.getNome()) + "\"" +
                ",\"email\":\"" + escape(doador.getEmail()) + "\"" +
                "}";
    }

    public static String toJson(Instituicao instituicao) {
        if (instituicao == null) {
            return "null";
        }
        return "{" +
                "\"id\":" + instituicao.getId() +
                ",\"nome\":\"" + escape(instituicao.getNome()) + "\"" +
                ",\"endereco\":\"" + escape(instituicao.getEndereco()) + "\"" +
                "}";
    }

    public static String toJson(Doacao doacao) {
        if (doacao == null) {
            return "null";
        }
        String data = doacao.getDataDoacao() != null ? doacao.getDataDoacao().toString() : "";
        return "{" +
                "\"id\":" + doacao.getId() +
                ",\"doadorId\":" + doacao.getDoadorId() +
                ",\"instituicaoId\":" + doacao.getInstituicaoId() +
                ",\"descricao\":\"" + escape(doacao.getDescricao()) + "\"" +
                ",\"dataDoacao\":\"" + escape(data) + "\"" +
                "}";
    }

    public static String toJson(List<Doador> doadores) {
        if (doadores == null) {
            return "[]";
        }
        List<String> jsonItems = new ArrayList<>();
        for (Doador doador : doadores) {
            jsonItems.add(toJson(doador));
        }
        return "[" + String.join(",", jsonItems) + "]";
    }

    public static String toJsonInstituicoes(List<Instituicao> instituicoes) {
        if (instituicoes == null) {
            return "[]";
        }
        List<String> jsonItems = new ArrayList<>();
        for (Instituicao instituicao : instituicoes) {
            jsonItems.add(toJson(instituicao));
        }
        return "[" + String.join(",", jsonItems) + "]";
    }

    public static String toJsonDoacoes(List<Doacao> doacoes) {
        if (doacoes == null) {
            return "[]";
        }
        List<String> jsonItems = new ArrayList<>();
        for (Doacao doacao : doacoes) {
            jsonItems.add(toJson(doacao));
        }
        return "[" + String.join(",", jsonItems) + "]";
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
