
# Medical Clinic

Este projeto é uma aplicação completa para agendamentos de consultas médicas, desenvolvida com foco em escalabilidade, modularidade e boas práticas de desenvolvimento. A arquitetura do sistema é baseada em microserviços, com backend desenvolvido em Java utilizando o Spring Framework e frontend em React para uma experiência de usuário dinâmica e responsiva.


## Tecnologias utilizadas

**Java + Spring:** Utilização de Spring Boot para desenvolvimento rápido e eficiente.

**Microserviços:**
Agendamentos: Gerencia a lógica de agendamentos de consultas médicas.

**Envio de E-mails:** Responsável por enviar notificações e confirmações por e-mail.

**Mensageria com Kafka:** Comunicação assíncrona e desacoplada entre os microserviços.

**Autenticação e Autorização:** Implementação de segurança com Spring Security e JWT para controle de acesso.

**Testes Unitários:** Garantia de qualidade do código com cobertura de testes utilizando JUNIT5 e Mockito.

**Docker:** Conteinerização dos serviços backend para facilitar a implantação e escalabilidade.

**React:** Biblioteca JavaScript para criação de interfaces de usuário.


## Rodando localmente

Certifique-se de possuir o **Docker** instalado em sua máquina, você pode baixá-lo [aqui](https://www.docker.com/).

Com Docker instalado, clone o repositório do projeto localmente:

```bash
  git clone https://github.com/matheusosan/medical_clinic_backend
```

Entre no diretório do projeto:

```bash
  cd medical_clinic_backend
```

Rode o Docker para que ele busque as imagens necessárias e suba o ambiente:

```bash
  docker-compose up --build
```

Pronto! Você já terá o Microsserviço de agendamentos rodando em http://localhost:8080

