package br.rv.ptjs.utils;

import java.util.ArrayList;
import java.util.List;

public class Log {

    private final List<String> Log = new ArrayList<String>();

    public void setLinha(String linha) {
        Log.add(linha);
    }

    public String getListaComPosi(int i) {
        return Log.get(i);
    }

    public int getTamanhoDocumento() {
        return Log.size();
    }
}

