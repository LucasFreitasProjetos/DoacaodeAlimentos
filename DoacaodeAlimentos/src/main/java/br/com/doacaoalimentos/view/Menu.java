package br.com.doacaoalimentos.view;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import br.com.doacaoalimentos.controller.DoadorController;
import br.com.doacaoalimentos.controller.InstituicaoController;
import br.com.doacaoalimentos.controller.DoacaoController;
import br.com.doacaoalimentos.model.Doador;
import br.com.doacaoalimentos.model.Instituicao;
import br.com.doacaoalimentos.model.Doacao;

public class Menu {
    public static void exibir() {
        Scanner scanner = new Scanner(System.in);
        DoadorController doadorController = new DoadorController();
        InstituicaoController instituicaoController = new InstituicaoController();
        DoacaoController doacaoController = new DoacaoController();

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n===== Sistema de Doação de Alimentos =====");
            System.out.println("1 - Cadastrar doador");
            System.out.println("2 - Listar doadores");
            System.out.println("3 - Atualizar doador");
            System.out.println("4 - Excluir doador");
            System.out.println("5 - Cadastrar instituição");
            System.out.println("6 - Listar instituições");
            System.out.println("7 - Atualizar instituição");
            System.out.println("8 - Excluir instituição");
            System.out.println("9 - Cadastrar doação");
            System.out.println("10 - Listar doações");
            System.out.println("11 - Atualizar doação");
            System.out.println("12 - Excluir doação");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Opção inválida.");
                continue;
            }
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarDoador(scanner, doadorController);
                    break;
                case 2:
                    listarDoadores(doadorController);
                    break;
                case 3:
                    atualizarDoador(scanner, doadorController);
                    break;
                case 4:
                    excluirDoador(scanner, doadorController);
                    break;
                case 5:
                    cadastrarInstituicao(scanner, instituicaoController);
                    break;
                case 6:
                    listarInstituicoes(instituicaoController);
                    break;
                case 7:
                    atualizarInstituicao(scanner, instituicaoController);
                    break;
                case 8:
                    excluirInstituicao(scanner, instituicaoController);
                    break;
                case 9:
                    cadastrarDoacao(scanner, doacaoController, doadorController, instituicaoController);
                    break;
                case 10:
                    listarDoacoes(doacaoController);
                    break;
                case 11:
                    atualizarDoacao(scanner, doacaoController, doadorController, instituicaoController);
                    break;
                case 12:
                    excluirDoacao(scanner, doacaoController);
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
        scanner.close();
    }

    private static void cadastrarDoador(Scanner scanner, DoadorController controller) {
        System.out.print("Nome do doador: ");
        String nome = scanner.nextLine();
        System.out.print("E-mail do doador: ");
        String email = scanner.nextLine();

        Doador doador = new Doador();
        doador.setNome(nome);
        doador.setEmail(email);

        if (controller.cadastrar(doador)) {
            System.out.println("Doador cadastrado com sucesso.");
        } else {
            System.out.println("Erro ao cadastrar doador.");
        }
    }

    private static void listarDoadores(DoadorController controller) {
        List<Doador> doadores = controller.listar();
        if (doadores.isEmpty()) {
            System.out.println("Nenhum doador encontrado.");
            return;
        }
        System.out.println("\n--- Lista de Doadores ---");
        for (Doador doador : doadores) {
            System.out.println(doador);
        }
    }

    private static void atualizarDoador(Scanner scanner, DoadorController controller) {
        System.out.print("ID do doador para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Doador doador = controller.buscarPorId(id);
        if (doador == null) {
            System.out.println("Doador não encontrado.");
            return;
        }

        System.out.print("Novo nome (atual: " + doador.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo e-mail (atual: " + doador.getEmail() + "): ");
        String email = scanner.nextLine();

        doador.setNome(nome.isEmpty() ? doador.getNome() : nome);
        doador.setEmail(email.isEmpty() ? doador.getEmail() : email);

        if (controller.atualizar(doador)) {
            System.out.println("Doador atualizado com sucesso.");
        } else {
            System.out.println("Erro ao atualizar doador.");
        }
    }

    private static void excluirDoador(Scanner scanner, DoadorController controller) {
        System.out.print("ID do doador para excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (controller.excluir(id)) {
            System.out.println("Doador excluído com sucesso.");
        } else {
            System.out.println("Erro ao excluir doador.");
        }
    }

    private static void cadastrarInstituicao(Scanner scanner, InstituicaoController controller) {
        System.out.print("Nome da instituição: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço da instituição: ");
        String endereco = scanner.nextLine();

        Instituicao instituicao = new Instituicao();
        instituicao.setNome(nome);
        instituicao.setEndereco(endereco);

        if (controller.cadastrar(instituicao)) {
            System.out.println("Instituição cadastrada com sucesso.");
        } else {
            System.out.println("Erro ao cadastrar instituição.");
        }
    }

    private static void listarInstituicoes(InstituicaoController controller) {
        List<Instituicao> instituicoes = controller.listar();
        if (instituicoes.isEmpty()) {
            System.out.println("Nenhuma instituição encontrada.");
            return;
        }
        System.out.println("\n--- Lista de Instituições ---");
        for (Instituicao instituicao : instituicoes) {
            System.out.println(instituicao);
        }
    }

    private static void atualizarInstituicao(Scanner scanner, InstituicaoController controller) {
        System.out.print("ID da instituição para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Instituicao instituicao = controller.buscarPorId(id);
        if (instituicao == null) {
            System.out.println("Instituição não encontrada.");
            return;
        }

        System.out.print("Novo nome (atual: " + instituicao.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo endereço (atual: " + instituicao.getEndereco() + "): ");
        String endereco = scanner.nextLine();

        instituicao.setNome(nome.isEmpty() ? instituicao.getNome() : nome);
        instituicao.setEndereco(endereco.isEmpty() ? instituicao.getEndereco() : endereco);

        if (controller.atualizar(instituicao)) {
            System.out.println("Instituição atualizada com sucesso.");
        } else {
            System.out.println("Erro ao atualizar instituição.");
        }
    }

    private static void excluirInstituicao(Scanner scanner, InstituicaoController controller) {
        System.out.print("ID da instituição para excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (controller.excluir(id)) {
            System.out.println("Instituição excluída com sucesso.");
        } else {
            System.out.println("Erro ao excluir instituição.");
        }
    }

    private static void cadastrarDoacao(Scanner scanner, DoacaoController doacaoController, DoadorController doadorController, InstituicaoController instituicaoController) {
        System.out.println("\n--- Lista de doadores ---");
        listarDoadores(doadorController);
        System.out.print("ID do doador: ");
        int doadorId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n--- Lista de instituições ---");
        listarInstituicoes(instituicaoController);
        System.out.print("ID da instituição: ");
        int instituicaoId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Descrição da doação: ");
        String descricao = scanner.nextLine();
        System.out.print("Data da doação (yyyy-mm-dd): ");
        String data = scanner.nextLine();

        try {
            Date dataDoacao = Date.valueOf(data);
            Doacao doacao = new Doacao();
            doacao.setDoadorId(doadorId);
            doacao.setInstituicaoId(instituicaoId);
            doacao.setDescricao(descricao);
            doacao.setDataDoacao(dataDoacao);

            if (doacaoController.cadastrar(doacao)) {
                System.out.println("Doação cadastrada com sucesso.");
            } else {
                System.out.println("Erro ao cadastrar doação.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Data inválida. Use o formato yyyy-mm-dd.");
        }
    }

    private static void listarDoacoes(DoacaoController controller) {
        List<Doacao> doacoes = controller.listar();
        if (doacoes.isEmpty()) {
            System.out.println("Nenhuma doação encontrada.");
            return;
        }
        System.out.println("\n--- Lista de Doações ---");
        for (Doacao doacao : doacoes) {
            System.out.println(doacao);
        }
    }

    private static void atualizarDoacao(Scanner scanner, DoacaoController doacaoController, DoadorController doadorController, InstituicaoController instituicaoController) {
        System.out.print("ID da doação para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Doacao doacao = doacaoController.buscarPorId(id);
        if (doacao == null) {
            System.out.println("Doação não encontrada.");
            return;
        }

        System.out.println("\n--- Lista de doadores ---");
        listarDoadores(doadorController);
        System.out.print("Novo ID do doador (atual: " + doacao.getDoadorId() + "): ");
        String doadorIdStr = scanner.nextLine();

        System.out.println("\n--- Lista de instituições ---");
        listarInstituicoes(instituicaoController);
        System.out.print("Novo ID da instituição (atual: " + doacao.getInstituicaoId() + "): ");
        String instituicaoIdStr = scanner.nextLine();

        System.out.print("Nova descrição (atual: " + doacao.getDescricao() + "): ");
        String descricao = scanner.nextLine();
        System.out.print("Nova data da doação (yyyy-mm-dd) (atual: " + doacao.getDataDoacao() + "): ");
        String data = scanner.nextLine();

        if (!doadorIdStr.isEmpty()) {
            doacao.setDoadorId(Integer.parseInt(doadorIdStr));
        }
        if (!instituicaoIdStr.isEmpty()) {
            doacao.setInstituicaoId(Integer.parseInt(instituicaoIdStr));
        }
        doacao.setDescricao(descricao.isEmpty() ? doacao.getDescricao() : descricao);
        if (!data.isEmpty()) {
            try {
                doacao.setDataDoacao(Date.valueOf(data));
            } catch (IllegalArgumentException ex) {
                System.out.println("Data inválida. Use o formato yyyy-mm-dd.");
                return;
            }
        }

        if (doacaoController.atualizar(doacao)) {
            System.out.println("Doação atualizada com sucesso.");
        } else {
            System.out.println("Erro ao atualizar doação.");
        }
    }

    private static void excluirDoacao(Scanner scanner, DoacaoController controller) {
        System.out.print("ID da doação para excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (controller.excluir(id)) {
            System.out.println("Doação excluída com sucesso.");
        } else {
            System.out.println("Erro ao excluir doação.");
        }
    }
}
