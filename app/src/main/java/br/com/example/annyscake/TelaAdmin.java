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

import br.com.example.annyscake.ui.Admin.HistoricoAdmin;
import br.com.example.annyscake.ui.Admin.InicioAdmin;
import br.com.example.annyscake.ui.Admin.PedidosAdmin;
import br.com.example.annyscake.ui.Admin.PerfilAdmin;



public class TelaAdmin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Objects.requireNonNull(getSupportActionBar()).hide();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentViewAdm, InicioAdmin.class, null)
                .commit();


        final LinearLayout inicioLayout = findViewById(R.id.pedidoLayoutAdm);
        final LinearLayout pedidosLayout = findViewById(R.id.statusLayoutAdm);
        final LinearLayout historicoLayout = findViewById(R.id.historicoLayoutAdm);
        final LinearLayout perfilLayout = findViewById(R.id.perfilLayoutAdm);

        final ImageView pedidoImage = findViewById(R.id.iconPedido);
        final ImageView statusImage = findViewById(R.id.iconStatus);
        final ImageView historicoImage = findViewById(R.id.iconHistorico);
        final ImageView perfilImage = findViewById(R.id.iconPerfil);

        inicioLayout.setBackgroundResource(R.drawable.round_shape);

        inicioLayout.setOnClickListener(v ->{
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentViewAdm, InicioAdmin.class, null)
                    .commit();

            perfilImage.setBackgroundResource(R.drawable.user_fill);
            statusImage.setBackgroundResource(R.drawable.time_fill);
            historicoImage.setBackgroundResource(R.drawable.file_paper_2_fill);

            perfilLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            pedidosLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            historicoLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            inicioLayout.setBackgroundResource(R.drawable.round_shape);

            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1f);
            scaleAnimation.setDuration(400);
            scaleAnimation.setFillAfter(true);
            inicioLayout.startAnimation(scaleAnimation);

        });

        pedidosLayout.setOnClickListener(v ->{
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentViewAdm, PedidosAdmin.class, null)
                    .commit();


            perfilImage.setBackgroundResource(R.drawable.user_fill);
            pedidoImage.setBackgroundResource(R.drawable.icon_bolo);
            historicoImage.setBackgroundResource(R.drawable.file_paper_2_fill);

            perfilLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            inicioLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            historicoLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            statusImage.setBackgroundResource(R.drawable.time_fill_pink);
            pedidosLayout.setBackgroundResource(R.drawable.round_shape);

            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1f);
            scaleAnimation.setDuration(400);
            scaleAnimation.setFillAfter(true);
            pedidosLayout.startAnimation(scaleAnimation);

        });

        historicoLayout.setOnClickListener(v ->{
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentViewAdm, HistoricoAdmin.class, null)
                    .commit();

            perfilImage.setBackgroundResource(R.drawable.user_fill);
            statusImage.setBackgroundResource(R.drawable.time_fill);
            pedidoImage.setBackgroundResource(R.drawable.icon_bolo);

            perfilLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            pedidosLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
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
                    .replace(R.id.fragmentViewAdm, PerfilAdmin.class, null)
                    .commit();


            pedidoImage.setBackgroundResource(R.drawable.icon_bolo);
            statusImage.setBackgroundResource(R.drawable.time_fill);
            historicoImage.setBackgroundResource(R.drawable.file_paper_2_fill);

            inicioLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            pedidosLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
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