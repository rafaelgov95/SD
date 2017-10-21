
public class Main {

    // Cria 4 Threads(Convidados) que depois de sincronizadas, só uma delas
    // poderá acessar o banheiro por vez.
    // Ou seja, as Threads criadas não podem acessar o objeto Banheiro ao mesmo
    // tempo.
    public static void main(String[] args) {
        Banheiro banheiro = new Banheiro();

        Thread convidado1 = new Thread(new TarefaNumero1(banheiro), "João");
        Thread convidado2 = new Thread(new TarefaNumero2(banheiro), "Pedro");
        Thread convidado3 = new Thread(new TarefaNumero1(banheiro), "Maria");
        Thread convidado4 = new Thread(new TarefaNumero2(banheiro), "Ana");
        Thread limpeza = new Thread(new TarefaLimpeza(banheiro), "Limpeza");

        // A JVM termina a execução a partir do momento em que a única Thread
        // rodando é a que foi declarada como Daemon.
        // Threads daemon são provedores de seviço para outras threads.
        limpeza.setPriority(Thread.MAX_PRIORITY);
        limpeza.setDaemon(true);

        convidado1.start();
        convidado2.start();
        limpeza.start();
        // convidado3.start();
        // convidado4.start();
    }
}