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

public class VelocidadeActivity extends AppCompatActivity {
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

        // getString: pega strings do arquivo strings.xml
        ((TextView) findViewById(R.id.tvTitle)).setText(getString(R.string.speed));
        campoValorOrigem = findViewById(R.id.editTextFrom);
        campoValorDestino = findViewById(R.id.editTextTo);
        spinnerUnidadeOrigem = findViewById(R.id.spinnerFrom);
        spinnerUnidadeDestino = findViewById(R.id.spinnerTo);

        // ArrayAdapter: conecta um array de dados com um Spinner
        // setAdapter: popula o Spinner com os dados
        // setSelection: define o item selecionado por padrao
        String[] unidades = {"km/h", "m/s", "mi/h"};
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, unidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidadeOrigem.setAdapter(adapter);
        spinnerUnidadeDestino.setAdapter(adapter);
        spinnerUnidadeDestino.setSelection(1);

        // TextWatcher: monitora mudancas no texto do EditText em tempo real
        // afterTextChanged: aciona a conversao automaticamente quando o texto muda
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
    // String.format: formata o numero com 4 casas decimais
    private void realizarConversao() {
        String texto = campoValorOrigem.getText().toString();
        if (texto.isEmpty()) {
            campoValorDestino.setText("");
            return;
        }
        try {
            double valor = Double.parseDouble(texto);
            double resultado = converterVelocidade(valor, 
                spinnerUnidadeOrigem.getSelectedItemPosition(), 
                spinnerUnidadeDestino.getSelectedItemPosition());
            campoValorDestino.setText(String.format("%.4f", resultado));
        } catch (NumberFormatException e) {
            campoValorDestino.setText("");
        }
    }

    // converte um valor de velocidade entre unidades diferentes
    // estratégia: converte primeiro para m/s (unidade base), depois para a unidade destino
    // origem: índice da unidade de origem (0=km/h, 1=m/s, 2=mi/h)
    // destino: índice da unidade de destino
    private double converterVelocidade(double valor, int origem, int destino) {
        double ms = valor;
        switch (origem) {
            case 0: ms = valor / 3.6; break; // km/h para m/s
            case 2: ms = valor * 0.44704; break; // mi/h para m/s
        }
        switch (destino) {
            case 0: return ms * 3.6; // m/s para km/h
            case 1: return ms; // m/s
            case 2: return ms / 0.44704; // m/s para mi/h
            default: return ms;
        }
    }
}
