package Model;

import java.io.BufferedReader;
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

  public Model_Eventos(Scanner scanner) {
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
      writer.write(nome + "|" + endereco + "|" + categoria + "|" + data + "|" + horario + "|" + descricao + "\n");
      writer.close();

      System.out.println("Usuario salvo com sucesso!\n");

    } catch (IOException e) {
      System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
    }
  }
}
