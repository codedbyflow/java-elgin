# Sistema de Impressão Elgin

Sistema em Java para controle de impressoras Elgin usando JNA.

## Sobre

Projeto feito para integrar com impressoras de caixa Elgin. Usa JNA pra chamar as funções da DLL da impressora.

## Funcionalidades

- Configurar e abrir conexão com a impressora
- Imprimir texto
- Imprimir QR Code
- Imprimir código de barras
- Imprimir XML SAT e cancelamento
- Abrir gaveta
- Sinal sonoro
- Corte de papel

## Como usar

Execute o programa e escolha uma opção do menu:

1 - Configurar Conexao
2 - Abrir Conexao
3 - Impressao Texto
4 - Impressao QRCode
5 - Impressao Cod Barras
6 - Impressao XML SAT
7 - Impressao XML Canc SAT
8 - Abrir Gaveta Elgin
9 - Abrir Gaveta
10 - Sinal Sonoro
0 - Fechar Conexao e Sair

## Requisitos

- Java instalado
- DLL E1_Impressora01.dll na pasta lib
- JNA (jna-5.15.0.jar)
- Impressora Elgin conectada

## Observações

Configure a conexão antes de abrir. Todas as operações precisam de conexão ativa. Sempre feche a conexão quando terminar.

## Desenvolvedores

Kaio, Felipe, Igor, Mauricio, Fernando
