# Data Integrator

**Data Integrator é uma plataforma de integração e automação de fluxos de trabalho projetada para ser flexível, segura e escalável. Conecte sistemas, processe dados e automatize tarefas complexas com o poder do Python em um ambiente totalmente gerenciado e containerizado.**

---

## Tabela de Conteúdos

1.  [Missão](https://www.google.com/search?q=%23miss%C3%A3o)
2.  [Visão Geral do Projeto](https://www.google.com/search?q=%23vis%C3%A3o-geral-do-projeto)
3.  [Principais Funcionalidades](https://www.google.com/search?q=%23principais-funcionalidades)
4.  [Arquitetura e Infraestrutura](https://www.google.com/search?q=%23arquitetura-e-infraestrutura)
    - [Diagrama de Alto Nível](https://www.google.com/search?q=%23diagrama-de-alto-n%C3%ADvel)
    - [Stack Tecnológica](https://www.google.com/search?q=%23stack-tecnol%C3%B3gica)
5.  [Fluxo de Trabalho Típico](https://www.google.com/search?q=%23fluxo-de-trabalho-t%C3%ADpico)
6.  [Considerações de Segurança](https://www.google.com/search?q=%23considera%C3%A7%C3%B5es-de-seguran%C3%A7a)
7.  [Começando](https://www.google.com/search?q=%23come%C3%A7ando)
8.  [Roadmap](https://www.google.com/search?q=%23roadmap)

## Missão

Nossa missão é **democratizar a criação de integrações e a automação de fluxos de trabalho**. Queremos capacitar desenvolvedores e analistas a conectar sistemas e transformar dados de forma ágil e visual, abstraindo a complexidade da infraestrutura subjacente e garantindo uma execução segura e escalável.

## Visão Geral do Projeto

Em muitos ambientes corporativos, a tarefa de extrair dados de um sistema (como um diretório de rede), transformá-los e carregá-los em outro (via API, por exemplo) é um processo manual, propenso a erros e que consome tempo.

O Data Integrator visa resolver esse problema fornecendo uma plataforma centralizada onde um usuário pode:

1.  Configurar uma **fonte de dados** (ex: um diretório de rede em um servidor).
2.  Criar uma **tarefa** para ser executada sob demanda ou de forma agendada.
3.  Escrever um **script Python** para definir a lógica de transformação e processamento dos dados.
4.  Configurar um **integrador** para enviar o resultado do processamento para qualquer API externa (JSON ou XML).

Tudo isso acontece em um ambiente seguro, onde cada execução de tarefa cria um contêiner sob demanda, garantindo isolamento total e uso eficiente de recursos.

## Principais Funcionalidades

- **Cadastro de Máquinas/Fontes de Dados:** Gerencie as fontes de dados e suas credenciais de acesso de forma centralizada e segura.
- **Editor de Scripts Python Integrado:** Escreva, teste e versione seus scripts de transformação diretamente na plataforma com um editor de código moderno.
- **Execução Segura e Sob Demanda:** Cada tarefa é executada em seu próprio contêiner efêmero, garantindo que não haja interferência entre execuções e que o ambiente seja sempre limpo.
- **Agendamento Flexível:** Configure tarefas para rodar em intervalos específicos (usando expressões cron), em horários fixos ou dispare-as manualmente.
- **Integrador de API Configurável:** Envie os dados processados para qualquer endpoint REST, com suporte para templates JSON e XML, headers customizados e métodos HTTP.
- **Dashboard e Logging:** Monitore o status de todas as execuções em tempo real e acesse logs detalhados para depuração e auditoria.

## Arquitetura e Infraestrutura

A plataforma é construída sobre uma arquitetura de microsserviços moderna e containerizada, projetada para ser executada em um ambiente como o Kubernetes.

### Diagrama de Alto Nível

_EM CONSTRUCAO_

**Componentes:**

1.  **Frontend:** Uma Single-Page Application (SPA) construída com **Next.js**, fornecendo a interface de usuário para gerenciamento da plataforma.
2.  **Backend (Orquestrador):** Uma aplicação **Spring Boot** que serve a API principal, gerencia a lógica de negócio, e o mais importante, atua como um orquestrador que se comunica com a API do Kubernetes para criar contêineres de tarefas.
3.  **Banco de Dados:** Uma instância do **PostgreSQL** para persistir todas as configurações, scripts, usuários e logs de execução.
4.  **Camada de Execução (Workers):** Contêineres **Docker** efêmeros, gerenciados como `Jobs` do **Kubernetes**. Cada `Job` contém o ambiente Python necessário e o script do usuário para executar uma única tarefa.
5.  **Conectividade:** A conexão entre a infraestrutura do Data Integrator e os diretórios de rede dos clientes é estabelecida através de métodos seguros como **VPNs Site-to-Site** ou **SFTP**, garantindo que os dados trafeguem de forma criptografada e segura.

### Stack Tecnológica

- **Frontend:** Next.js, React, TypeScript
- **Backend:** Spring Boot, Java, Spring Web, Spring Data JPA, Spring Security
- **Linguagem de Scripting:** Python 3
- **Infraestrutura:** Docker, Kubernetes (K8s)
- **Banco de Dados:** PostgreSQL
- **CI/CD:** GitHub Actions (ou similar)

## Fluxo de Trabalho Típico

1.  **Configuração:** O usuário cadastra uma "Máquina" no Data Integrator, fornecendo detalhes de como se conectar à sua fonte de dados.
2.  **Criação da Tarefa:** O usuário cria uma nova tarefa, associa-a à máquina, define um agendamento e escreve o script Python que fará a leitura e transformação.
3.  **Criação do Integrador:** O usuário configura o destino, definindo a URL da API, o método e o formato (JSON/XML) do corpo da requisição.
4.  **Execução:** Quando a tarefa é disparada, o backend do Data Integrator cria um `Job` no Kubernetes.
5.  **Processamento:** O Kubernetes aloca um novo Pod. Dentro dele, o script Python é executado, acessando o diretório de rede do cliente, processando os arquivos e gerando um resultado.
6.  **Entrega:** O backend recebe o resultado do Job, formata-o conforme a configuração do integrador e faz a chamada para a API externa.
7.  **Log:** O resultado da operação (sucesso ou falha) e os logs de execução são salvos e exibidos no dashboard.

## Considerações de Segurança

A segurança é um pilar fundamental deste projeto, dado que ele executa código fornecido pelo usuário.

- **Execução Remota de Código (RCE) por Design:** Lidamos com este risco inerente através de múltiplos níveis de isolamento.
- **Isolamento com Kubernetes Jobs:** Cada tarefa roda em seu próprio contêiner com um filesystem isolado. O contêiner é destruído após a execução.
- **Controle de Acesso com RBAC:** O pod do orquestrador tem permissões mínimas no cluster Kubernetes, podendo apenas gerenciar `Jobs` em um namespace específico.
- **Limites de Recursos:** Cada `Job` tem limites estritos de CPU e memória para prevenir abuso de recursos e ataques de negação de serviço (DoS).

## Começando

_(Esta seção será preenchida com instruções detalhadas de como configurar o ambiente de desenvolvimento e fazer o deploy da aplicação.)_

### Pré-requisitos

- Java 17+
- Node.js 18+
- Docker
- Minikube ou um cluster Kubernetes

### Instalação

```bash
# Clone o repositório
git clone https://github.com/honokai/data-integrator.git
```

## Roadmap

Temos grandes planos para o Data Integrator. Nosso roadmap futuro inclui:

- [ ] **Construtor Visual de Fluxos (Drag-and-Drop):** Permitir a criação de fluxos de trabalho sem código.
- [ ] **Mais Tipos de Gatilhos:** Suporte a gatilhos por Webhooks, eventos em filas (RabbitMQ/Kafka) e upload de arquivos.
- [ ] **Biblioteca de Scripts:** Um local para usuários compartilharem e reutilizarem scripts de transformação comuns.
- [ ] **Suporte a Múltiplos Steps:** Criação de tarefas com múltiplos passos de processamento.
- [ ] **Dashboard de Análise Avançada:** Métricas de performance e uso de recursos.
