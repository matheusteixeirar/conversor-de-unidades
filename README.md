# Conversor de Unidades

Projeto acadêmico da matéria Programação para Dispositivos Móveis

Aplicativo que permite converter valores entre diferentes unidades de tempo, velocidade, peso, temperatura, distância e moedas

## Funcionalidades do Aplicativo

- Todas as Activities monitoram alterações no campo de entrada usando TextWatcher, o que permite que as conversões sejam feitas em tempo real

- Cada Activity possui dois Spinners:
  - Unidade de origem: a unidade do valor digitado
  - Unidade de destino: a unidade para a qual será convertido
- Quando o usuário muda qualquer Spinner, a conversão é recalculada imediatamente 

- Cada Activity utiliza uma unidade base interna para simplificar a conversão entre múltiplas unidades:
  - Tempo base: segundo
  - Velocidade base: m/s  
  - Peso base: quilograma
  - Temperatura base: celsius
  - Distancia base: metros
  - Moeda base: real
- A lógica é sempre:  
  1. Converter o valor de origem para a unidade base
  2. Converter da unidade base para a unidade destino 

- Cada Activity possui dois métodos principais:
  1. realizarConversao(): pega o valor digitado, as unidades selecionadas (de origem e destino), chama o metodo de converter e atualiza o resultado na tela
  2. converter(valor, origem, destino): faz a conversão da categoria (tempo, velocidade, peso, etc...) 

- ArrayAdapter conecta os arrays de unidades aos Spinners
- Cada Spinner exibe uma lista de opções e mantém um item selecionado por padrão

- Vídeo de demonstração: https://youtube.com/shorts/vF2FtqBl84w
