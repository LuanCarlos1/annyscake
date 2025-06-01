package br.com.example.annyscake;

import android.os.Bundle;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

import br.com.example.annyscake.ui.historicoCliente.HistoricoCliente;
import br.com.example.annyscake.ui.pedidocliente.pedidoCliente;
import br.com.example.annyscake.ui.perfilCliente.PerfilCliente;
import br.com.example.annyscake.ui.statuspedido.StatusPedido;

public class TelaCliente extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Objects.requireNonNull(getSupportActionBar()).hide();

        final LinearLayout pedidoLayout = findViewById(R.id.pedidoLayout);
        final LinearLayout statusLayout = findViewById(R.id.statusLayout);
        final LinearLayout historicoLayout = findViewById(R.id.historicoLayout);
        final LinearLayout perfilLayout = findViewById(R.id.perfilLayout);

        final ImageView pedidoImage = findViewById(R.id.iconPedido);
        final ImageView statusImage = findViewById(R.id.iconStatus);
        final ImageView historicoImage = findViewById(R.id.iconHistorico);
        final ImageView perfilImage = findViewById(R.id.iconPerfil);

        pedidoLayout.setBackgroundResource(R.drawable.round_shape);

        pedidoLayout.setOnClickListener(v ->{
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentView, pedidoCliente.class, null)
                        .commit();

                perfilImage.setBackgroundResource(R.drawable.user_fill);
                statusImage.setBackgroundResource(R.drawable.time_fill);
                historicoImage.setBackgroundResource(R.drawable.file_paper_2_fill);

                perfilLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                statusLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                historicoLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                pedidoLayout.setBackgroundResource(R.drawable.round_shape);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1f);
                scaleAnimation.setDuration(400);
                scaleAnimation.setFillAfter(true);
                pedidoLayout.startAnimation(scaleAnimation);

        });

        statusLayout.setOnClickListener(v ->{
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentView, StatusPedido.class, null)
                        .commit();


                perfilImage.setBackgroundResource(R.drawable.user_fill);
                pedidoImage.setBackgroundResource(R.drawable.icon_bolo);
                historicoImage.setBackgroundResource(R.drawable.file_paper_2_fill);

                perfilLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                pedidoLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                historicoLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                statusImage.setBackgroundResource(R.drawable.time_fill_pink);
                statusLayout.setBackgroundResource(R.drawable.round_shape);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1f);
                scaleAnimation.setDuration(400);
                scaleAnimation.setFillAfter(true);
                statusLayout.startAnimation(scaleAnimation);

        });

        historicoLayout.setOnClickListener(v ->{
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentView, HistoricoCliente.class, null)
                        .commit();

                perfilImage.setBackgroundResource(R.drawable.user_fill);
                statusImage.setBackgroundResource(R.drawable.time_fill);
                pedidoImage.setBackgroundResource(R.drawable.icon_bolo);

                perfilLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                statusLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                historicoLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                historicoImage.setBackgroundResource(R.drawable.file_paper_2_fill_pink);
                historicoLayout.setBackgroundResource(R.drawable.round_shape);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1f);
                scaleAnimation.setDuration(400);
                scaleAnimation.setFillAfter(true);
                historicoLayout.startAnimation(scaleAnimation);

        });

        perfilLayout.setOnClickListener(v ->{
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentView, PerfilCliente.class, null)
                        .commit();


                pedidoImage.setBackgroundResource(R.drawable.icon_bolo);
                statusImage.setBackgroundResource(R.drawable.time_fill);
                historicoImage.setBackgroundResource(R.drawable.file_paper_2_fill);

                pedidoLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                statusLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                historicoLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                perfilImage.setBackgroundResource(R.drawable.user_fill_pink);
                perfilLayout.setBackgroundResource(R.drawable.round_shape);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1f);
                scaleAnimation.setDuration(400);
                scaleAnimation.setFillAfter(true);
                perfilLayout.startAnimation(scaleAnimation);

        });
    }
}