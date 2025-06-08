package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Model_Eventos {
  private String nome;
  private String endereco;
  private String categoria;
  private String data;
  private String horario;
  private String descricao;
  private String criador;

  public Model_Eventos(Scanner scanner, String criador) {
    System.out.print("Digite o nome do evento: ");
    this.nome = scanner.nextLine();

    System.out.print("Qual o endereço do evento: ");
    this.endereco = scanner.nextLine();

    System.out.print("Qual a categoria do evento (ex: palestra, workshop, curso): ");
    this.categoria = scanner.nextLine();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    while (true) {
      System.out.print("Data do evento (ex: 25/06/2025): ");
      String entradaData = scanner.nextLine();

      try {
        LocalDate dataValida = LocalDate.parse(entradaData, formatter);
        this.data = dataValida.format(formatter); // armazena como String formatada
        break;
      } catch (DateTimeParseException e) {
        System.out.println("Formato inválido. Digite a data no formato dd/MM/yyyy.");
      }
    }

    DateTimeFormatter horarioFormatter = DateTimeFormatter.ofPattern("HH:mm");
    while (true) {
      System.out.print("Horário de início do evento (ex: 14:30): ");
      String entradaHorario = scanner.nextLine();

      try {
        LocalTime horarioValido = LocalTime.parse(entradaHorario, horarioFormatter);
        this.horario = horarioValido.format(horarioFormatter); // armazena como String formatada
        break;
      } catch (DateTimeParseException e) {
        System.out.println("Formato inválido. Digite o horário no formato HH:mm.");
      }
    }

    System.out.print("Descrição do evento: ");
    this.descricao = scanner.nextLine();

    this.criador = criador;
  }

  public void salvarEmArquivo() {
    try {
      // 1. Verifica se o evento já existe
      BufferedReader reader = new BufferedReader(new FileReader("DB/events.data"));
      String linha;
      boolean existe = false;

      // /// Verifica se o nome do evento já existe no arquivo
      while ((linha = reader.readLine()) != null) {
        String[] partes = linha.split("\\|"); // separa nome e senha
        if (partes[0].equals(nome) && partes[1].equals(endereco) && partes[3].equals(data)) {
          existe = true;
          break; // já achou, pode parar
        }
      }

      reader.close();

      if (existe) {
        System.out.println("Erro: Evento '" + nome + "' ja existe!\n");
        return; // não salva
      }

      // 2. Salva se não existir
      FileWriter writer = new FileWriter("DB/events.data", true);
      writer.write(nome + "|" + endereco + "|" + categoria + "|" + data + "|" + horario + "|" + descricao + "|"
          + criador + "\n");
      writer.close();

      System.out.println("Evento salvo com sucesso!\n");

    } catch (IOException e) {
      System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
    }
  }

  public static void listarEventos() {
    File arquivo = new File("DB/events.data");

    if (!arquivo.exists() || arquivo.length() == 0) {
      System.out.println("Nenhum evento cadastrado.");
      return;
    }

    // Cria uma lista que vai guardar todas as linhas do arquivo já separadas (por
    // "|").
    // Cada item da lista representa um evento completo, onde as informações estão
    // em um vetor de Strings.
    List<String[]> eventos = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
      String linha;

      while ((linha = reader.readLine()) != null) {
        String[] partes = linha.split("\\|");
        if (partes.length >= 7) {
          eventos.add(partes);
        }
      }

      // Ordenar por data e hora (convertendo para LocalDateTime)
      eventos.sort((a, b) -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
          LocalDateTime dataHoraA = LocalDateTime.parse(a[3] + " " + a[4], formatter);
          LocalDateTime dataHoraB = LocalDateTime.parse(b[3] + " " + b[4], formatter);
          return dataHoraA.compareTo(dataHoraB);
        } catch (Exception e) {
          return 0; // Se der erro, não muda a ordem
        }
      });

      int i = 1;
      for (String[] e : eventos) {
        System.out.println("Criado por: " + e[6]);
        System.out.println("Evento " + i++);
        System.out.println("Nome: " + e[0]);
        System.out.println("Endereço: " + e[1]);
        System.out.println("Categoria: " + e[2]);
        System.out.println("Data: " + e[3]);
        System.out.println("Horário: " + e[4]);
        System.out.println("Descrição: " + e[5]);
        System.out.println("-----------------------------");
      }

    } catch (IOException e) {
      System.out.println("Erro ao ler eventos: " + e.getMessage());
    }
  }

}
