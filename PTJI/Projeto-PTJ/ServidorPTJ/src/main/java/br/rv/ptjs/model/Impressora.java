package br.rv.ptjs.model;

public class Impressora {
    private String name;
    private String ip;
    private int porta;
    private boolean status;

    public Impressora(String ip, int porta, String name){
        this.name = name;
        this.ip = ip;
        this.porta = porta;
        this.status = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
