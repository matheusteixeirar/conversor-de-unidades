package com.example.conversordeunidades;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DistanciaActivity extends AppCompatActivity {
    private EditText campoValorOrigem, campoValorDestino;
    private Spinner spinnerUnidadeOrigem, spinnerUnidadeDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // EdgeToEdge: permite que o conteudo se estenda ate as bordas da tela
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversor);
        
        // ViewCompat.setOnApplyWindowInsetsListener: trata os window insets para evitar sobreposicao
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                Insets barrasDoSistema = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                view.setPadding(barrasDoSistema.left, barrasDoSistema.top, barrasDoSistema.right, barrasDoSistema.bottom);
                return insets;
            }
        });

        ((TextView) findViewById(R.id.tvTitle)).setText(getString(R.string.distance));
        campoValorOrigem = findViewById(R.id.editTextFrom);
        campoValorDestino = findViewById(R.id.editTextTo);
        spinnerUnidadeOrigem = findViewById(R.id.spinnerFrom);
        spinnerUnidadeDestino = findViewById(R.id.spinnerTo);

        // ArrayAdapter: conecta um array de dados com um Spinner
        // setAdapter: popula o Spinner com os dados
        // setSelection: define o item selecionado por padrao
        String[] unidades = {"Metro (m)", "Quilômetro (km)", "Centímetro (cm)", "Milha (mi)", "Pé (ft)"};
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, unidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidadeOrigem.setAdapter(adapter);
        spinnerUnidadeDestino.setAdapter(adapter);
        spinnerUnidadeDestino.setSelection(1);

        // TextWatcher: monitora mudanças no texto do EditText em tempo real
        // afterTextChanged: aciona a conversão automaticamente quando o texto muda
        campoValorOrigem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                realizarConversao();
            }
        });

        // OnItemSelectedListener: monitora quando o usuario seleciona um item no Spinner
        // onItemSelected: recalcula a conversao quando a unidade muda
        spinnerUnidadeOrigem.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                realizarConversao();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        spinnerUnidadeDestino.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                realizarConversao();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // finish: finaliza a Activity atual e retorna para a anterior
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // realiza a conversao do valor digitado entre as unidades selecionadas
    // getText: pega o texto do EditText
    // Double.parseDouble: converte String para número decimal
    // getSelectedItemPosition: retorna o indice do item selecionado no Spinner
    // String.format: formata o número com 4 casas decimais
    private void realizarConversao() {
        String texto = campoValorOrigem.getText().toString();
        if (texto.isEmpty()) {
            campoValorDestino.setText("");
            return;
        }
        try {
            double valor = Double.parseDouble(texto);
            double resultado = converterDistancia(valor, 
                spinnerUnidadeOrigem.getSelectedItemPosition(), 
                spinnerUnidadeDestino.getSelectedItemPosition());
            campoValorDestino.setText(String.format("%.4f", resultado));
        } catch (NumberFormatException e) {
            campoValorDestino.setText("");
        }
    }

    // converte um valor de distancia entre unidades diferentes
    // estrategia: converte primeiro para Metro (unidade base), depois para a unidade destino
    // origem: indice da unidade de origem (0=Metro, 1=Quilometro, 2=Centimetro, 3=Milha, 4=Pe)
    // destino: indice da unidade de destino
    private double converterDistancia(double valor, int origem, int destino) {
        double metros = valor;
        switch (origem) {
            case 1: metros = valor * 1000; break; // Quilometro para Metro
            case 2: metros = valor / 100; break; // Centimetro para Metro
            case 3: metros = valor * 1609.34; break; // Milha para Metro
            case 4: metros = valor * 0.3048; break; // Pe para Metro
        }
        switch (destino) {
            case 0: return metros; // Metro
            case 1: return metros / 1000; // Metro para Quilometro
            case 2: return metros * 100; // Metro para Centimetro
            case 3: return metros / 1609.34; // Metro para Milha
            case 4: return metros / 0.3048; // Metro para Pe
            default: return metros;
        }
    }
}
