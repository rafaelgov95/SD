package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;

public class EnviarMensagemParaImpressora implements Runnable {
    private Buffer buffer;


    public EnviarMensagemParaImpressora(Buffer buffer) {
        this.buffer = buffer;
    }


    public void run() {
        while (true) {
            try {
                Buffer.getMensagem();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
