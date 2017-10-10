class ThreadB extends Thread{

    int total;

    @Override
    public void run(){
        synchronized(this){
            for(int i=0; i<2000 ; i++){
                total += i;
            }
            System.out.println("Terminou o laço");
            notify();
            System.out.println("Continua a execução de ThreadB");
        }
    }
}

public class ThreadA {
    public static void main(String[] args){
        ThreadB b = new ThreadB();
        b.start();

        synchronized(b){
            try{
                System.out.println("Aguardando o b completar...");
                b.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("Total é igual a: " + b.total);
        }
    }
}
