package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Model_User {
  private String nome;
  private String senha;

  public Model_User(Scanner scanner) {
    System.out.print("Digite o nome do usuário: ");
    this.nome = scanner.nextLine();

    System.out.print("Digite a senha: ");
    this.senha = scanner.nextLine();
  }

  public void salvarEmArquivo() {
    try {
      FileWriter writer = new FileWriter("DB/usuarios.txt", true); // true = append
      writer.write(nome + "|" + senha + "\n");
      writer.close();
      System.out.println("Usuario salvo com sucesso!\n\n\n");
    } catch (IOException e) {
      System.out.println("Erro ao salvar usuario: " + e.getMessage());
    }
  }

}
