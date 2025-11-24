import com.sun.jna.Library;
import com.sun.jna.Native;

import java.util.Scanner;
import java.nio.file.*;

public class Main {


    public interface ImpressoraDLL extends Library {

        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                Paths.get(System.getProperty("user.dir"), "lib", "E1_Impressora01.dll").toString(), ImpressoraDLL.class); // Adicione o Caminho completo para a DLL

        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int parametro);

        int FechaConexaoImpressora();

        // Impressão de texto: String, int, int, int
        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);

        // Corte: int
        int Corte(int avanco);

        // Impressão de QR Code: String, int, int
        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);

        // Impressão de código de barras: int, String, int, int, int
        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);

        // Avançar papel: int
        int AvancaPapel(int linhas);

        int AbreGavetaElgin(int pino, int ti, int tf);

        // Abre gaveta: int, int, int
        int AbreGaveta(int pino, int ti, int tf);

        // Sinal sonoro: int, int, int
        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);

        // Imprime XML SAT: String, int
        int ImprimeXMLSAT(String dados, int param);

        // Imprime XML Cancelamento SAT: String, String, int
        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);

    }

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;
    private static final Scanner input = new Scanner(System.in); // Scanner estÃ¡tico e final


    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return input.nextLine();
    }



    public static void configurarConexao() {
        if (!conexaoAberta) {
            System.out.println("Digite o tipo de conexao (1 para USB, 2 para Serial, etc.): ");
            tipo = input.nextInt(); // Lê o tipo de conexão como inteiro
            input.nextLine(); // Consumir quebra de linha restante

            System.out.println("Digite o modelo da impressora: ");
            modelo = input.nextLine(); // Lê o modelo da impressora

            System.out.println("Digite a porta de conexão (ex: USB): ");
            conexao = input.nextLine(); // Lê a porta de conexão

            System.out.println("Digite o parâmetro adicional (ex: 0 para padrão): ");
            parametro = input.nextInt();
            input.nextLine(); // Consumir quebra de linha restante

            System.out.println("Parâmetros de conexão configurados com sucesso.");
        } else {
            System.out.println("Conexão já configurada. Pronta para uso.");
        }

    }

    public static void abrirConexao() {
        // Função para abrir a conexão com a impressora
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Conexão já está aberta.");
        }
    }

    public static void fecharConexao() {
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
            conexaoAberta = false;
            System.out.println("Conexão fechada.");
        } else {
            System.out.println("Nenhuma conexão aberta para fechar.");
        }

    }


    public static void exibirMenuOpcoes() {
        System.out.println("\n*************************************************");
        System.out.println("**************** MENU IMPRESSORA *******************");
        System.out.println("*************************************************\n");

        System.out.println("1  - Configurar Conexao");
        System.out.println("2  - Abrir Conexao");
        System.out.println("3  - Impressao Texto");
        System.out.println("4  - Impressao QRCode");
        System.out.println("5  - Impressao Cod Barras");
        System.out.println("6  - Impressao XML SAT");
        System.out.println("7  - Impressao XML Canc SAT");
        System.out.println("8  - Abrir Gaveta Elgin");
        System.out.println("9  - Abrir Gaveta");
        System.out.println("10 - Sinal Sonoro");
        System.out.println("0  - Fechar Conexao e Sair");
        System.out.println("--------------------------------------");
    }

    public static void ImpressaoTexto() {
        if (conexaoAberta) {
            int ret = ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste de impressao", 1, 4, 0);
            ImpressoraDLL.INSTANCE.AvancaPapel(2);
            ImpressoraDLL.INSTANCE.Corte(2);
            System.out.println(ret);
        } else {
            System.out.println("Erro: Conexão não está aberta.");

        }
    }


    public static void ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao) {

        if (conexaoAberta == true) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode("Teste de impressao", 6, 4);
            ImpressoraDLL.INSTANCE.AvancaPapel(2);
            ImpressoraDLL.INSTANCE.Corte(2);
            System.out.println("Impressao Realizada");
        } else {
            System.out.println("Conexão está fechada!");
        }
    }

    public static void ImpressaoCodigoBarras() {
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);
            ImpressoraDLL.INSTANCE.AvancaPapel(2);
            ImpressoraDLL.INSTANCE.Corte(2);
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
    }

    public static void ImprimeXMLSAT(String dados, int parametro) {

        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT("\n" +
                    "<CFe>\n" +
                    "    <infCFe Id=\"CFe13190814200166000166599000178400000148075811\" versao=\"0.08\" versaoDadosEnt=\"0.08\" versaoSB=\"010103\">\n" +
                    "        <ide>\n" +
                    "            <cUF>13</cUF>\n" +
                    "            <cNF>807581</cNF>\n" +
                    "            <mod>59</mod>\n" +
                    "            <nserieSAT>900017840</nserieSAT>\n" +
                    "            <nCFe>000014</nCFe>\n" +
                    "            <dEmi>20190814</dEmi>\n" +
                    "            <hEmi>163227</hEmi>\n" +
                    "            <cDV>1</cDV>\n" +
                    "            <tpAmb>2</tpAmb>\n" +
                    "            <CNPJ>16716114000172</CNPJ>\n" +
                    "            <signAC>SGR-SAT SISTEMA DE GESTAO E RETAGUARDA DO SAT</signAC>\n" +
                    "            <assinaturaQRCODE>kAmMUV2AFOP11vsbAwb4S1MrcnzIm8o6trefwjpRvpJaNGeXXcM2GKbs+aALc3WDxrimeOf9BdgoZl29RvtC1DmvqZ56oVEoRz0ymia1tHUdGPzuO035OuiEseEj3+gU+8NzGzqWQIJgqdgLOZgnmUP2aBOkC0QcokhHPVfwm9tJFQnQkzGzu4692+LtpxhLwEhnFjoZh+1hpBXn5AEcbMS4Lx751qvFlfZX0hsYJf5pKcFH88E3byU82MU8UD5p9fyX2qL5Ae+Uql1VLPqoJOsQmC2BCBkMW7oQRCCR0osz6eLX1DHHJU+stxKCkITlQnz6XJd4LKXifGZuZ25+Uw==</assinaturaQRCODE>\n" +
                    "            <numeroCaixa>001</numeroCaixa>\n" +
                    "        </ide>\n" +
                    "        <emit>\n" +
                    "            <CNPJ>14200166000166</CNPJ>\n" +
                    "            <xNome>ELGIN INDUSTRIAL DA AMAZONIA LTDA</xNome>\n" +
                    "            <enderEmit>\n" +
                    "                <xLgr>AVENIDA ABIURANA</xLgr>\n" +
                    "                <nro>579</nro>\n" +
                    "                <xBairro>DIST INDUSTRIAL</xBairro>\n" +
                    "                <xMun>MANAUS</xMun>\n" +
                    "                <CEP>69075010</CEP>\n" +
                    "            </enderEmit>\n" +
                    "            <IE>111111111111</IE>\n" +
                    "            <cRegTrib>3</cRegTrib>\n" +
                    "            <indRatISSQN>N</indRatISSQN>\n" +
                    "        </emit>\n" +
                    "        <dest>\n" +
                    "            <CPF>14808815893</CPF>\n" +
                    "        </dest>\n" +
                    "        <det nItem=\"1\">\n" +
                    "            <prod>\n" +
                    "                <cProd>0000000000001</cProd>\n" +
                    "                <xProd>PRODUTO NFCE 1</xProd>\n" +
                    "                <NCM>94034000</NCM>\n" +
                    "                <CFOP>5102</CFOP>\n" +
                    "                <uCom>UN</uCom>\n" +
                    "                <qCom>1.0000</qCom>\n" +
                    "                <vUnCom>3.51</vUnCom>\n" +
                    "                <vProd>3.51</vProd>\n" +
                    "                <indRegra>T</indRegra>\n" +
                    "                <vItem>3.00</vItem>\n" +
                    "                <vRatDesc>0.51</vRatDesc>\n" +
                    "            </prod>\n" +
                    "            <imposto>\n" +
                    "                <ICMS>\n" +
                    "                    <ICMS00>\n" +
                    "                        <Orig>0</Orig>\n" +
                    "                        <CST>00</CST>\n" +
                    "                        <pICMS>7.00</pICMS>\n" +
                    "                        <vICMS>0.21</vICMS>\n" +
                    "                    </ICMS00>\n" +
                    "                </ICMS>\n" +
                    "                <PIS>\n" +
                    "                    <PISAliq>\n" +
                    "                        <CST>01</CST>\n" +
                    "                        <vBC>6.51</vBC>\n" +
                    "                        <pPIS>0.0165</pPIS>\n" +
                    "                        <vPIS>0.11</vPIS>\n" +
                    "                    </PISAliq>\n" +
                    "                </PIS>\n" +
                    "                <COFINS>\n" +
                    "                    <COFINSAliq>\n" +
                    "                        <CST>01</CST>\n" +
                    "                        <vBC>6.51</vBC>\n" +
                    "                        <pCOFINS>0.0760</pCOFINS>\n" +
                    "                        <vCOFINS>0.49</vCOFINS>\n" +
                    "                    </COFINSAliq>\n" +
                    "                </COFINS>\n" +
                    "            </imposto>\n" +
                    "        </det>\n" +
                    "        <total>\n" +
                    "            <ICMSTot>\n" +
                    "                <vICMS>0.21</vICMS>\n" +
                    "                <vProd>3.51</vProd>\n" +
                    "                <vDesc>0.00</vDesc>\n" +
                    "                <vPIS>0.11</vPIS>\n" +
                    "                <vCOFINS>0.49</vCOFINS>\n" +
                    "                <vPISST>0.00</vPISST>\n" +
                    "                <vCOFINSST>0.00</vCOFINSST>\n" +
                    "                <vOutro>0.00</vOutro>\n" +
                    "            </ICMSTot>\n" +
                    "            <vCFe>3.00</vCFe>\n" +
                    "            <DescAcrEntr>\n" +
                    "                <vDescSubtot>0.51</vDescSubtot>\n" +
                    "            </DescAcrEntr>\n" +
                    "            <vCFeLei12741>0.56</vCFeLei12741>\n" +
                    "        </total>\n" +
                    "        <pgto>\n" +
                    "            <MP>\n" +
                    "                <cMP>01</cMP>\n" +
                    "                <vMP>6.51</vMP>\n" +
                    "            </MP>\n" +
                    "            <vTroco>3.51</vTroco>\n" +
                    "        </pgto>\n" +
                    "        <infAdic>\n" +
                    "            <infCpl>Trib aprox R$ 0,36 federal, R$ 1,24 estadual e R$ 0,00 municipal<br>CAIXA: 001 OPERADOR: ROOT</infCpl>\n" +
                    "            <obsFisco xCampo=\"04.04.05.04\">\n" +
                    "                <xTexto>Comete crime quem sonega</xTexto>\n" +
                    "            </obsFisco>\n" +
                    "        </infAdic>\n" +
                    "    </infCFe>\n" +
                    "    <Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                    "        <SignedInfo>\n" +
                    "            <CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\n" +
                    "            <SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/>\n" +
                    "            <Reference URI=\"#CFe13190814200166000166599000178400000148075811\">\n" +
                    "                <Transforms>\n" +
                    "                    <Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\n" +
                    "                    <Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\n" +
                    "                </Transforms>\n" +
                    "                <DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
                    "                <DigestValue>rozf1i6JehNUqx8tfP1FG3QVUIxlrcHgozaB4LAjkDM=</DigestValue>\n" +
                    "            </Reference>\n" +
                    "        </SignedInfo>\n" +
                    "        <SignatureValue>cAiGHfx3QxIhrmz3b36Z1EBs/TzLXKQkE5Ykn3Q1HOEKpshyZRaOLKlg6LiHjFgaZNKZnwWkKLQav2Af342Xc3XIkIjOF72vmLZxd/u6naZ3lYVWcf/G2YYdMpUAoqfpihLilVZZMqAIQQ/SW76mGstSI743lc0FL1NuMXSvAT3b9X1JcaC1r4uHezYGp/iSrX/lxfdnu4ZP2gzJ7KEnRvrTm9TCF3mA0zhDF5iem4vJC8bS/+OjhKhKfDeyxkrPDQc8+n7brALOYWbR3RUleMMKCIQf/nxaEy9XEsb53rC4KXLe5JL15YCxQ8TRYU6vvLoqFecd72HebFQ/C2BvgA==</SignatureValue>\n" +
                    "        <KeyInfo>\n" +
                    "            <X509Data>\n" +
                    "                <X509Certificate>MIIFzTCCBLWgAwIBAgICH9owDQYJKoZIhvcNAQENBQAwaDELMAkGA1UEBhMCQlIxEjAQBgNVBAgMCVNBTyBQQVVMTzESMBAGA1UEBwwJU0FPIFBBVUxPMQ8wDQYDVQQKDAZBQ0ZVU1AxDzANBgNVBAsMBkFDRlVTUDEPMA0GA1UEAwwGQUNGVVNQMB4XDTE5MDUxNjEyMjU1NFoXDTI0MDUxNDEyMjU1NFowgbIxCzAJBgNVBAYTAkJSMREwDwYDVQQIDAhBbWF6b25hczERMA8GA1UECgwIU0VGQVotU1AxGDAWBgNVBAsMD0FDIFNBVCBTRUZBWiBTUDEoMCYGA1UECwwfQXV0b3JpZGFkZSBkZSBSZWdpc3RybyBTRUZBWiBTUDE5MDcGA1UEAwwwRUxHSU4gSU5EVVNUUklBTCBEQSBBTUFaT05JQSBMVERBOjE0MjAwMTY2MDAwMTY2MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuKdsN2mi5OserALssgKyPBZBnt3TeytHObTV2xboIeZ8nQ3qDbzovkpxwvBLEKCI1+5sWkZUBmQAqDXTv4Zu/oVPmgVAjLOQszH9mEkyrhlT5tRxyptGKCN58nfx150rHPvov9ct9uizR4S5+nDvMSLNVFpcpbT2y+vnfmMPQzOec8SbKdSPy1dytHl+id9r4XD/s/fXc19URb9XXtMui+praC9vWasiJsFScnTJScIdLwqZsCAmDQeRFHX1e57vCLNxFNCfKzgCRd9VhCQE03kXrz+xo7nJGdXP2baABvh3mi6ifHeuqPXw5JBwjemS0KkRZ5PhE5Ndkih86ZAeIwIDAQABo4ICNDCCAjAwCQYDVR0TBAIwADAOBgNVHQ8BAf8EBAMCBeAwLAYJYIZIAYb4QgENBB8WHU9wZW5TU0wgR2VuZXJhdGVkIENlcnRpZmljYXRlMB0GA1UdDgQWBBRS6g1qRE9lsk/8dfDlVjhISI/1wTAfBgNVHSMEGDAWgBQVtOORhiQs6jNPBR4tL5O3SJfHeDATBgNVHSUEDDAKBggrBgEFBQcDAjBDBgNVHR8EPDA6MDigNqA0hjJodHRwOi8vYWNzYXQuZmF6ZW5kYS5zcC5nb3YuYnIvYWNzYXRzZWZhenNwY3JsLmNybDCBpwYIKwYBBQUHAQEEgZowgZcwNQYIKwYBBQUHMAGGKWh0dHA6Ly9vY3NwLXBpbG90LmltcHJlbnNhb2ZpY2lhbC5jb20uYnIvMF4GCCsGAQUFBzAChlJodHRwOi8vYWNzYXQtdGVzdGUuaW1wcmVuc2FvZmljaWFsLmNvbS5ici9yZXBvc2l0b3Jpby9jZXJ0aWZpY2Fkb3MvYWNzYXQtdGVzdGUucDdjMHsGA1UdIAR0MHIwcAYJKwYBBAGB7C0DMGMwYQYIKwYBBQUHAgEWVWh0dHA6Ly9hY3NhdC5pbXByZW5zYW9maWNpYWwuY29tLmJyL3JlcG9zaXRvcmlvL2RwYy9hY3NhdHNlZmF6c3AvZHBjX2Fjc2F0c2VmYXpzcC5wZGYwJAYDVR0RBB0wG6AZBgVgTAEDA6AQDA4xNDIwMDE2NjAwMDE2NjANBgkqhkiG9w0BAQ0FAAOCAQEArHy8y6T6ZMX6qzZaTiTRqIN6ZkjOknVCFWTBFfEO/kUc1wFBTG5oIx4SDeas9+kxZzUqjk6cSsysH8jpwRupqrJ38wZir1OgPRBuGAPz9lcah8IL4tUQkWzOIXqqGVxDJ8e91MjIMWextZuy212E8Dzn3NNMdbqyOynd7O0O5p6wPS5nuTeEsm3wcw0aLu0bbIy+Mb/nHIqFKaoZEZ8LSYn2TfmP+z9AhOC1vj3swxuRTKf1xLcNvVbq/r+SAwwBC/IGRpgeMAzELLPLPUHrBeSO+26YWwXdxaq29SmEo77KkUpUXPlt9hS2MPagCLsE6ZwDSmc8x1h3Hv+MW8zxyg==</X509Certificate>\n" +
                    "            </X509Data>\n" +
                    "        </KeyInfo>\n" +
                    "    </Signature>\n" +
                    "</CFe>", 0);

            if (retorno == 0) {
                System.out.println("Impressão do Danfe SAT realizada com sucesso!");
            } else {
                System.out.println("Erro ao imprimir o Danfe SAT. Código de erro: " + retorno);
            }

            ImpressoraDLL.INSTANCE.AvancaPapel(2);
            ImpressoraDLL.INSTANCE.Corte(2);
        } else {

            System.out.println("Erro: Conexão não está aberta.");
        }
    }


    public static void ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param) {


        if (conexaoAberta) {

            //String dadosXML = new String(Files.readAllBytes(Paths.get("C:/Users/kaio/Downloads/Material de apoio/Exemplo Java/CANC_SAT.xml")));
            assQRCode = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT("<CFeCanc>\n" +
                    "    <infCFe Id=\"CFe13180314200166000166599000108160001324252883\" chCanc=\"CFe13180314200166000166599000108160001316693175\" versao=\"0.07\">\n" +
                    "        <dEmi>20180305</dEmi>\n" +
                    "        <hEmi>142819</hEmi>\n" +
                    "        <ide>\n" +
                    "            <cUF>13</cUF>\n" +
                    "            <cNF>425288</cNF>\n" +
                    "            <mod>59</mod>\n" +
                    "            <nserieSAT>900010816</nserieSAT>\n" +
                    "            <nCFe>000132</nCFe>\n" +
                    "            <dEmi>20180305</dEmi>\n" +
                    "            <hEmi>142846</hEmi>\n" +
                    "            <cDV>3</cDV>\n" +
                    "            <CNPJ>16716114000172</CNPJ>\n" +
                    "            <signAC>SGR-SAT SISTEMA DE GESTAO E RETAGUARDA DO SAT</signAC>\n" +
                    "            <assinaturaQRCODE>Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==</assinaturaQRCODE>\n" +
                    "            <numeroCaixa>001</numeroCaixa>\n" +
                    "        </ide>\n" +
                    "        <emit>\n" +
                    "            <CNPJ>14200166000166</CNPJ>\n" +
                    "            <xNome>ELGIN INDUSTRIAL DA AMAZONIA LTDA</xNome>\n" +
                    "            <enderEmit>\n" +
                    "                <xLgr>AVENIDA ABIURANA</xLgr>\n" +
                    "                <nro>579</nro>\n" +
                    "                <xBairro>DIST INDUSTRIAL</xBairro>\n" +
                    "                <xMun>MANAUS</xMun>\n" +
                    "                <CEP>69075010</CEP>\n" +
                    "            </enderEmit>\n" +
                    "            <IE>111111111111</IE>\n" +
                    "            <IM>111111</IM>\n" +
                    "        </emit>\n" +
                    "        <dest>\n" +
                    "            <CPF>14808815893</CPF>\n" +
                    "        </dest>\n" +
                    "        <total>\n" +
                    "            <vCFe>3.00</vCFe>\n" +
                    "        </total>\n" +
                    "        <infAdic>\n" +
                    "            <obsFisco xCampo=\"xCampo1\">\n" +
                    "                <xTexto>xTexto1</xTexto>\n" +
                    "            </obsFisco>\n" +
                    "        </infAdic>\n" +
                    "    </infCFe>\n" +
                    "    <Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                    "        <SignedInfo>\n" +
                    "            <CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\n" +
                    "            <SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/>\n" +
                    "            <Reference URI=\"#CFe13180314200166000166599000108160001324252883\">\n" +
                    "                <Transforms>\n" +
                    "                    <Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\n" +
                    "                    <Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\n" +
                    "                </Transforms>\n" +
                    "                <DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
                    "                <DigestValue>pePcOYfIU+b59qGayJiJj492D9fTVhqbHEqFLDUi1Wc=</DigestValue>\n" +
                    "            </Reference>\n" +
                    "        </SignedInfo>\n" +
                    "        <SignatureValue>og35vHuErSOCB29ME4WRwdVPwps/mOUQJvk3nA4Oy//CVPIt0X/iGUZHMnJhQa4aS4c7dq5YUaE2yf8H9FY8xPkY9vDQW62ZzuM/6qSHeh9Ft09iP55T76h7iLY+QLl9FZL4WINmCikv/kzmCCi4+8miVwx1MnFiTNsgSMmzRnvAv1iVkhBogbAZES03iQIi7wZGzZDo7bFmWyXVdtNnjOke0WS0gTLhJbftpDT3gi0Muu8J+AfNjaziBMFQB3i1oN96EkpCKsT78o5Sb+uBux/bV3r79nrFk4MXzaFOgBoTqv1HF5RVNx2nWSoZrbpAV8zPB1icnAnfb4Qfh1oJdA==</SignatureValue>\n" +
                    "        <KeyInfo>\n" +
                    "            <X509Data>\n" +
                    "                <X509Certificate>MIIFzTCCBLWgAwIBAgICESswDQYJKoZIhvcNAQENBQAwaDELMAkGA1UEBhMCQlIxEjAQBgNVBAgMCVNBTyBQQVVMTzESMBAGA1UEBwwJU0FPIFBBVUxPMQ8wDQYDVQQKDAZBQ0ZVU1AxDzANBgNVBAsMBkFDRlVTUDEPMA0GA1UEAwwGQUNGVVNQMB4XDTE3MDEyNzEzMzMyMloXDTIyMDEyNjEzMzMyMlowgbIxCzAJBgNVBAYTAkJSMREwDwYDVQQIDAhBbWF6b25hczERMA8GA1UECgwIU0VGQVotU1AxGDAWBgNVBAsMD0FDIFNBVCBTRUZBWiBTUDEoMCYGA1UECwwfQXV0b3JpZGFkZSBkZSBSZWdpc3RybyBTRUZBWiBTUDE5MDcGA1UEAwwwRUxHSU4gSU5EVVNUUklBTCBEQSBBTUFaT05JQSBMVERBOjE0MjAwMTY2MDAwMTY2MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtyLG8URyX8fqjOQa+rj3Rl6Z6eIX/dndhNe0rw6inNAXt06HtXQslBqnReuSanN3ssgpV6oev0ikfXA7hhmpZM7qVigTJp3+h1K9vKUlPZ5ELT36yAokpxakIyYRy5ELjP4KwFrAjQUgB6xu5X/MOoUmBKRLIiwm3wh7kUA9jZArQGD4pRknuvFuQ99ot3y6u3lI7Oa2ZqJ1P2E7NBmfdswQL8VG51by0Weivugsv3xWAHvdXZmmOrmv2W5C2U2VnsTjA3p2zQVwitZBEh6JxqLE3KljXlokbhHb1m2moSbzRLCdAJHIq/6eWL8kl2OVWViECODGoYA0Qz0wSgk/vwIDAQABo4ICNDCCAjAwCQYDVR0TBAIwADAOBgNVHQ8BAf8EBAMCBeAwLAYJYIZIAYb4QgENBB8WHU9wZW5TU0wgR2VuZXJhdGVkIENlcnRpZmljYXRlMB0GA1UdDgQWBBTIeTKrUS19raxSgeeIHYSXclNYkDAfBgNVHSMEGDAWgBQVtOORhiQs6jNPBR4tL5O3SJfHeDATBgNVHSUEDDAKBggrBgEFBQcDAjBDBgNVHR8EPDA6MDigNqA0hjJodHRwOi8vYWNzYXQuZmF6ZW5kYS5zcC5nb3YuYnIvYWNzYXRzZWZhenNwY3JsLmNybDCBpwYIKwYBBQUHAQEEgZowgZcwNQYIKwYBBQUHMAGGKWh0dHA6Ly9vY3NwLXBpbG90LmltcHJlbnNhb2ZpY2lhbC5jb20uYnIvMF4GCCsGAQUFBzAChlJodHRwOi8vYWNzYXQtdGVzdGUuaW1wcmVuc2FvZmljaWFsLmNvbS5ici9yZXBvc2l0b3Jpby9jZXJ0aWZpY2Fkb3MvYWNzYXQtdGVzdGUucDdjMHsGA1UdIAR0MHIwcAYJKwYBBAGB7C0DMGMwYQYIKwYBBQUHAgEWVWh0dHA6Ly9hY3NhdC5pbXByZW5zYW9maWNpYWwuY29tLmJyL3JlcG9zaXRvcmlvL2RwYy9hY3NhdHNlZmF6c3AvZHBjX2Fjc2F0c2VmYXpzcC5wZGYwJAYDVR0RBB0wG6AZBgVgTAEDA6AQDA4xNDIwMDE2NjAwMDE2NjANBgkqhkiG9w0BAQ0FAAOCAQEAAhF7TLbDABp5MH0qTDWA73xEPt20Ohw28gnqdhUsQAII2gGSLt7D+0hvtr7X8K8gDS0hfEkv34sZ+YS9nuLQ7S1LbKGRUymphUZhAfOomYvGS56RIG3NMKnjLIxAPOHiuzauX1b/OwDRmHThgPVF4s+JZYt6tQLESEWtIjKadIr4ozUXI2AcWJZL1cKc/NI7Vx7l6Ji/66f8l4Qx704evTqN+PjzZbFNFvbdCeC3H3fKhVSj/75tmK2TBnqzdc6e1hrjwqQuxNCopUSV1EJSiW/LR+t3kfSoIuQCPhaiccJdAUMIqethyyfo0ie7oQSn9IfSms8aI4lh2BYNR1mf5w==</X509Certificate>\n" +
                    "            </X509Data>\n" +
                    "        </KeyInfo>\n" +
                    "    </Signature>\n" +
                    "</CFeCanc>", assQRCode, 0);


            if (retorno == 0) {
                System.out.println("Impressão do Cancelamento SAT realizada com sucesso!");
            } else {
                System.out.println("Erro ao imprimir o Cancelamento SAT. Código de erro: " + retorno);
            }


            ImpressoraDLL.INSTANCE.AvancaPapel(2);
            ImpressoraDLL.INSTANCE.Corte(2);
        } else {

            System.out.println("Erro: Conexão não está aberta.");
        }
    }


    public static void SinalSonoro() {
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.SinalSonoro(4, 5, 5);
            System.out.println("Sinal sonoro emitido com sucesso!");
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
    }

    public static void AbreGaveta() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 10);
            if (retorno == 0) {
                System.out.println("Gaveta aberta com sucesso!");
            } else {
                System.out.println("Erro ao abrir gaveta. Código: " + retorno);
            }
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
    }

    public static void AbreGavetaElgin() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreGavetaElgin(1, 50, 50);
            if (retorno == 0) {
                System.out.println("Gaveta Elgin aberta com sucesso!");
            } else {
                System.out.println("Erro ao abrir gaveta Elgin. Código: " + retorno);
            }
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
    }

    public static void AvancaPapel(int linhas) {
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.AvancaPapel(2);
            System.out.println("Papel avançado com sucesso!");
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
    }

    public static void main(String[] args) throws java.io.IOException {

        while (true) {
            exibirMenuOpcoes();

            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            switch (escolha) {
                case "1":
                    System.out.println("Configurando conexao 🔧");
                    configurarConexao();
                    break;

                case "2":
                    System.out.println("Abrindo conexao ");
                    abrirConexao();
                    break;

                case "3":
                    System.out.println("Impriminto Texto ....");
                    ImpressaoTexto();
                    break;

                case "4":
                    System.out.println("Impressao QRCode ....");
                    ImpressaoQRCode("Teste de impressao", 6, 4);
                    break;

                case "5":
                    System.out.println("Impressao Cod Barras .....");
                    ImpressaoCodigoBarras();
                    break;

                case "6":
                    System.out.println("Impressao XML SAT ....");
                    ImprimeXMLSAT("C:/Users/kaio/Downloads/Material de apoio/Exemplo Java/XMLSAT.xml", 2);
                    break;

                case "7":
                    System.out.println("Impressao XML Cancelamento SAT ....");
                    String xmlCancelamento = "path=C:/Users/kaio/Downloads/Material de apoio/Exemplo Java/CANC_SAT.xml";
                    String assinaturaQRCode = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";
                    int param = 0;

                    ImprimeXMLCancelamentoSAT(xmlCancelamento, assinaturaQRCode, param);
                    break;

                case "8":
                    System.out.println("Abrir Gaveta Elgin ....");
                    AbreGavetaElgin();
                    break;

                case "9":
                    System.out.println("Abrir Gaveta");
                    AbreGaveta();
                    break;

                case "10":
                    System.out.println("Sinal Sonoro");
                    SinalSonoro();
                    break;

                case "0":
                    System.out.println("Fechar Conexao e Sair");
                    fecharConexao();
                    System.exit(0);
                    break;

            }
        }

    }

}