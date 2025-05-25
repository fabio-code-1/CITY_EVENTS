package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    System.out.print("Data do evento (ex: 25/06/2025): ");
    this.data = scanner.nextLine();

    System.out.print("Horário de início do evento (ex: 14:30): ");
    this.horario = scanner.nextLine();

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

      System.out.println("Usuario salvo com sucesso!\n");

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

    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
      String linha;
      int contador = 1;

      while ((linha = reader.readLine()) != null) {
        String[] partes = linha.split("\\|");
        if (partes.length >= 6) {
          System.out.println("Evento " + contador++);
          System.out.println("Nome: " + partes[0]);
          System.out.println("Endereço: " + partes[1]);
          System.out.println("Categoria: " + partes[2]);
          System.out.println("Data: " + partes[3]);
          System.out.println("Horário: " + partes[4]);
          System.out.println("Descrição: " + partes[5]);
          System.out.println("Criado por: " + partes[6]);
          System.out.println("-----------------------------");
        }
      }

    } catch (IOException e) {
      System.out.println("Erro ao ler eventos: " + e.getMessage());
    }
  }

}
