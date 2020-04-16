public static void main(String[] args) {

        int tamanho_pacote = 100;
        int tamanho_ultimo_pacote = tamanho_pacote;

        byte dados_a_transmitir[] = new byte[305];
        for (int i = 0; i < dados_a_transmitir.length; i++) {
            dados_a_transmitir[i] = (byte) (10 + i % 90);
        }

        int pacotes = (int) (dados_a_transmitir.length / tamanho_pacote);
        if (dados_a_transmitir.length % tamanho_pacote != 0) {
            //tamanho_ultimo_pacote = dados_a_transmitir.length % tamanho_pacote;
            tamanho_ultimo_pacote = dados_a_transmitir.length - pacotes * tamanho_pacote;
            pacotes++;
        }

        System.out.println(pacotes);
        System.out.println(tamanho_ultimo_pacote);

        for (int i = 0; i < pacotes - 1; i++) {
            System.out.println("");
            for (int j = 0; j < tamanho_pacote; j++) {
                System.out.print(dados_a_transmitir[i * tamanho_pacote + j] + " ");
            }
        }

        System.out.println("");
        for (int j = 0; j < tamanho_ultimo_pacote; j++) {
            System.out.print(dados_a_transmitir[(pacotes - 1) * tamanho_pacote + j] + " ");
        }

    }