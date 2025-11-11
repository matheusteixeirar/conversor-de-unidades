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

public class TempoActivity extends AppCompatActivity {
    private EditText campoValorOrigem, campoValorDestino;
    private Spinner spinnerUnidadeOrigem, spinnerUnidadeDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // EdgeToEdge: permite que o conteudo se estenda até as bordas da tela
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

        // findViewById: busca views no layout pelo ID
        // getString: pega strings do arquivo strings.xml
        ((TextView) findViewById(R.id.tvTitle)).setText(getString(R.string.time));
        campoValorOrigem = findViewById(R.id.editTextFrom);
        campoValorDestino = findViewById(R.id.editTextTo);
        spinnerUnidadeOrigem = findViewById(R.id.spinnerFrom);
        spinnerUnidadeDestino = findViewById(R.id.spinnerTo);

        // ArrayAdapter: conecta um array de dados com um Spinner
        // setAdapter: popula o Spinner com os dados
        // setSelection: define o item selecionado por padrao
        String[] unidades = {"Segundo (s)", "Minuto (min)", "Hora (h)", "Dia", "Semana", "Mês", "Ano"};
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
        // onItemSelected: reclacula a conversao quando a unidade muda
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
            double resultado = converterTempo(valor, 
                spinnerUnidadeOrigem.getSelectedItemPosition(), 
                spinnerUnidadeDestino.getSelectedItemPosition());
            campoValorDestino.setText(String.format("%.4f", resultado));
        } catch (NumberFormatException e) {
            campoValorDestino.setText("");
        }
    }

    // converte um valor de tempo entre unidades diferentes
    // estratégia: converte primeiro para Segundos (unidade base), depois para a unidade destino
    // origem: indice da unidade de origem (0=Segundo, 1=Minuto, 2=Hora, 3=Dia, 4=Semana, 5=Mês, 6=Ano)
    // destino: indice da unidade de destino
    private double converterTempo(double valor, int origem, int destino) {
        double segundos = valor;
        switch (origem) {
            case 1: segundos = valor * 60; break; // Minuto para Segundo
            case 2: segundos = valor * 3600; break; // Hora para Segundo
            case 3: segundos = valor * 86400; break; // Dia para Segundo
            case 4: segundos = valor * 604800; break; // Semana para Segundo
            case 5: segundos = valor * 2592000; break; // Mês para Segundo (30 dias)
            case 6: segundos = valor * 31536000; break; // Ano para Segundo
        }
        switch (destino) {
            case 0: return segundos; // Segundo
            case 1: return segundos / 60; // Segundo para Minuto
            case 2: return segundos / 3600; // Segundo para Hora
            case 3: return segundos / 86400; // Segundo para Dia
            case 4: return segundos / 604800; // Segundo para Semana
            case 5: return segundos / 2592000; // Segundo para Mes
            case 6: return segundos / 31536000; // Segundo para Ano
            default: return segundos;
        }
    }
}
