# 🖨️ Sistema de Impressão Elgin

> **Sistema PDV desenvolvido em Java para controle e comunicação com impressoras de caixa Elgin**

---

## 📋 Sobre o Projeto

Sistema desenvolvido para facilitar a integração e controle de impressoras de caixa da marca Elgin através de uma interface Java. O projeto utiliza JNA (Java Native Access) para comunicação nativa com a DLL proprietária da impressora, permitindo operações completas de impressão e controle de periféricos.

---

## ✨ Recursos Disponíveis

### 🔌 Gerenciamento de Conexão
- Configuração personalizada de conexão (tipo, modelo, porta, parâmetros)
- Abertura e fechamento seguro de conexões
- Validação de estado da conexão

### 📄 Impressão de Conteúdo
- **Texto simples** — Impressão de textos formatados
- **QR Code** — Geração e impressão de códigos QR
- **Código de Barras** — Suporte a diversos formatos de código de barras
- **XML SAT** — Impressão de documentos fiscais (SAT e cancelamentos)

### 🎛️ Controles de Hardware
- Corte automático de papel
- Avanço de papel programável
- Abertura de gaveta (Elgin e genérica)
- Emissão de sinais sonoros

---

## 🛠️ Stack Tecnológica

| Tecnologia | Descrição |
|------------|-----------|
| **Java** | Linguagem principal do projeto |
| **JNA** | Biblioteca para acesso a funções nativas |
| **DLL Elgin** | Driver proprietário para controle da impressora |
| **Console** | Interface de linha de comando |

---

## 🚀 Guia de Uso

### Menu Principal

Ao executar o sistema, você terá acesso ao menu interativo com as seguintes opções:

```
┌─────────────────────────────────────┐
│   SISTEMA DE IMPRESSÃO ELGIN        │
├─────────────────────────────────────┤
│ 1. Configurar conexão               │
│ 2. Abrir conexão                    │
│ 3. Imprimir conteúdo                │
│ 4. Imprimir XML SAT                 │
│ 5. Controles de hardware            │
│ 6. Fechar conexão                   │
│ 0. Sair                             │
└─────────────────────────────────────┘
```

### Fluxo de Trabalho Recomendado

1. **Configurar** → Defina os parâmetros de conexão
2. **Conectar** → Estabeleça comunicação com a impressora
3. **Imprimir** → Execute as operações desejadas
4. **Desconectar** → Encerre a conexão adequadamente

---

## 🔧 API Principal

### Interface `ImpressoraDLL`

A interface principal mapeia os métodos nativos da DLL para uso em Java:

#### Métodos de Conexão
- `AbreConexaoImpressora(...)` — Inicia conexão com a impressora
- `FechaConexaoImpressora()` — Encerra a conexão

#### Métodos de Impressão
- `ImpressaoTexto(...)` — Envia texto para impressão
- `ImpressaoQRCode(...)` — Gera e imprime QR Code
- `ImpressaoCodigoBarras(...)` — Imprime código de barras
- `ImprimeXMLSAT(...)` — Processa XML SAT
- `ImprimeXMLCancelamentoSAT(...)` — Processa cancelamento SAT

#### Métodos de Controle
- `AvancaPapel(...)` — Avança papel na impressora
- `Corte(...)` — Executa corte de papel
- `AbreGavetaElgin()` / `AbreGaveta(...)` — Controla gaveta de dinheiro
- `SinalSonoro(...)` — Emite alertas sonoros

---

## ⚠️ Requisitos e Observações

### Pré-requisitos
- Java Runtime Environment (JRE) instalado
- DLL da impressora Elgin disponível no sistema
- Impressora Elgin conectada e configurada

### Importante
- ⚠️ Configure a conexão antes de tentar abrir
- ⚠️ Todas as operações de impressão requerem conexão ativa
- ⚠️ Mensagens de erro são exibidas no console para diagnóstico
- ⚠️ Sempre feche a conexão ao finalizar o uso

---

## 👥 Equipe de Desenvolvimento

Desenvolvido com dedicação por:
- Kaio
- Felipe
- Igor
- Mauricio
- Fernando

---

## 📝 Licença

Este projeto é fornecido como está, para uso em sistemas PDV.

