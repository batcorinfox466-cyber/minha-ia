# SolanaForge (Versão Fabric 1.21.11)

O **SolanaForge** é um mod para Minecraft projetado originalmente para o Forge e totalmente portado e reestruturado para o **Fabric (1.21.11)**. Este mod adiciona diversas mecânicas essenciais para servidores de sobrevivência focados em economia com criptomoeda e utilidades de transporte.

## 🌟 Funcionalidades Principais

- **Integração com a Solana (Web3)**:
  - Sistema completo de carteiras integradas ao banco de dados MySQL (`/criarcarteira` / `/createwallet`).
  - Consulta de saldo em SOL diretamente dentro do jogo.
- **Loja de Relíquias (Store)**:
  - Possibilidade de comprar melhorias (asas, pernas) gastando saldo in-game.
  - Relíquias conferem efeitos de status ou utilidades exclusivas aos jogadores.
- **Sistema de Homes e Teleportes (TPA)**:
  - Defina sua casa (salva no banco de dados) e teleporte-se para ela com facilidade.
  - Solicitações de teleporte para outros jogadores usando `/tpa`, `/tpaccept` e `/tpdeny`.
  - Salvamento automático de local de morte para retorno seguro.
- **Segurança de Baús**:
  - Tranque seus baús com chaves personalizadas e previna que outros jogadores roubem seus itens.
- **Banco de Dados Seguro**:
  - Salva jogadores, saldos, locais de sethome, e permissões diretamente em um banco MySQL, compatível com a sincronização do servidor web.

## ⚙️ Tecnologias Utilizadas
- **Java 21**
- **Fabric API 1.21.11** (Migrado do Forge)
- **Fabric Loom 1.7+**
- **MySQL Connector/J**

## 🚀 Como Compilar e Rodar o Projeto

1. Certifique-se de ter o **Java 17** instalado em sua máquina.
2. Clone o repositório em seu ambiente local:
   ```bash
   git clone https://github.com/batcorinfox466-cyber/minha-ia.git
   ```
3. Na raiz do projeto, execute o comando de build via Gradle:
   ```bash
   ./gradlew build
   ```
4. O arquivo `.jar` compilado será gerado dentro da pasta `build/libs/`. 
5. Basta colocar o `.jar` gerado na pasta `mods` da sua instância Fabric 1.21.11, junto com a dependência **Fabric API**.

## 🛠 Configuração do Banco de Dados

O mod necessita de um banco de dados MySQL rodando.
Na primeira execução do mod (ou do servidor), um arquivo chamado `solanaforge.json` será gerado dentro da sua pasta `config/`. 
Abra-o e configure os dados do seu MySQL:

```json
{
  "DB_URL": "jdbc:mysql://localhost:3306/solana",
  "DB_USER": "root",
  "DB_PASSWORD": "sua_senha",
  "DB_USE_SSL": false,
  "DB_VERIFY_CERT": false
}
```

Feito isso, ao iniciar novamente, o mod vai criar as tabelas necessárias de forma automática.

## 👥 Contribuição
Fique à vontade para mandar *Pull Requests* ou abrir *Issues* caso encontre problemas!

## 🙏 Agradecimentos Especiais

Um enorme agradecimento ao(à) **Anabel369** por ter criado a versão original do mod em Forge, servindo como base crucial para que esta nova e aprimorada versão em Fabric ganhasse vida.
Agradecimento especial também ao **Venom** (que atende pelo nome de **Sonic** por aqui!), grande parceiro e amigo que acompanha esse projeto de perto. Muito obrigado a todos!
