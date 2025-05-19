package br.com.example.annyscake.ui.pedido;

import org.json.JSONException;
import org.json.JSONObject;

public class Pedido {
    public String nome;
    public String telefone;
    public String endereco;
    public String data;
    public String tema;
    public String tamanho;
    public String massa;
    public String recheio;
    public String recheio_especial;
    public String valor;

    public static Pedido fromJSON(JSONObject obj) throws JSONException {
        Pedido p = new Pedido();
        p.nome = obj.getString("nome");
        p.telefone = obj.getString("telefone");
        p.endereco = obj.getString("endereco");
        p.data = obj.getString("data");
        p.tema = obj.getString("tema");
        p.tamanho = obj.getString("tamanho");
        p.massa = obj.getString("massa");
        p.recheio = obj.getString("recheio");
        p.recheio_especial = obj.getString("recheio_especial");
        p.valor = obj.getString("valor");
        return p;
    }
}

