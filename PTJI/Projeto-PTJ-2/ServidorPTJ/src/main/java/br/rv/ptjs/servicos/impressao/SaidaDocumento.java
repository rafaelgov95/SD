package br.rv.ptjs.servicos.impressao;


import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Documento;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SaidaDocumento implements Runnable {
    private static Socket SocketCliente;

    public SaidaDocumento(Socket SocketCliente) {
        this.SocketCliente = SocketCliente;
    }


    public String Recebe(Socket cliente) throws IOException {
        Scanner leia = new Scanner(cliente.getInputStream());
        if (leia.hasNextLine()) {
            String a = leia.nextLine();
//            leia.close();
            return a;
        }
        return "Estralho1";
    }

    public  void setMensagem(String mensagem) throws IOException {
        PrintStream ps = new PrintStream(SocketCliente.getOutputStream());
        ps.println(mensagem);
        ps.close();
    }
    public  void getBuffer() throws InterruptedException, IOException {
        String rc = Recebe(SocketCliente);
        System.out.println(rc);
        setMensagem("OK");
        if (rc.equals("OK")) {
            if (!Buffer.isDocumentos()) {
                Documento doc = Buffer.getDocumento();
                setMensagem("OK");
            }
        }else{
            setMensagem("ERROR");
        }
    }


    public void run() {
        try {
            getBuffer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
