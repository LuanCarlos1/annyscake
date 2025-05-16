package br.com.example.annyscake.ui.pedido;

import org.json.JSONException;
import org.json.JSONObject;

public class Pedido {

    public String nome;
    public String tema;
    public String tamanho;
    public String recheio;
    public String valor;
    public String data;

    public Pedido(String nome, String tema, String tamanho, String recheio, String valor, String data) {
        this.nome = nome;
        this.tema = tema;
        this.tamanho = tamanho;
        this.recheio = recheio;
        this.valor = valor;
        this.data = data;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("nome", nome);
        obj.put("tema", tema);
        obj.put("tamanho", tamanho);
        obj.put("recheio", recheio);
        obj.put("valor", valor);
        obj.put("data", data);
        return obj;
    }

    public static Pedido fromJSON(JSONObject obj) throws JSONException {
        return new Pedido(
                obj.getString("nome"),
                obj.getString("tema"),
                obj.getString("tamanho"),
                obj.getString("recheio"),
                obj.getString("valor"),
                obj.getString("data")
        );
    }
}

