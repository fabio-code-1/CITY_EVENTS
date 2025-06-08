# CityEvents

Sistema de Cadastro e Notificação de Eventos Locais

## 🎯 Objetivo

Projeto desenvolvido como atividade prática da unidade curricular de programação orientada a objetos. O sistema permite o cadastro de usuários e eventos, confirmação de participação, visualização e ordenação por data, com persistência em arquivo.

## 🛠️ Tecnologias

- Java 17+
- IDE: VSCODE
- Armazenamento: arquivo texto `events.data`, `usuarios.data`

## 📚 Funcionalidades

- Login/Logout ✅
- Cadastro de usuários ✅
- Verificação de cadastro _nome_ e _senha_ existente ("Teste" ≠ "teste": diferentes) ✅
- Excluir conta ✅
- Cadastro de eventos (nome, endereço, categoria, data, horário, descrição, criador) ✅
- Verificação de cadastro de evento _nome_, _endereço_ e _data_ existente ("Teste" ≠ "teste": diferentes) ✅
- Listagem de eventos disponíveis ✅
- Listagem de eventos que o usuário está participando ✅
- Confirmação e cancelamento de participação ✅
- Identificação de eventos ocorrendo no momento ou já passados ✅
- Ordenação de eventos por data e hora ✅
- Persistência em arquivo ✅
- Carregamento automático de dados salvos ao iniciar o sistema ✅

## 🧱 Estrutura de Classes

- `Usuario`
- `Evento`
- `EventoService`
- `UsuarioService`
- `Main` (menu de console)
- `Login` (menu de console)

## 💾 Persistência

Os usuários são salvos em um arquivo chamado `usuarios.data`, que será carregado automaticamente ao iniciar o programa.
Os eventos são salvos em um arquivo chamado `events.data`, que será carregado automaticamente ao iniciar o programa.

## 🚀 Execução

Compile e execute o projeto a partir da classe `Main`. Interaja pelo menu em console.
