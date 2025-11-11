# Conversor de Unidades

Um aplicativo Android moderno e funcional para conversão de unidades em diferentes categorias. Desenvolvido com Material Design e interface intuitiva.

## 📱 Sobre o Projeto

O Conversor de Unidades é um aplicativo Android que permite realizar conversões entre diferentes unidades de medida em 6 categorias principais:

- 🌡️ **Temperatura**: Celsius, Fahrenheit, Kelvin
- ⚖️ **Peso**: Quilograma, Grama, Libra, Onça, Tonelada
- 📏 **Distância**: Metro, Quilômetro, Centímetro, Milha, Pé, Polegada
- 🚗 **Velocidade**: km/h, m/s, mi/h, pés/s, nós
- ⏰ **Tempo**: Segundo, Minuto, Hora, Dia, Semana, Mês, Ano
- 💰 **Moeda**: Real, Dólar, Euro, Libra, Iene

## ✨ Características

- ✅ Interface moderna com Material Design
- ✅ Conversão em tempo real enquanto você digita
- ✅ Design responsivo e adaptável
- ✅ Layout totalmente em ConstraintLayout
- ✅ Código bem documentado em português
- ✅ Navegação intuitiva entre categorias
- ✅ Cores e ícones personalizados

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java
- **SDK Mínimo**: Android 8.1 (API 27)
- **SDK Alvo**: Android 14 (API 36)
- **Bibliotecas Principais**:
  - AndroidX AppCompat
  - Material Design Components
  - ConstraintLayout
  - CardView
  - CoordinatorLayout
  - EdgeToEdge (para experiência imersiva)

## 📁 Estrutura do Projeto

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/conversordeunidades/
│   │   │   ├── MainActivity.java              # Tela principal com grid de cards
│   │   │   ├── TemperaturaActivity.java       # Conversão de temperatura
│   │   │   ├── PesoActivity.java              # Conversão de peso
│   │   │   ├── DistanciaActivity.java         # Conversão de distância
│   │   │   ├── VelocidadeActivity.java        # Conversão de velocidade
│   │   │   ├── TempoActivity.java             # Conversão de tempo
│   │   │   └── MoedaActivity.java             # Conversão de moeda
│   │   │
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml          # Layout da tela principal
│   │   │   │   └── activity_conversor.xml     # Layout das telas de conversão
│   │   │   │
│   │   │   ├── values/
│   │   │   │   ├── colors.xml                 # Paleta de cores
│   │   │   │   ├── strings.xml                 # Strings do aplicativo
│   │   │   │   └── themes.xml                 # Temas do aplicativo
│   │   │   │
│   │   │   └── drawable/
│   │   │       ├── ic_temperature.xml         # Ícone de temperatura
│   │   │       ├── ic_weight.xml              # Ícone de peso
│   │   │       ├── ic_length.xml               # Ícone de distância
│   │   │       ├── ic_speed.xml                # Ícone de velocidade
│   │   │       ├── ic_time.xml                 # Ícone de tempo
│   │   │       └── ic_currency.xml             # Ícone de moeda
│   │   │
│   │   └── AndroidManifest.xml                # Configuração do aplicativo
```

## 🎨 Design

### Cores Principais
- **Laranja Primário**: `#FF6B35` - Usado no header e elementos de destaque
- **Branco**: `#FFFFFF` - Fundo e cards
- **Texto Primário**: `#333333` - Texto principal
- **Texto Secundário**: `#666666` - Texto secundário

### Layout
- **Tela Principal**: Grid de 3 linhas × 2 colunas com cards clicáveis
- **Telas de Conversão**: Interface com dois cards (origem e destino) e spinners para seleção de unidades
- **100% ConstraintLayout**: Todo o layout utiliza ConstraintLayout para máxima flexibilidade e responsividade

## 🚀 Como Executar

### Pré-requisitos
- Android Studio (versão mais recente recomendada)
- JDK 11 ou superior
- Android SDK 27 ou superior
- Emulador Android ou dispositivo físico com Android 8.1+

### Passos

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd Conversordeunidades
   ```

2. **Abra o projeto no Android Studio**
   - Abra o Android Studio
   - Selecione "Open an Existing Project"
   - Navegue até a pasta do projeto e selecione

3. **Sincronize o Gradle**
   - O Android Studio deve sincronizar automaticamente
   - Se não, clique em "Sync Now" ou `File > Sync Project with Gradle Files`

4. **Execute o aplicativo**
   - Conecte um dispositivo Android ou inicie um emulador
   - Clique no botão ▶️ (Run) ou pressione `Shift + F10`
   - Selecione o dispositivo/emulador desejado

## 💻 Como Usar

1. **Tela Principal**
   - Ao abrir o app, você verá um grid com 6 cards representando as categorias
   - Toque em qualquer card para abrir a tela de conversão correspondente

2. **Tela de Conversão**
   - Selecione a unidade de origem no spinner superior
   - Digite o valor a ser convertido no campo de entrada
   - Selecione a unidade de destino no spinner inferior
   - O resultado aparecerá automaticamente no campo de saída (conversão em tempo real)
   - Use o botão de voltar (←) para retornar à tela principal

## 🔧 Arquitetura do Código

### Padrão de Conversão
Todas as Activities de conversão seguem o mesmo padrão:

1. **Unidade Base**: Cada categoria tem uma unidade base (ex: Celsius para temperatura, Metro para distância)
2. **Conversão em 2 Etapas**:
   - Primeiro: converte da unidade de origem para a unidade base
   - Segundo: converte da unidade base para a unidade de destino

**Vantagens desta abordagem**:
- Evita criar uma matriz N×N de conversões
- Facilita adicionar novas unidades
- Código mais limpo e fácil de manter

### Exemplo de Conversão (Temperatura)
```java
// Converter 100°F para Celsius:
// 1. Fahrenheit → Celsius: (100 - 32) * 5/9 = 37.78°C
// 2. Celsius → Celsius: 37.78°C (já está na unidade base)
```

## 📝 Funcionalidades Técnicas

### EdgeToEdge
O aplicativo utiliza `EdgeToEdge` para uma experiência imersiva, permitindo que o conteúdo se estenda até as bordas da tela, incluindo áreas de sistema.

### TextWatcher
Implementa `TextWatcher` para conversão em tempo real, proporcionando feedback imediato ao usuário enquanto digita.

### Window Insets
Utiliza `ViewCompat.setOnApplyWindowInsetsListener` para ajustar automaticamente o padding quando as barras do sistema mudam.

## 🎯 Melhorias Futuras

- [ ] Adicionar mais categorias de conversão
- [ ] Integração com API de câmbio em tempo real
- [ ] Histórico de conversões
- [ ] Favoritos de conversões frequentes
- [ ] Modo escuro
- [ ] Suporte a mais idiomas
- [ ] Widgets para tela inicial

## 📄 Licença

Este projeto é de código aberto e está disponível para uso educacional e pessoal.

## 👨‍💻 Desenvolvimento

- **Código**: Totalmente comentado em português
- **Variáveis**: Todas em português para melhor legibilidade
- **Documentação**: Métodos e classes documentados com JavaDoc
- **Padrões**: Segue as melhores práticas do Android

## 📞 Suporte

Para questões, sugestões ou problemas, abra uma issue no repositório do projeto.

---

**Desenvolvido com ❤️ usando Android e Material Design**

