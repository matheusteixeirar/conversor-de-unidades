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

public class PesoActivity extends AppCompatActivity {
    private EditText campoValorOrigem, campoValorDestino;
    private Spinner spinnerUnidadeOrigem, spinnerUnidadeDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // EdgeToEdge: permite que o conteudo se estenda ate as bordas da tela
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversor);

        // ViewCompat.setOnApplyWindowInsetsListener: trata os window insets para evitar sobreposicao com barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                Insets barrasDoSistema = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                view.setPadding(barrasDoSistema.left, barrasDoSistema.top, barrasDoSistema.right, barrasDoSistema.bottom);
                return insets;
            }
        });

        // getString: pega strings do arquivo strings.xml
        ((TextView) findViewById(R.id.tvTitle)).setText(getString(R.string.weight));
        campoValorOrigem = findViewById(R.id.editTextFrom);
        campoValorDestino = findViewById(R.id.editTextTo);
        spinnerUnidadeOrigem = findViewById(R.id.spinnerFrom);
        spinnerUnidadeDestino = findViewById(R.id.spinnerTo);

        // ArrayAdapter: conecta um array de dados com um Spinner, fornecendo os itens para exibicao
        // setAdapter: popula o Spinner com os dados do adapter
        // setSelection: define qual item sera selecionado por padrao
        String[] unidades = {"Quilograma (kg)", "Grama (g)", "Libra (lb)", "Tonelada (t)"};
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, unidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidadeOrigem.setAdapter(adapter);
        spinnerUnidadeDestino.setAdapter(adapter);
        spinnerUnidadeDestino.setSelection(1);

        // TextWatcher: monitora mudancas no texto do EditText em tempo real
        // afterTextChanged: chamado apos o texto ser alterado, acionando a conversao automaticamente
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

        // OnItemSelectedListener: monitora quando o usuario seleciona um item diferente no Spinner
        // onItemSelected: chamado quando um item é selecionado, recalculando a conversao
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

        // finish: finaliza a Activity atual e retorna para a Activity anterior
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
    // String.format: formata o número com 4 casas decimais ("%.4f")
    private void realizarConversao() {
        String texto = campoValorOrigem.getText().toString();
        if (texto.isEmpty()) {
            campoValorDestino.setText("");
            return;
        }
        try {
            double valor = Double.parseDouble(texto);
            double resultado = converterPeso(valor, 
                spinnerUnidadeOrigem.getSelectedItemPosition(), 
                spinnerUnidadeDestino.getSelectedItemPosition());
            campoValorDestino.setText(String.format("%.4f", resultado));
        } catch (NumberFormatException e) {
            campoValorDestino.setText("");
        }
    }

    // converte um valor de peso entre unidades diferentes
    // estrategia: converte primeiro para Quilograma (unidade base), depois para a unidade destino
    // origem: indice da unidade de origem (0=Quilograma, 1=Grama, 2=Libra, 3=Tonelada)
    // destino: indice da unidade de destino
    private double converterPeso(double valor, int origem, int destino) {
        double kg = valor;
        switch (origem) {
            case 1: kg = valor / 1000; break; // Grama para Quilograma
            case 2: kg = valor * 0.453592; break; // Libra para Quilograma
            case 3: kg = valor * 1000; break; // Tonelada para Quilograma
        }
        switch (destino) {
            case 0: return kg; // Quilograma
            case 1: return kg * 1000; // Quilograma para Grama
            case 2: return kg / 0.453592; // Quilograma para Libra
            case 3: return kg / 1000; // Quilograma para Tonelada
            default: return kg;
        }
    }
}
