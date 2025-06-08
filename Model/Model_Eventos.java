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

    System.out.print("Qual o endereco do evento: ");
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

    System.out.print("Descricao do evento: ");
    this.descricao = scanner.nextLine();

    this.criador = criador;
  }

  public void salvarEmArquivo() {
    try {
      // 1. Verifica se o evento já existe
      BufferedReader reader = new BufferedReader(new FileReader("DB/events.data"));
      String linha;
      String participantes = "[" + criador + "]";
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
        return; // nao salva
      }

      // 2. Salva se nao existir
      FileWriter writer = new FileWriter("DB/events.data", true);
      writer.write(nome + "|" + endereco + "|" + categoria + "|" + data + "|" + horario + "|" + descricao + "|"
          + criador + "|" + participantes + "\n");
      writer.close();

      System.out.println("Evento salvo com sucesso!\n");

    } catch (IOException e) {
      System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
    }
  }

  public static void listarEventos() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    LocalDateTime agora = LocalDateTime.now();

    File arquivo = new File("DB/events.data");

    if (!arquivo.exists() || arquivo.length() == 0) {
      System.out.println("Nenhum evento cadastrado.");
      return;
    }

    // Cria uma lista que vai guardar todas as linhas do arquivo já separadas ("|")
    // Cada item da lista representa um evento completo, onde as informacoes estao
    // em um vetor de Strings.
    List<String[]> eventos = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
      String linha;

      while ((linha = reader.readLine()) != null) {
        String[] partes = linha.split("\\|");
        if (partes.length >= 8) {
          eventos.add(partes);
        }
      }

      // Ordenar por data e hora (convertendo para LocalDateTime)
      eventos.sort((a, b) -> {
        try {
          LocalDateTime dataHoraA = LocalDateTime.parse(a[3] + " " + a[4], formatter);
          LocalDateTime dataHoraB = LocalDateTime.parse(b[3] + " " + b[4], formatter);
          return dataHoraA.compareTo(dataHoraB);
        } catch (Exception e) {
          return 0; // Se der erro, nao muda a ordem
        }
      });

      for (String[] e : eventos) {
        LocalDateTime dataHora = LocalDateTime.parse(e[3] + " " + e[4], formatter);

        System.out.println("Criado por: " + e[6]);
        System.out.println("Nome: " + e[0]);
        System.out.println("Endereco: " + e[1]);
        System.out.println("Categoria: " + e[2]);
        System.out.println("Data: " + e[3]);
        System.out.println("Horário: " + e[4]);
        System.out.println("Descricao: " + e[5]);

        // Verificacao do status
        if (dataHora.isBefore(agora)) {
          if (dataHora.toLocalDate().equals(agora.toLocalDate())) {
            System.out.println("Status: Evento HOJE (mas já passou o horário)");
          } else {
            System.out.println("Status: Evento já passou");
          }
        } else if (dataHora.toLocalDate().equals(agora.toLocalDate())) {
          if (dataHora.getHour() == agora.getHour()) {
            System.out.println("Status: Evento acontecendo agora");
          } else {
            System.out.println("Status: Evento HOJE (mais tarde)");
          }
        } else {
          System.out.println("Status: Evento futuro");
        }

        System.out.println("Participantes: " + e[7]);

        System.out.println("-----------------------------");
      }

    } catch (IOException e) {
      System.out.println("Erro ao ler eventos: " + e.getMessage());
    }
  }

  public static void confirmarPresenca(Scanner scanner, String nomeUsuario) {
    System.out.print("Digite exatamente o NOME do evento que deseja confirmar presenca: ");
    String nomeEvento = scanner.nextLine();

    File arquivo = new File("DB/events.data");
    File temp = new File("DB/events_temp.data");

    boolean encontrou = false;
    boolean jaConfirmado = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        FileWriter writer = new FileWriter(temp)) {

      String linha;
      while ((linha = reader.readLine()) != null) {
        String[] partes = linha.split("\\|");

        if (partes.length >= 8 && partes[0].equalsIgnoreCase(nomeEvento)) {
          encontrou = true;

          String participantes = partes[7]; // Ex: [Míria,Luanna]

          // Garante que tem colchetes
          if (!participantes.startsWith("["))
            participantes = "[" + participantes;
          if (!participantes.endsWith("]"))
            participantes = participantes + "]";

          String nomesSemColchetes = participantes.replace("[", "").replace("]", "");
          String[] nomes = nomesSemColchetes.split(",");

          for (String nome : nomes) {
            if (nome.trim().equalsIgnoreCase(nomeUsuario)) {
              jaConfirmado = true;
              break;
            }
          }

          if (!jaConfirmado) {
            participantes = participantes.replace("]", "," + nomeUsuario + "]");
            partes[7] = participantes;
            linha = String.join("|", partes);
            System.out.println("Presenca confirmada com sucesso!");
          } else {
            System.out.println("Voce ja confirmou presenca nesse evento.");
          }
        }

        writer.write(linha + "\n");
      }

    } catch (IOException e) {
      System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
      return;
    }

    // Finaliza substituindo o arquivo original
    if (arquivo.delete()) {
      if (!temp.renameTo(arquivo)) {
        System.out.println("Erro ao salvar alteracoes no arquivo.");
      }
    } else {
      System.out.println("Erro ao apagar o arquivo antigo.");
    }

    if (!encontrou) {
      System.out.println("Evento nao encontrado.");
    }
  }

  public static void cancelarPresenca(Scanner scanner, String nomeUsuario) {
    System.out.print("Digite exatamente o NOME do evento que deseja cancelar a presenca: ");
    String nomeEvento = scanner.nextLine();

    File arquivo = new File("DB/events.data");
    File temp = new File("DB/events_temp.data");

    boolean encontrou = false;
    boolean participava = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        FileWriter writer = new FileWriter(temp)) {

      String linha;
      while ((linha = reader.readLine()) != null) {
        String[] partes = linha.split("\\|");

        if (partes.length >= 8 && partes[0].equalsIgnoreCase(nomeEvento)) {
          encontrou = true;

          String participantes = partes[7]; // Ex: [Míria,Luanna]

          if (!participantes.startsWith("["))
            participantes = "[" + participantes;
          if (!participantes.endsWith("]"))
            participantes = participantes + "]";

          String nomesSemColchetes = participantes.replace("[", "").replace("]", "");
          String[] nomes = nomesSemColchetes.split(",");

          List<String> novaLista = new ArrayList<>();

          for (String nome : nomes) {
            if (!nome.trim().equalsIgnoreCase(nomeUsuario)) {
              novaLista.add(nome.trim());
            } else {
              participava = true;
            }
          }

          // Atualiza participantes
          String novaString = "[" + String.join(",", novaLista) + "]";
          partes[7] = novaString;
          linha = String.join("|", partes);

          if (participava) {
            System.out.println("Presenca cancelada com sucesso.");
          } else {
            System.out.println("Voce nao estava confirmado nesse evento.");
          }
        }

        writer.write(linha + "\n");
      }

    } catch (IOException e) {
      System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
      return;
    }

    if (arquivo.delete()) {
      if (!temp.renameTo(arquivo)) {
        System.out.println("Erro ao salvar alteracoes no arquivo.");
      }
    } else {
      System.out.println("Erro ao apagar o arquivo antigo.");
    }

    if (!encontrou) {
      System.out.println("Evento nao encontrado.");
    }
  }

}
