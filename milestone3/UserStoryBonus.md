# User Story: Visualização do Progresso da Campanha

## Author(s)

Miguel Carmo 65871

## Reviewer(s)

(Please add the user story reviewer(s) here, one in each line, providing the authors' name and surname, along with their student number.)

## User Story:

**As a** player  
**I want** to see a visual representation of my campaign progress  
**So that** I can understand my overall advancement

**Como** jogador  
**Quero** ver uma representação visual do meu progresso na campanha  
**Para que** possa entender o meu avanço geral

## Implementation Summary

Esta funcionalidade adiciona um display de progresso da campanha à tela de seleção de planetas, mostrando setores capturados vs total de setores, uma barra de progresso visual e percentagem de conclusão.

**Detalhes de Implementação**:
- Localização: `PlanetDialog.java` (linhas 719-755)
- Lógica de cálculo de progresso que conta setores da campanha
- Display de UI posicionado no topo central do ecrã
- Atualizações em tempo real quando setores são capturados
- Funcionalidade de debug (Ctrl+C) para testes

**Ficheiros Modificados**:
- `core/src/mindustry/ui/dialogs/PlanetDialog.java` - Adicionado display de progresso e método de debug

**Nota sobre Documentação**: Esta funcionalidade foi implementada como um requisito obrigatório do milestone. No entanto, devido à sua natureza complementar e implementação mais simples comparada à user story principal (Planet Statistics), não foi desenvolvida uma suite completa de documentação (use cases detalhados, diagramas de classes, diagramas de sequência). A implementação foca-se em melhorar a experiência de seleção de planetas com um indicador visual de progresso, e o código é autoexplicativo através da sua integração direta na classe existente `PlanetDialog`. Para informação técnica detalhada, consulte a implementação do código fonte.

