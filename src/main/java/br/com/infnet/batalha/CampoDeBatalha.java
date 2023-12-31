package br.com.infnet.batalha;

import br.com.infnet.personagens.Personagem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CampoDeBatalha {
    private static final Logger LOGGER = LoggerFactory.getLogger(CampoDeBatalha.class);
    public static void Batalha(String nickname, Personagem enemyPersonagem, Personagem userPersonagem) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatadaAtual = LocalDate.now().format(formatter);
        String statusFinal;
        Personagem atacante = null;
        Personagem atacado = null;
        int contadorRodadas = 0;
        String reset = "\u001B[0m";
        String vermehlo = "\u001B[31m";
        String amarelo = "\u001B[33m";

        while (true) {
            contadorRodadas++;
            // calculo da iniciativa
            int enemyInciativa = enemyPersonagem.calcularIniciativa();
            int userIniciativa = userPersonagem.calcularIniciativa();

            // caso empate
            if (userIniciativa == enemyInciativa) {
                while (userIniciativa == enemyInciativa) {

                    enemyInciativa = enemyPersonagem.calcularIniciativa();
                    userIniciativa = userPersonagem.calcularIniciativa();
                }

            } else {

                // inimigo maior iniciativa
                if (enemyInciativa > userIniciativa) {
                    atacante = enemyPersonagem;
                    atacado = userPersonagem;
                }

                // user maior iniciativa
                if (userIniciativa > enemyInciativa) {
                    atacante = userPersonagem;
                    atacado = enemyPersonagem;
                }
            }

            // ataque
            int ataque = atacante.calcularFatorDeAtaque();

            // defesa
            int defesa = atacado.calcularFatorDeDefesa();

            // ocorre o ataque
            if (ataque > defesa) {

                int dano = atacante.calcularFatorDeDano();
                atacado.setPontosDeVida(atacado.getPontosDeVida() - dano);

                LOGGER.info("\n==================================");
                LOGGER.info("\nO " + atacante.getNome() + " causou " + dano + " de dano no " + atacado.getNome());
                LOGGER.info("\n==================================");
                LOGGER.info("\nVida atual do " + atacado.getNome() + ": " + atacado.getPontosDeVida());
                LOGGER.info("\nVida atual do " + atacante.getNome() + ": " + atacante.getPontosDeVida());

            }

            if (enemyPersonagem.getPontosDeVida() <= 0) {
                LOGGER.info("\nO " + amarelo + userPersonagem.getNome() + reset + " ganhou do " + enemyPersonagem.getNome());
                statusFinal = "Ganhou";
                break;
            }
            if (userPersonagem.getPontosDeVida() <= 0) {
                LOGGER.info("\nO " + vermehlo + enemyPersonagem.getNome() + reset + " ganhou do " + userPersonagem.getNome());
                statusFinal = "Perdeu";
                break;
            }
        }

        List<String> dados = Arrays.asList(
                dataFormatadaAtual,
                userPersonagem.getNome(),
                statusFinal,
                enemyPersonagem.getNome(),
                String.valueOf(contadorRodadas));

        Gravacoes.Logs(nickname, dados);

    }
}
