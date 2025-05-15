package br.com.example.annyscake.ui.bolo;

public class Bolo {

    private byte tamanhoDoBolo;
    private String tema;
    private double valorDoBolo;
    private String recheio;

    public byte getTamanhoDoBolo() {
        return tamanhoDoBolo;
    }

    public void setTamanhoDoBolo(byte tamanhoDoBolo) {
        this.tamanhoDoBolo = tamanhoDoBolo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public double getValorDoBolo() {
        return valorDoBolo;
    }

    public void setValorDoBolo(double valorDoBolo) {
        this.valorDoBolo = valorDoBolo;
    }

    public String getRecheio() {
        return recheio;
    }

    public void setRecheio(String recheio) {
        this.recheio = recheio;
    }
}
