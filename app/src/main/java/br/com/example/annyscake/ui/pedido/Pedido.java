package br.com.example.annyscake.ui.pedido;

public class Pedido {
    private String nome;
    private String telefone;
    private String endereco;
    private String dataEntrega;
    private String tema;
    private String tamanho;
    private String massa;
    private String recheio;
    private String recheioEspecial;
    private String valor;
    private String status;
    private String usuarioId; // ✅ Novo campo

    public Pedido() {
        // Construtor vazio necessário para Firestore
    }

    public Pedido(String nome, String telefone, String endereco, String dataEntrega,
                  String tema, String tamanho, String massa, String recheio,
                  String recheioEspecial, String valor, String status, String usuarioId) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataEntrega = dataEntrega;
        this.tema = tema;
        this.tamanho = tamanho;
        this.massa = massa;
        this.recheio = recheio;
        this.recheioEspecial = recheioEspecial;
        this.valor = valor;
        this.status = status;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getMassa() {
        return massa;
    }

    public void setMassa(String massa) {
        this.massa = massa;
    }

    public String getRecheio() {
        return recheio;
    }

    public void setRecheio(String recheio) {
        this.recheio = recheio;
    }

    public String getRecheioEspecial() {
        return recheioEspecial;
    }

    public void setRecheioEspecial(String recheioEspecial) {
        this.recheioEspecial = recheioEspecial;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}