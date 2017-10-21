
public class Banheiro {
    private boolean estaSujo = true;

    public void fazNumero1() {
        String nome = Thread.currentThread().getName();
        System.out.println(nome + " batendo na porta");

        // Esse bloco de código fica sincronizado, o objeto está sendo bloqueado
        // para outras Threads que não sejam a Thread atual que está em
        // execução.
        // Outras Threads terão que esperar a primeira Thread que
        // obteve acesso a esse método, terminar de executar o bloco de código
        // sincronizado.
        synchronized (this) {
            System.out.println(nome + " entrando no banheiro");

            while (this.estaSujo) {
                esperaLaFora(nome);
            }

            System.out.println(nome + " fazendo coisa rapida");
            try {
                Thread.sleep(8000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.estaSujo = true;

            System.out.println(nome + " dando descarga");
            System.out.println(nome + " lavando a mão");
            System.out.println(nome + " saindo do banheiro");

        }

    }

    // Esse bloco de código fica sincronizado, o objeto está sendo bloqueado
    // para outras Threads que não sejam a Thread atual que está em
    // execução.
    // Outras Threads terão que esperar a primeira Thread que
    // obteve acesso a esse método, terminar de executar o bloco de código
    // sincronizado.
    public void fazNumero2() {
        String nome = Thread.currentThread().getName();
        System.out.println(nome + " batendo na porta");

        synchronized (this) {
            System.out.println(nome + " entrando no banheiro");

            // Chama um método que implementa object.wait(), colocando a thread
            // no estado de espera(WAITING).
            // Para ser liberada é necessário uma
            // notificação(object.notifyAll()).
            while (this.estaSujo) {
                esperaLaFora(nome);
            }

            System.out.println(nome + " fazendo coisa demorada");
            try {
                Thread.sleep(15000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.estaSujo = true;

            System.out.println(nome + " lavando a mão");
            System.out.println(nome + " dando descarga");
            System.out.println(nome + " saindo do banheiro");

        }
    }

    private void esperaLaFora(String nome) {
        System.out.println(nome + ", =(, banheiro está sujo");
        try {
            // O wait() precisa sempre ser chamado dentro de um bloco
            // synchronized para que tenha a chave em mãos
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void limpa() {
        String nome = Thread.currentThread().getName();

        System.out.println(nome + " batendo na porta");

        synchronized (this) {
            System.out.println(nome + " entrando no banheiro");

            if (!this.estaSujo) {
                System.out.println(nome + ", não está sujo, vou sair");
                return;
            }

            System.out.println(nome + " limpando o banheiro");
            this.estaSujo = false;

            try {
                Thread.sleep(13000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Notifica todas as Threads que estão em estado de WAITING para que
            // sejam liberadas. Precisa sempre ser invocado dentro de um bloco
            // synchronized
            this.notifyAll();

            System.out.println(nome + " saindo do banheiro");
        }

    }
}