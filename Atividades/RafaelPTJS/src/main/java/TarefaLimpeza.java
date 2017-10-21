public class TarefaLimpeza implements Runnable {

    private Banheiro banheiro;

    public TarefaLimpeza(Banheiro banheiro) {
        this.banheiro = banheiro;
    }

    @Override
    public void run() {
        while (true) {
            this.banheiro.limpa();
            try {
                Thread.sleep(15000);// Limpa a cada 15 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}