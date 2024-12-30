import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] tablero = new char[10][10];
        iniciarTablero(tablero);
        imprimirTablero(tablero);
        System.out.println("Enter the coordinates of the ship:");
        String[] position = sc.nextLine().split(" ");
        String resultVal = validarRango(position);
        System.out.println(resultVal);

        if(!resultVal.equals("Error!")){
            System.out.println(imprimirPos(position));
        }

    }

    public static void imprimirTablero(char[][] tablero){
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char letraMay = 'A';

        for(int i = 0; i < tablero.length; i++){
            System.out.print(letraMay);
            letraMay++;

            for(int j = 0; j < tablero[0].length; j++){
                System.out.print(" " + tablero[i][j]);
            }
            System.out.println();
        }
    }

    public static void iniciarTablero(char[][] tablero){

        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero[0].length; j++){
                tablero[i][j] = '~';
            }
        }
    }

    public static String validarRango(String[] pos){
        char letraIni = pos[0].charAt(0);
        char letraFin = pos[1].charAt(0);
        int posIni = Integer.parseInt(pos[0].substring(1));
        int posFin = Integer.parseInt(pos[1].substring(1));

        if(posIni <=0 || posIni > 10 || posFin <= 0 || posFin > 10 ||
                letraIni < 'A' || letraIni > 'J' || letraFin < 'A' || letraFin > 'J' ){
            return "Error!";
        }

        if(letraIni != letraFin && posIni != posFin){
            return "Error!";
        }

        if(posIni == posFin){
            int distLetters = Math.abs(letraIni - letraFin) + 1;
            return "Length: " + distLetters;
        }

        int distPos = Math.abs(posIni - posFin) + 1;
        return "Length: " + distPos;
    }

    public static String imprimirPos(String[] pos){
        char letraIni = pos[0].charAt(0);
        char letraFin = pos[1].charAt(0);
        int posIni = Integer.parseInt(pos[0].substring(1));
        int posFin = Integer.parseInt(pos[1].substring(1));
        String resultado = "";

        //igual número de posición diferente letra
        if(posIni == posFin && letraIni != letraFin){
            if(letraFin > letraIni){
                for(char i = letraIni; i <= letraFin; i++){
                    resultado+= " " + i + posIni;
                }
            }else{
                for(char i = letraIni; i >= letraFin; i--){
                    resultado+= " " + i + posIni;
                }
            }

        }

        //igual letra diferente número de posición
        if(posIni != posFin && letraIni == letraFin){

            if (posFin > posIni){

                for(int i = posIni; i <= posFin; i++){
                    resultado+= " " + letraIni + i;
                }
            }else{

                for(int i = posIni; i >= posFin; i--){
                    resultado+= " " + letraIni + i;
                }
            }
        }

        return resultado;
    }

}

