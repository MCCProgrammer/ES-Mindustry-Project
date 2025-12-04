# User story 3

Estatísticas específicas por planeta.

## Author(s)

Miguel Carmo 65871

## Reviewer(s)

(Please add the user story reviewer(s) here, one in each line, providing the authors' name and surname, along with their student number. In the reviews presented in this document, add the corresponding reviewers.)

## User Story:

**As a** player  
**I want** to see statistics tracked individually for each planet  
**So that** I can track my progress and performance on different planets separately

## Review

(Please add your user story review here)

## Use case diagram

<img width="712" height="537" alt="image" src="https://github.com/user-attachments/assets/7e700271-a0d9-49ef-a15b-a74b4da77154" />


## Use case textual description

### Use Case: View Planet Statistics

**Descrição**: O jogador visualiza as estatísticas específicas de um planeta através de um diálogo dedicado. Este caso inclui obrigatoriamente a exibição de todas as métricas rastreadas para o planeta selecionado.

**Ator Principal**: Player.

**Pre-condições**:
- O jogador tem que estar na tela de seleção de planetas (PlanetDialog)
- O sistema de estatísticas está inicializado

**Cenário**:
1. O jogador abre a tela de seleção de planetas
2. O jogador clica no botão "Planet Statistics" OU pressiona a tecla 'S'
3. O sistema abre o PlanetStatsDialog para o planeta atual
4. O sistema carrega as PlanetStats do planeta selecionado
5. O sistema exibe todas as estatísticas rastreadas:
   - Tempo jogado (formatado)
   - Distância percorrida (formatada)
   - Mortes do jogador
   - Inimigos destruídos
   - Ondas sobrevividas (se aplicável)
   - Unidades criadas
   - Edifícios construídos
   - Edifícios destruídos
   - Edifícios desconstruídos
6. As estatísticas são exibidas com animações de contador
7. O jogador visualiza as estatísticas
8. O jogador fecha o diálogo (clicando no botão voltar ou pressionando 'S'/Escape)

**Inclui** Load Statistics que é responsável por carregar as estatísticas do armazenamento persistente.

**Extende** No Statistics Available caso o planeta não tenha estatísticas ainda (exibe valores zero).

### Use Case: Track Building Statistics

**Descrição**: O sistema rastreia estatísticas de edifícios quando eventos relacionados ocorrem durante o jogo.

**Ator Principal**: Sistema.

**Pre-condições**:
- O jogador está em um jogo ativo
- Eventos de construção/destruição ocorrem

**Cenário**:
1. Um evento de construção é disparado (BlockBuildEndEvent)
2. O sistema identifica o planeta atual do jogo
3. O sistema obtém as PlanetStats do planeta
4. O sistema incrementa o contador apropriado (buildingsBuilt)
5. O sistema salva as estatísticas atualizadas

**Inclui** Update Statistics que é responsável por atualizar e persistir as estatísticas.

### Use Case: Track Unit Statistics

**Descrição**: O sistema rastreia estatísticas de unidades quando unidades são criadas ou destruídas.

**Ator Principal**: Sistema.

**Pre-condições**:
- O jogador está em um jogo ativo
- Eventos de unidade ocorrem

**Cenário**:
1. Um evento de unidade é disparado (UnitCreateEvent ou UnitDestroyEvent)
2. O sistema identifica o planeta atual
3. O sistema obtém as PlanetStats do planeta
4. O sistema verifica o tipo de evento:
   - Se UnitCreateEvent: incrementa unitsCreated
   - Se UnitDestroyEvent: verifica se é unidade do jogador ou inimiga
     - Se do jogador: incrementa playerDeaths
     - Se inimiga: incrementa enemyUnitsDestroyed
5. O sistema salva as estatísticas atualizadas

**Inclui** Update Statistics e Identify Unit Type que determina se a unidade pertence ao jogador.

### Use Case: Track Wave Statistics

**Descrição**: O sistema rastreia a maior onda alcançada quando uma onda é completada.

**Ator Principal**: Sistema.

**Pre-condições**:
- O jogador está em um jogo ativo com ondas habilitadas
- Uma onda é completada

**Cenário**:
1. Uma onda é completada (GameOverEvent ou wave completion)
2. O sistema identifica o planeta atual
3. O sistema obtém as PlanetStats do planeta
4. O sistema compara a onda atual com wavesLasted
5. Se a onda atual for maior, o sistema atualiza wavesLasted
6. O sistema salva as estatísticas atualizadas

**Inclui** Update Statistics e Compare Wave que compara a onda atual com o recorde.

## Review

(Please add your use case review here)

## Implementation documentation

### Implementation summary

A implementação segue uma arquitetura modular baseada no padrão MVC (Model-View-Controller) para garantir separação de responsabilidades e facilitar manutenção.

**Model (Dados e Persistência)**: A classe `PlanetStats` armazena todas as estatísticas específicas do planeta (mortes, inimigos destruídos, ondas, edifícios, unidades, tempo jogado, distância). A persistência é gerida através de `Core.settings.putJson()` com chave `{planetName}-stats`, garantindo estatísticas independentes por planeta.

**View (Interface)**: A classe `PlanetStatsDialog` (estende `BaseDialog`) gere a interface gráfica com componentes `StatLabel` e `StatLabelString` para exibir estatísticas com animações. Inclui formatação especializada para tempo (milissegundos → formato legível) e distância (metros/quilômetros).

**Controller (Lógica de Negócio)**: A classe `Planet` integra `PlanetStats` e fornece métodos para carregar, salvar e aceder estatísticas. As classes `Logic` e `Control` interceptam eventos do jogo (BlockBuildEndEvent, BlockDestroyEvent, UnitCreateEvent, UnitDestroyEvent, GameOverEvent) e atualizam as estatísticas do planeta atual.

**Integração de Eventos**: O sistema utiliza o sistema de eventos do Mindustry. Cada handler: (1) identifica o planeta atual, (2) obtém `PlanetStats` via `Planet.getStats()`, (3) atualiza a estatística apropriada, (4) salva para persistência.

**Gestão de Estado**: Estatísticas são carregadas automaticamente na inicialização do planeta. O método `getStats()` garante sempre uma instância válida, criando uma nova se necessário, prevenindo erros de null pointer.

**Acesso à Interface**: Integração com `PlanetDialog` permite acesso através de botão "Planet Statistics", atalho 'S', e tooltip informativo.

**Separação de Responsabilidades**: `PlanetStats` (dados), `Planet` (gestão e persistência), `Logic/Control` (atualização por eventos), `PlanetStatsDialog` (apresentação), `StatLabel/StatLabelString` (componentes UI reutilizáveis). Esta arquitetura facilita testes, manutenção e extensão futura.

## Review

(Please add your implementation summary review here)

## Class diagrams

<img width="1538" height="759" alt="image" src="https://github.com/user-attachments/assets/322dbeb1-41a3-40e2-967e-59da2d65a0df" />


### Diagrama de Classes

O diagrama demonstra uma arquitetura MVC com separação clara entre dados, lógica e apresentação.

**Classes Principais**:
- **PlanetStats** (Entity): Armazena todas as estatísticas (playerDeaths, enemyUnitsDestroyed, wavesLasted, buildingsBuilt, etc.)
- **Planet** (Entity/Controller): Integra PlanetStats e fornece métodos de gestão (getStats, saveStats, loadStats)
- **PlanetStatsDialog** (View): Interface gráfica que estende BaseDialog. Métodos públicos: `show()`, `addStat()`, `addStatString()`, `formatTime()`, `formatDistance()`
- **Logic** (Controller): Intercepta eventos de construção e unidades, atualizando PlanetStats
- **Control** (Controller): Intercepta eventos de ondas, atualizando PlanetStats
- **StatLabel** (View Component): Exibe estatísticas numéricas com animações
- **StatLabelString** (View Component): Exibe estatísticas formatadas como strings (tempo, distância) com animações. Depende de PlanetStats.

## Review

(Please add your class diagram review here)

## Sequence diagram

<img width="782" height="754" alt="image" src="https://github.com/user-attachments/assets/35ab8b37-d89e-4814-af03-16fa9fe816e7" />

O diagrama de sequência ilustra o fluxo completo de visualização de estatísticas, desde a abertura do diálogo até à exibição das estatísticas formatadas. Mostra as interações entre Player, PlanetDialog, PlanetStatsDialog, Planet e PlanetStats, incluindo o carregamento de dados, formatação e exibição com animações.

## Review

(Please add your sequence diagram review here)

## Test specifications

Estratégia de testes focada em Testes Unitários (JUnit) para validar a lógica de rastreamento de estatísticas e integridade dos dados, isolando componentes das dependências gráficas do motor de jogo (Arc/Mindustry).

### 1. Testes de Persistência de Estatísticas (PlanetStatsTest.java)

Esta classe de teste valida a serialização e deserialização de PlanetStats, garantindo que as estatísticas persistem corretamente entre sessões de jogo.

**TC01 - Statistics Serialization (Serialização de Estatísticas)**:

**Objetivo**: Verificar se a classe PlanetStats pode ser serializada e deserializada corretamente usando JSON.

**Cenário**: Criar uma instância de PlanetStats com valores conhecidos, serializar para JSON, deserializar e comparar os valores.

**Resultado Esperado**: Todos os campos devem ser iguais após deserialização, mantendo a integridade dos dados.

**TC02 - Statistics Initialization (Inicialização)**:

**Objetivo**: Verificar se uma nova instância de PlanetStats inicia com valores zero.

**Cenário**: Criar uma nova instância de PlanetStats sem definir valores.

**Resultado Esperado**: Todos os campos numéricos devem ser 0, e os objetos devem ser inicializados corretamente.

**TC03 - Statistics Update (Atualização)**:

**Objetivo**: Validar que os métodos de atualização funcionam corretamente.

**Cenário**: Criar PlanetStats, incrementar vários campos, verificar valores.

**Resultado Esperado**: Cada campo deve refletir o número correto de incrementos.

### 2. Testes de Lógica de Rastreamento (PlanetStatsTrackingTest.java)

Esta classe testa a lógica de rastreamento de eventos e atualização de estatísticas.

**TC04 - Building Built Tracking (Rastreamento de Construção)**:

**Objetivo**: Verificar se eventos de construção incrementam corretamente buildingsBuilt.

**Cenário**: Simular BlockBuildEndEvent, verificar se buildingsBuilt é incrementado.

**Resultado Esperado**: buildingsBuilt deve aumentar em 1 após cada evento de construção.

### 3. Testes de Formatação (PlanetStatsDialogTest.java)

Esta classe testa os métodos de formatação de valores para exibição.

**TC08 - Time Formatting (Formatação de Tempo)**:

**Objetivo**: Verificar se o método formatTime converte milissegundos corretamente.

**Cenário**: Testar com diferentes valores (1000ms, 60000ms, 3600000ms).

**Resultado Esperado**: Deve retornar formato legível (ex: "1s", "1m", "1h").

**TC09 - Distance Formatting (Formatação de Distância)**:

**Objetivo**: Verificar se o método formatDistance converte metros/quilômetros corretamente.

**Cenário**: Testar com valores abaixo e acima de 1000m.

**Resultado Esperado**: 
- 1000m: formato em metros (ex: "500.0 m")
- 1000m: formato em quilômetros (ex: "1.5 km")

**TC10 - Null Handling (Tratamento de Null)**:

**Objetivo**: Verificar robustez ao lidar com valores null ou inválidos.

**Cenário**: Passar null ou valores negativos para métodos de formatação.

**Resultado Esperado**: Sistema deve tratar graciosamente sem lançar exceções.

### 4. Testes de Integração (PlanetStatsIntegrationTest.java)

Esta classe testa a integração entre componentes.

**TC11 - Planet Statistics Integration (Integração)**:

**Objetivo**: Verificar que Planet.getStats() retorna instância válida e pode ser atualizada.

**Cenário**: Criar Planet, obter stats, atualizar, verificar persistência.

**Resultado Esperado**: Estatísticas devem ser acessíveis e atualizáveis através de Planet.

**TC12 - Event Flow Integration (Integração de Fluxo de Eventos)**:

**Objetivo**: Verificar que eventos do jogo atualizam estatísticas corretamente.

**Cenário**: Simular fluxo completo: evento → Logic → Planet → PlanetStats → save.

**Resultado Esperado**: Estatísticas devem ser atualizadas e persistidas após eventos.

## Review

(Please add your test specification review here)

