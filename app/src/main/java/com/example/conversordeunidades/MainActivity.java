package com.example.conversordeunidades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // EdgeToEdge: permite que o conteúdo da aplicação se estenda até as bordas da tela,
        // incluindo areas ocupadas pelas barras do sistema (status bar e navigation bar)
        // isso proporciona uma experiencia visual mais imersiva e moderna.
        EdgeToEdge.enable(this);
        
        // setContentView: define qual layout XML será usado para esta Activity
        // o layout é inflado e todas as views são criadas e conectadas ao código Java
        setContentView(R.layout.activity_main);

        // ViewCompat.setOnApplyWindowInsetsListener: configura um listener para tratar os window insets
        // insets são as áreas ocupadas pelas barras do sistema (status bar no topo, navigation bar na parte inferior)
        // quando usamos EdgeToEdge, precisamos ajustar o padding do conteúdo para evitar sobreposicao.
        // android.R.id.content: refere a view raiz do conteúdo da Activity (onde o layout é inflado).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                // getInsets: pega as dimensoes (left, top, right, bottom) das barras do sistema em pixels
                // systemBars(): retorna os insets relacionados as barras do sistema (status bar e navigation bar)
                Insets barrasDoSistema = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                
                // setPadding: aplica padding na view para que o conteúdo nao fique escondido atrás das barras do sistema
                // isso garante que todo o conteúdo permaneça visível e acessível ao usuario
                view.setPadding(barrasDoSistema.left, barrasDoSistema.top, barrasDoSistema.right, barrasDoSistema.bottom);
                
                // retorna os insets para que outros listeners possam processa-los, se necessario
                return insets;
            }
        });

        // setOnClickListener: define o que acontece quando o card é clicado pelo usuário
        // Intent: é o mecanismo do Android para comunicacao entre componentes (Activities, Services, BroadcastReceivers)
        // um Intent explicito especifica exatamente qual Activity deve ser aberta, usando a classe da Activity
        // startActivity: inicia a Activity especificada no Intent, fazendo a transicao da tela atual para a nova tela
        findViewById(R.id.cardTemperatura).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TemperaturaActivity.class));
            }
        });

        findViewById(R.id.cardPeso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PesoActivity.class));
            }
        });

        findViewById(R.id.cardDistancia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DistanciaActivity.class));
            }
        });

        findViewById(R.id.cardVelocidade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VelocidadeActivity.class));
            }
        });

        findViewById(R.id.cardTempo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TempoActivity.class));
            }
        });

        findViewById(R.id.cardMoeda).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MoedaActivity.class));
            }
        });
    }
}
