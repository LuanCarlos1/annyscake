package br.com.example.annyscake.ui.pedido;

public class Pedido {
    public String nome, telefone, endereco, data, tema, tamanho, massa, recheio, recheio_especial, valor, status;

    public Pedido() {} // Necessário para Firestore

    public Pedido(String nome, String telefone, String endereco, String data, String tema,
                  String tamanho, String massa, String recheio, String recheio_especial,
                  String valor, String status) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.data = data;
        this.tema = tema;
        this.tamanho = tamanho;
        this.massa = massa;
        this.recheio = recheio;
        this.recheio_especial = recheio_especial;
        this.valor = valor;
        this.status = status;
    }
}