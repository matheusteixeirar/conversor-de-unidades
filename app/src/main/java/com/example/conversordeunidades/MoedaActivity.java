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

public class MoedaActivity extends AppCompatActivity {
    private EditText campoValorOrigem, campoValorDestino;
    private Spinner spinnerUnidadeOrigem, spinnerUnidadeDestino;
    
    // constantes estaticas: valores fixos que não mudam durante a execucao do programa
    // static final: cria uma constante que pertence a classe, nao a instância, e nao pode ser alterada
    // essas taxas de câmbio sao valores aproximados e fixos (em uma aplicacao real, deveriam vir de uma API)
    private static final double TAXA_DOLAR = 5.0;
    private static final double TAXA_EURO = 5.5;
    private static final double TAXA_LIBRA = 6.3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // EdgeToEdge: permite que o conteudo se estenda ate as bordas da tela.
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

        // getString: obtém strings do arquivo strings.xml
        ((TextView) findViewById(R.id.tvTitle)).setText(getString(R.string.currency));
        campoValorOrigem = findViewById(R.id.editTextFrom);
        campoValorDestino = findViewById(R.id.editTextTo);
        spinnerUnidadeOrigem = findViewById(R.id.spinnerFrom);
        spinnerUnidadeDestino = findViewById(R.id.spinnerTo);

        // ArrayAdapter: conecta um array de dados com um Spinner
        // setAdapter: popula o Spinner com os dados
        // setSelection: define o item selecionado por padrao
        String[] unidades = {"Real (BRL)", "Dólar (USD)", "Euro (EUR)", "Libra (GBP)"};
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

        // OnItemSelectedListener: monitora quando o usuário seleciona um item no Spinner
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

    // realiza a conversao do valor digitado entre as moedas selecionadas
    // getText: pega o texto do EditText
    // Double.parseDouble: converte String para número decimal
    // getSelectedItemPosition: retorna o índice do item selecionado no Spinner
    // String.format: formata o número com 2 casas decimais ("%.2f")
    private void realizarConversao() {
        String texto = campoValorOrigem.getText().toString();
        if (texto.isEmpty()) {
            campoValorDestino.setText("");
            return;
        }
        try {
            double valor = Double.parseDouble(texto);
            double resultado = converterMoeda(valor, 
                spinnerUnidadeOrigem.getSelectedItemPosition(), 
                spinnerUnidadeDestino.getSelectedItemPosition());
            campoValorDestino.setText(String.format("%.2f", resultado));
        } catch (NumberFormatException e) {
            campoValorDestino.setText("");
        }
    }

    // converte um valor de moeda entre moedas diferentes
    // estrategia: converte primeiro para Real (unidade base), depois para a moeda destino
    // origem: indice da moeda de origem (0=Real, 1=Dólar, 2=Euro, 3=Libra).
    // destino: indice da moeda de destino
    // as taxas de cambio são valores fixos armazenados em constantes (TAXA_DOLAR, TAXA_EURO, TAXA_LIBRA)
    private double converterMoeda(double valor, int origem, int destino) {
        double reais = valor;
        switch (origem) {
            case 1: reais = valor * TAXA_DOLAR; break; // Dolar para Real
            case 2: reais = valor * TAXA_EURO; break; // Euro para Real
            case 3: reais = valor * TAXA_LIBRA; break; // Libra para Real
        }
        switch (destino) {
            case 0: return reais; // Real
            case 1: return reais / TAXA_DOLAR; // Real para Dolar
            case 2: return reais / TAXA_EURO; // Real para Euro
            case 3: return reais / TAXA_LIBRA; // Real para Libra
            default: return reais;
        }
    }
}
