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

public class TemperaturaActivity extends AppCompatActivity {
    private EditText campoValorOrigem, campoValorDestino;
    private Spinner spinnerUnidadeOrigem, spinnerUnidadeDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // EdgeToEdge: permite que o conteúdo se estenda ate as bordas da tela
        // isso cria uma experiencia visual mais imersiva, usando toda a área disponivel do dispositivo
        EdgeToEdge.enable(this);
        
        // setContentView: infla o layout XML e cria todas as views definidas nele
        // apos esta chamada, podemos usar findViewById para obter referencias as views
        setContentView(R.layout.activity_conversor);

        // ViewCompat.setOnApplyWindowInsetsListener: trata os window insets para evitar sobreposicao
        // quando EdgeToEdge está ativo, o conteúdo pode ficar atras das barras do sistema
        // este listener ajusta o padding automaticamente sempre que os insets mudam
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                // getInsets: obtém as dimensoes das barras do sistema em pixels
                Insets barrasDoSistema = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                
                // setPadding: adiciona espaco interno na view para compensar as barras do sistema
                view.setPadding(barrasDoSistema.left, barrasDoSistema.top, barrasDoSistema.right, barrasDoSistema.bottom);
                return insets;
            }
        });

        // findViewById: busca uma view no layout pelo seu ID e retorna uma referencia
        // TextView: componente usado para exibir texto na tela
        // getString: pega uma string do arquivo strings.xml
        ((TextView) findViewById(R.id.tvTitle)).setText(getString(R.string.temperature));
        
        // EditText: campo de texto editável onde o usuário pode digitar valores
        // campoValorOrigem: campo onde o usuário digita o valor a ser convertido
        // campoValorDestino: campo onde o resultado da conversao é exibido (somente leitura)
        campoValorOrigem = findViewById(R.id.editTextFrom);
        campoValorDestino = findViewById(R.id.editTextTo);
        
        // Spinner: componente de seleçao (dropdown) que permite ao usuário escolher uma opçao de uma lista
        // spinnerUnidadeOrigem: spinner para selecionar a unidade de origem da conversao
        // spinnerUnidadeDestino: spinner para selecionar a unidade de destino da conversao
        spinnerUnidadeOrigem = findViewById(R.id.spinnerFrom);
        spinnerUnidadeDestino = findViewById(R.id.spinnerTo);

        // ArrayAdapter: adapter que conecta um array de dados com um componente de lista (como Spinner)
        // é responsável por criar as views dos itens e fornecer os dados para o Spinner
        // simple_spinner_item: layout padrao do Android para exibir cada item do Spinner quando selecionado
        // simple_spinner_dropdown_item: layout padrão para exibir os itens no dropdown quando o Spinner é aberto
        // setAdapter: conecta o adapter ao Spinner, populando-o com os dados
        // setSelection: define qual item será selecionado por padrão (índice 1 = segundo item)
        String[] unidades = {"Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)"};
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, unidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidadeOrigem.setAdapter(adapter);
        spinnerUnidadeDestino.setAdapter(adapter);
        spinnerUnidadeDestino.setSelection(1);

        // TextWatcher: interface que monitora mudanças no texto de um EditText em tempo real
        // é util para executar açoes automaticamente enquanto o usuário digita, sem precisar clicar em um botao
        // beforeTextChanged: chamado antes do texto ser alterado (nao usado neste caso)
        // onTextChanged: chamado durante a alteração do texto (nao usado neste caso)
        // afterTextChanged: chamado após o texto ser alterado, quando já podemos ler o novo valor
        // aqui usamos para realizar a conversão automaticamente sempre que o usuario digitar algo
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

        // OnItemSelectedListener: interface que monitora quando o usuario seleciona um item diferente no Spinner
        // onItemSelected: chamado quando um item é selecionado, recebendo a posição do item selecionado
        // aqui usamos para recalcular a conversao sempre que o usuário mudar a unidade de origem
        spinnerUnidadeOrigem.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                realizarConversao();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // listener similar para o Spinner de destino
        // recalcula a conversao sempre que o usuario mudar a unidade de destino
        spinnerUnidadeDestino.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                realizarConversao();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // finish: finaliza a Activity atual e retorna para a Activity anterior (MainActivity).
        // é equivalente ao botão "Voltar" do Android, removendo esta Activity da pilha de Activities.
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // realiza a conversao do valor digitado entre as unidades selecionadas
    // getText: pega o texto atual do EditText como um objeto Editable
    // toString: converte o Editable para String para poder manipular o texto
    // isEmpty: verifica se a string está vazia (sem caracteres)
    // Double.parseDouble: converte uma String para um número decimal (double)
    // pode lancar NumberFormatException se a string não for um número válido (ex: "abc", "12.34.56")
    // getSelectedItemPosition: retorna o indice do item atualmente selecionado no Spinner (0, 1, 2, etc.)
    // String.format: formata um número com uma quantidade específica de casas decimais
    // "%.2f": formata um double com exatamente 2 casas decimais (ex: 25.50)
    private void realizarConversao() {
        String texto = campoValorOrigem.getText().toString();
        if (texto.isEmpty()) {
            campoValorDestino.setText("");
            return;
        }
        try {
            double valor = Double.parseDouble(texto);
            double resultado = converterTemperatura(valor, 
                spinnerUnidadeOrigem.getSelectedItemPosition(), 
                spinnerUnidadeDestino.getSelectedItemPosition());
            campoValorDestino.setText(String.format("%.2f", resultado));
        } catch (NumberFormatException e) {
            // se o usuario digitou algo inválido, limpa o campo de resultado
            // isso evita exibir valores incorretos ou causar crashes
            campoValorDestino.setText("");
        }
    }

    // converte um valor de temperatura entre unidades diferentes
    // estratégia: converte primeiro para Celsius (unidade base), depois converte de Celsius para a unidade destino
    // isso simplifica o código, evitando criar uma matriz de conversões entre todas as unidades
    // origem: índice da unidade de origem (0=Celsius, 1=Fahrenheit, 2=Kelvin)
    // destino: índice da unidade de destino (0=Celsius, 1=Fahrenheit, 2=Kelvin)
    private double converterTemperatura(double valor, int origem, int destino) {
        double celsius = valor;
        switch (origem) {
            case 1: celsius = (valor - 32) * 5 / 9; break; // Fahrenheit para Celsius
            case 2: celsius = valor - 273.15; break; // Kelvin para Celsius
        }
        switch (destino) {
            case 0: return celsius; // Celsius (ja está na unidade base)
            case 1: return (celsius * 9 / 5) + 32; // Celsius para Fahrenheit
            case 2: return celsius + 273.15; // Celsius para Kelvin
            default: return celsius;
        }
    }
}
