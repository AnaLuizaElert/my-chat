# 💬 Chat de Mensageria Multi-thread via Sockets (Java)

Aplicação de mensageria instantânea cliente-servidor desenvolvida em Java para a disciplina de Redes / Programação Distribuída. O sistema permite a troca de mensagens de texto privadas e a transferência de arquivos entre clientes conectados, com controle de conexões via logs e execução concorrente por meio de threads.

---

## 🚀 Funcionalidades

- **Mensagens Privadas:** Envio de texto direto entre clientes (P2P intermediado pelo servidor).
- **Envio de Arquivos:** Transferência de arquivos binários com salvamento automático no diretório local do destinatário.
- **Listagem de Usuários:** Exibição em tempo real dos clientes conectados no momento.
- **Log de Conexões:** Registro automático no servidor com endereço IP, data e hora de cada novo acesso.
- **Comunicação Multithread:** Suporte a múltiplos clientes concorrentes através do uso de `ServerSocket` e `ConcurrentHashMap`.

---

## 🛠️ Comandos do Cliente

| Comando | Descrição | Exemplo |
| :--- | :--- | :--- |
| `/send message <destinatario> <mensagem>` | Envia uma mensagem de texto privada | `/send message bob Olá, tudo bem?` |
| `/send file <destinatario> <caminho_arquivo>` | Envia um arquivo para o destinatário | `/send file alice ./docs/aula.pdf` |
| `/users` | Lista todos os usuários conectados | `/users` |
| `/sair` | Desconecta do servidor e encerra o programa | `/sair` |

---

## 📐 Estrutura do Projeto

```text
src/
├── client/
│   └── ChatClient.java        # Aplicação cliente (interface via terminal e I/O)
├── server/
│   ├── ChatServer.java        # Main do servidor, escuta portas e aceita conexões
│   ├── ClientHandler.java     # Runnable que gerencia a comunicação de cada cliente
│   ├── Router.java            # Gerencia a tabela de usuários e o roteamento
│   └── ConnectionLogger.java  # Escrita concorrente dos logs de acesso em disco
└── shared/
    ├── MessagePackage.java    # DTO serializável que trafega na rede
    └── MessageType.java       # Enum para classificação das mensagens/comandos