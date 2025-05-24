# CityEvents

Sistema de Cadastro e Notificação de Eventos Locais

## 🎯 Objetivo

Projeto desenvolvido como atividade prática da unidade curricular de programação orientada a objetos. O sistema permite o cadastro de usuários e eventos, confirmação de participação, visualização e ordenação por data, com persistência em arquivo.

## 🛠️ Tecnologias

- Java 17+
- IDE: VSCODE
- Armazenamento: arquivo texto `events.data`

## 📚 Funcionalidades

- Login/Logout
- Cadastro de usuários ✅
- Verificação de cadastro já existente
- Excluir conta
- Cadastro de eventos (com nome, endereço, categoria, horário e descrição)
- Listagem de eventos disponíveis
- Confirmação e cancelamento de participação
- Identificação de eventos ocorrendo no momento ou já passados
- Ordenação de eventos por data e hora
- Persistência em arquivo
- Carregamento automático de dados salvos ao iniciar o sistema

## 🧱 Estrutura de Classes (em desenvolvimento)

- `Usuario`
- `Evento`
- `EventoService`
- `UsuarioService`
- `Main` (menu de console)

## 💾 Persistência

Os eventos são salvos em um arquivo chamado `events.data`, que será carregado automaticamente ao iniciar o programa.

## 📊 Diagrama de Classes

_(A ser incluído — pode ser feito via draw.io ou PlantUML)_

## 🚀 Execução

Compile e execute o projeto a partir da classe `Main`. Interaja pelo menu em console.
