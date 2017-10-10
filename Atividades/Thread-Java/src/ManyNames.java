class NamesRunnable implements Runnable{
    public void run(){
        for (int x=1;x<=3;x++){
            System.out.println("Run by"+ Thread.currentThread().getName()+",X is "+x);
        }
    }
}

public class ManyNames {

    public static void main(String []args){
        System.out.println("Iniciou");
        NamesRunnable nr = new NamesRunnable();
        Thread one = new Thread(nr);
        Thread two = new Thread(nr);
        Thread three = new Thread(nr);
        one.setName("Rafael");
        two.setName("Lucas");
        two.setPriority(10);
        three.setName("Poul");
        one.start();
        two.start();
    }
}
