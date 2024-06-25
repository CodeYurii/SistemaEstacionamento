# Sistema de Estacionamento - Aplicativo Java
Este é um sistema de estacionamento desenvolvido em Java, que permite registrar a entrada e saída de veículos, calcular o tempo de estadia e processar pagamentos. A interface gráfica facilita a interação com o usuário para operações básicas de gerenciamento de estacionamento, incluindo reconhecimento de placa por imagem usando o Tesseract OCR.

## Funcionalidades

- **Registrar Entrada de Veículo:** Adicione novos veículos ao sistema inserindo informações como placa, marca, modelo, cor e observações adicionais.
  
- **Registrar Saída de Veículo:** Registre a saída de veículos previamente cadastrados, calculando automaticamente o tempo de estadia e exibindo o valor a ser pago.

- **Atualizar Pátio:** Mantenha a lista de veículos ativos atualizada no sistema.

- **Alterar Entrada de Veículo:** Atualize informações de veículos já registrados, como marca, modelo e cor.

- **Histórico de Movimentação:** Consulte o histórico completo de entradas e saídas de veículos para referência.

- **Pagamento:** Realize pagamentos de estadias utilizando opções como dinheiro, Pix e cartão de crédito. O pagamento requer a placa do veículo para identificação.

- **Reconhecimento de Placa por Imagem:** Utilize o Tesseract OCR para capturar automaticamente a placa de veículos a partir de uma imagem, agilizando o registro de entrada.

## Como Utilizar

1. **Executar o Aplicativo:**
   - Certifique-se de ter o ambiente Java configurado.
   - Execute o aplicativo Java.

2. **Interagir com a Interface Gráfica:**
   - Ao iniciar o aplicativo, você verá uma interface amigável com botões para cada funcionalidade.
   - Utilize os botões para realizar as operações desejadas, como registrar a entrada de um veículo, registrar sua saída, atualizar a lista de veículos, entre outros.

3. **Registrar Entrada de Veículo:**
   - Preencha os campos obrigatórios (placa, marca, modelo e cor).
   - Clique no botão "Registrar Entrada" para adicionar o veículo ao estacionamento.

4. **Registrar Saída de Veículo:**
   - Informe a placa do veículo que está saindo.
   - Clique no botão "Registrar Saída" para calcular o tempo de estadia e exibir o valor a ser pago.

5. **Atualizar Pátio:**
   - Clique no botão "Atualizar Pátio" para obter a lista atualizada de veículos no estacionamento.

6. **Alterar Entrada de Veículo:**
   - Informe a placa do veículo que deseja atualizar.
   - Faça as alterações necessárias nos campos de marca, modelo ou cor.
   - Clique no botão "Alterar Entrada" para confirmar as alterações.

7. **Histórico de Movimentação:**
   - Clique no botão "Histórico" para visualizar o registro completo de entradas e saídas de veículos.

8. **Pagamento:**
   - Escolha a forma de pagamento desejada (dinheiro, Pix ou cartão).
   - Informe a placa do veículo para identificação.
   - Confirme o pagamento clicando no botão correspondente.

9. **Reconhecimento de Placa por Imagem:**
   - Certifique-se de ter o Tesseract OCR instalado e configurado corretamente.
   - Utilize a opção "Captura Automática" para capturar a placa de um veículo a partir de uma imagem.


### Tela Captura Automática
![Captura Automática](https://github.com/CodeYurii/SistemaEstacionamento/blob/main/SistemaEstacionamento/src/main/resources/Imagens/CapturaAutomatica.PNG)

### Tela Pátio
![Pátio](https://github.com/CodeYurii/SistemaEstacionamento/blob/main/SistemaEstacionamento/src/main/resources/Imagens/ENTRADA.PNG)

### Tela Histórico
![Histórico](https://github.com/CodeYurii/SistemaEstacionamento/blob/main/SistemaEstacionamento/src/main/resources/Imagens/HISTORICO.PNG)

### Tela Recibo
![Recibo](https://github.com/CodeYurii/SistemaEstacionamento/blob/main/SistemaEstacionamento/src/main/resources/Imagens/RECIBO.PNG)

## Observações

- Certifique-se de preencher corretamente todas as informações solicitadas para evitar erros de validação.
- Para utilizar o reconhecimento de placa por imagem, é necessário configurar o caminho da imagem no código-fonte da aplicação.

Este aplicativo de estacionamento foi desenvolvido como parte de um projeto educacional e demonstra a integração de funcionalidades básicas de gerenciamento de estacionamento em uma interface gráfica Java.
