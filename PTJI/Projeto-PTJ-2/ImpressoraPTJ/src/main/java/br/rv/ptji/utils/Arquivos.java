package br.rv.ptji.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


/**
 *
 * @author rafael
 */

public class Arquivos {

    public static void CriarArquioX(String nome, String caminho, String log) throws IOException {
        String nomedoarquivo = (caminho + "/" + nome + ".txt");
        Path path = Paths.get(nomedoarquivo);
        try (BufferedWriter escritor = Files.newBufferedWriter(path, Charset.defaultCharset(), StandardOpenOption.APPEND)) {
                escritor.append(log);
                escritor.newLine();
        }
    }
}
