import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static char letraIni = ' ';
    private static char letraFin = ' ' ;
    private static int posIni = -1;
    private static int posFin = -1;

    private static char letraShotPlayerOne = ' ';
    private static int numberShotPlayerOne = -1;

    public enum RowIndex {
        A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7), I(8), J(9);

        private final int index;

        RowIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        // Método estático para obtener el índice a partir del carácter
        public static int indexFromChar(char c) {
            try {
                return RowIndex.valueOf(String.valueOf(c)).getIndex();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Carácter no válido: " + c);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] tablero = new char[10][10];
        char[][] tableroFog = new char[10][10];
        char[][] selectedPos = new char[10][10];

        String validationResult= "";
        int countPos = -1;

        boolean aircraftOk = false;
        boolean battleShipOk = false;
        boolean submarineOk = false;
        boolean cruiserOk = false;
        boolean destroyerOk = false;

        boolean shotPlayerOne = false;
        boolean shotRangeError = false;

        iniciarTablero(tablero);
        iniciarTablero(tableroFog);
        imprimirTablero(tablero);

        // Aircraft 5 cells
        do{

            System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");

            try {
                String[] airPositions = sc.nextLine().toUpperCase().split(" ");

                extraeLetrasyNumeros(airPositions);

                validationResult = validarRango(posIni, posFin, letraIni, letraFin);
                countPos = cellsCount(posIni, posFin, letraIni, letraFin);
                System.out.println(countPos);

                if (validationResult.equals("Ok")) {
                    if (countPos == 5) {
                        prepararPosiciones(airPositions, selectedPos);
                        if (cumpleLasReglasDePosicion(selectedPos, tablero)) {

                            if (cumpleLasReglasDeAdyacencia(selectedPos, tablero)) {
                                marcarPosicion(selectedPos, tablero);
                                imprimirTablero(tablero);
                                aircraftOk = true;
                            } else {
                                System.out.println("Error! You placed it too close to another one. Try again:");
                            }

                        } else {
                            System.out.println("Error! position rules Air");
                        }
                    } else {
                        System.out.println("Error! Wrong length of the Aircraft Carrier! Try again:");
                    }
                } else {
                    System.out.println(validationResult);
                }
            } catch (Exception e) {
                System.out.println( e.getMessage());
            }
        }while(!aircraftOk);


        // Battleship 4 cells
        do{
            System.out.println("Enter the coordinates of the Battleship (4 cells):");

            try {
                String[] batPositions = sc.nextLine().toUpperCase().split(" ");

                extraeLetrasyNumeros(batPositions);
                validationResult = validarRango(posIni, posFin, letraIni, letraFin);
                countPos = cellsCount(posIni, posFin, letraIni, letraFin);
                System.out.println(countPos);

                if (validationResult.equals("Ok")) {
                    if (countPos == 4) {
                        prepararPosiciones(batPositions, selectedPos);
                        if (cumpleLasReglasDePosicion(selectedPos, tablero)) {

                            if (cumpleLasReglasDeAdyacencia(selectedPos, tablero)) {
                                marcarPosicion(selectedPos, tablero);
                                imprimirTablero(tablero);
                                battleShipOk = true;
                            } else {
                                System.out.println("Error! You placed it too close to another one. Try again:");
                            }
                        } else {
                            System.out.println("Error! position rules Batt");
                        }
                    } else {
                        System.out.println("Error! Wrong length of the Battleship! Try again:");
                    }
                } else {
                    System.out.println(validationResult);
                }
            } catch (Exception e) {
                System.out.println( e.getMessage());
            }
        }while(!battleShipOk);


        // Submarine 3 cells
        do{
            System.out.println("Enter the coordinates of the Submarine (3 cells):");

            try {
                String[] subPositions = sc.nextLine().toUpperCase().split(" ");

                extraeLetrasyNumeros(subPositions);

                validationResult = validarRango(posIni, posFin, letraIni, letraFin);
                countPos = cellsCount(posIni, posFin, letraIni, letraFin);
                System.out.println(countPos);

                if (validationResult.equals("Ok")) {
                    if (countPos == 3) {
                        prepararPosiciones(subPositions, selectedPos);
                        if (cumpleLasReglasDePosicion(selectedPos, tablero)) {

                            if (cumpleLasReglasDeAdyacencia(selectedPos, tablero)) {
                                marcarPosicion(selectedPos, tablero);
                                imprimirTablero(tablero);
                                submarineOk = true;
                            } else {
                                System.out.println("Error! You placed it too close to another one. Try again:");
                            }
                        } else {
                            System.out.println("Error! position rules Sub");
                        }
                    } else {
                        System.out.println("Error! Wrong length of the Submarine! Try again:");
                    }
                } else {
                    System.out.println(validationResult);
                }
            } catch (Exception e) {
                System.out.println( e.getMessage());
            }
        }while(!submarineOk);

        // Cruiser 3 cells
        do{
            System.out.println("Enter the coordinates of the Cruiser (3 cells):");

            try {
                String[] cruPositions = sc.nextLine().toUpperCase().split(" ");

                extraeLetrasyNumeros(cruPositions);

                validationResult = validarRango(posIni, posFin, letraIni, letraFin);
                countPos = cellsCount(posIni, posFin, letraIni, letraFin);
                System.out.println(countPos);

                if (validationResult.equals("Ok")) {
                    if (countPos == 3) {
                        prepararPosiciones(cruPositions, selectedPos);
                        if (cumpleLasReglasDePosicion(selectedPos, tablero)) {

                            if (cumpleLasReglasDeAdyacencia(selectedPos, tablero)) {
                                marcarPosicion(selectedPos, tablero);
                                imprimirTablero(tablero);
                                cruiserOk = true;
                            } else {
                                System.out.println("Error! You placed it too close to another one. Try again:");
                            }

                        } else {
                            System.out.println("Error! position rules Crui");
                        }
                    } else {
                        System.out.println("Error! Wrong length of the Cruiser! Try again:");
                    }
                }else{
                    System.out.println(validationResult);
                }
            } catch (Exception e) {
                System.out.println( e.getMessage());
            }
        }while(!cruiserOk);

        // Destroyer 2 cells
        do{
            System.out.println("Enter the coordinates of the Destroyer (2 cells):");

            try {
                String[] desPositions = sc.nextLine().toUpperCase().split(" ");

                extraeLetrasyNumeros(desPositions);

                validationResult = validarRango(posIni, posFin, letraIni, letraFin);
                countPos = cellsCount(posIni, posFin, letraIni, letraFin);
                System.out.println(countPos);

                if (validationResult.equals("Ok")) {
                    if (countPos == 2) {
                        prepararPosiciones(desPositions, selectedPos);
                        if (cumpleLasReglasDePosicion(selectedPos, tablero)) {

                            if (cumpleLasReglasDeAdyacencia(selectedPos, tablero)) {
                                marcarPosicion(selectedPos, tablero);
                                imprimirTablero(tablero);
                                destroyerOk = true;
                            } else {
                                System.out.println("Error! You placed it too close to another one. Try again:");
                            }

                        } else {
                            System.out.println("Error! position rules Dest");
                        }
                    } else {
                        System.out.println("Error! Wrong length of the Destroyer! Try again:");
                    }
                }else{
                    System.out.println(validationResult);
                }
            } catch (Exception e) {
                System.out.println( e.getMessage());
            }
        }while(!destroyerOk);

        System.out.println("The game starts!\n");


        // shot player one
        do{

            try{

                if(!shotRangeError){
                    imprimirTablero(tableroFog);
                    System.out.println();
                    System.out.println("Take a shot!");
                }
                String shotPlayerOnePosition = sc.nextLine().toUpperCase();
                extraeLetrayNumShotPlayerOne(shotPlayerOnePosition);
                validationResult = validarRangoShot(letraShotPlayerOne, numberShotPlayerOne);

                if (validationResult.equals("Ok")) {

                    char shotResult = marcarShotPosition(tablero, tableroFog, letraShotPlayerOne, numberShotPlayerOne);
                    imprimirTableroFog(tableroFog);

                    if(shotResult == 'X'){
                        System.out.println("You hit a ship!");
                    }else if(shotResult == 'M' || shotResult == 'R'){
                        System.out.println("You missed!");
                    }else if(shotResult == 'E'){
                        System.out.println("Shot Error...");
                    }

                    imprimirTablero(tablero);
                    shotPlayerOne = true;
                }else if(validationResult.equals("Error")){
                    System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                    shotRangeError = true;
                }
            }catch(Exception e){
                System.out.println( e.getMessage());
            }
        }while(!shotPlayerOne);

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
        System.out.println();
    }

    public static void imprimirTableroFog(char[][] tableroFog){
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char letraMay = 'A';

        for(int i = 0; i < tableroFog.length; i++){
            System.out.print(letraMay);
            letraMay++;

            for(int j = 0; j < tableroFog[0].length; j++){
                System.out.print(" " + tableroFog[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void iniciarTablero(char[][] tablero){

        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero[0].length; j++){
                tablero[i][j] = '~';
            }
        }
    }


    public static String validarRango(int posIni, int posFin, char letraIni, char letraFin){

        if(posIni < 1 || posIni > 10 || posFin < 1 || posFin > 10 ||
                letraIni < 'A' || letraIni > 'J' || letraFin < 'A' || letraFin > 'J' ){
            return "Error! Wrong ship location! Try again:";
        }

        if(letraIni != letraFin && posIni != posFin){
            return "Error! Wrong ship location! Try again:";
        }

        return "Ok";
    }

    public static int cellsCount(int posIni, int posFin, char letraIni, char letraFin){
        if(posIni == posFin){
            return rowsCounter(letraIni, letraFin);
        }

        if(letraIni == letraFin){
            return colsCounter(posIni, posFin);
        }
        return -1;
    }

    //rotorna la cantidad de celdas entre columnas
    public static int colsCounter(int posIni, int posFin){
        return Math.abs(posIni - posFin) + 1;
    }

    //retorna la cantidad de celdas entre filas
    public static int rowsCounter(char letraIni, char letraFin){
        return Math.abs(letraIni - letraFin) + 1;
    }


    //se inicializa una matriz auxiliar
    //se marcan las posiciónes ingresada del barco que se quiere colocar en el tablero
    public static void prepararPosiciones(String[] pos, char[][] selectedPos){
        char letraIni = pos[0].charAt(0);
        char letraFin = pos[1].charAt(0);
        int posIni = Integer.parseInt(pos[0].substring(1));
        int posFin = Integer.parseInt(pos[1].substring(1));
        posIni--;
        posFin--;

        iniciarTablero(selectedPos);

        //igual número de posición diferente letra
        if(posIni == posFin && letraIni != letraFin){

            int intLetraIni = RowIndex.indexFromChar(letraIni);
            int intLetraFin = RowIndex.indexFromChar(letraFin);

            if(letraFin > letraIni){
                for(int i = intLetraIni; i <= intLetraFin; i++){
                    selectedPos[i][posIni] = 'O';
                }
            }else{
                for(int i = intLetraIni; i >= intLetraFin; i--){
                    selectedPos[i][posIni] = 'O';
                }
            }
        }

        //igual letra diferente número de posición
        if(posIni != posFin && letraIni == letraFin){
            if (posFin > posIni){

                for(int i = posIni; i <= posFin; i++){
                    // System.out.println("Letra: " + RowIndex.indexFromChar(letraIni) + " i: " + i);
                    selectedPos[RowIndex.indexFromChar(letraIni)][i] = 'O';
                }
            }else{

                for(int i = posIni; i >= posFin; i--){
                    // System.out.println("E - Letra: " + RowIndex.indexFromChar(letraIni) + " i: " + i);
                    selectedPos[RowIndex.indexFromChar(letraIni)][i] = 'O';
                }
            }
        }

    }

    public static void extraeLetrasyNumeros(String[] positions){
        letraIni = extraeLetra(positions,'F');
        letraFin = extraeLetra(positions,'S');

        posIni = extraeNumero(positions,'F');
        posFin = extraeNumero(positions,'S');
    }

    public static char extraeLetra(String[] positions, char firstOrSecondCell ){
        if( firstOrSecondCell == 'F'){
            return positions[0].charAt(0);
        }else{
            return positions[1].charAt(0);
        }
    }

    public static int extraeNumero(String[] positions, char firstOrSecondCell ){
        if( firstOrSecondCell == 'F'){
            return Integer.parseInt(positions[0].substring(1));
        }else{
            return Integer.parseInt(positions[1].substring(1));
        }
    }

    public static void extraeLetrayNumShotPlayerOne(String position){
        letraShotPlayerOne = extraeLetraShotPlayerOne(position,'O');
        numberShotPlayerOne = extraeNumeroShotPlayerOne(position);
    }

    public static char extraeLetraShotPlayerOne(String positions, char playerOneOrTwo ){
        return positions.charAt(0);
    }

    public static int extraeNumeroShotPlayerOne(String positions){
        return Integer.parseInt(positions.substring(1));
    }

    public static String validarRangoShot(char letra, int cellNumber){

        if(cellNumber < 1 || cellNumber > 10  || letra < 'A' || letra > 'J'){
            return "Error";
        }

        return "Ok";
    }

    //se recorren las posiciones elegidas
    //se verifica si cumplen con las reglas de posición del juego
    public static boolean cumpleLasReglasDePosicion(char[][] selectedPos, char[][] tablero){

        for(int i = 0; i < selectedPos.length;i++){
            for(int j = 0; j< selectedPos[0].length; j++){
                if(selectedPos[i][j] == 'O'){
                    if(tablero[i][j] == 'O'){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static boolean cumpleLasReglasDeAdyacencia(char[][] selectedPos, char[][] tablero){
        ArrayList<String> celdasSelected = new ArrayList<String>();
        ArrayList<String> celdasAdyacentes = new ArrayList<String>();

        for(int i = 0; i < selectedPos.length; i++){
            for(int j = 0; j < selectedPos[0].length; j++){
                if(selectedPos[i][j] == 'O'){
                    String unirNums = i + "" + j;
                    celdasSelected.add(unirNums);
                }
            }
        }

        int rowIni = Integer.valueOf(celdasSelected.get(0).substring(0, 1));
        int colIni = Integer.valueOf(celdasSelected.get(0).substring(1));
        int rowFin = Integer.valueOf(celdasSelected.get(celdasSelected.size() -1).substring(0, 1));
        int colFin = Integer.valueOf(celdasSelected.get(celdasSelected.size() -1).substring(1));

        if(rowIni == rowFin){
            // nave con ubicación Horizontal
            if(rowIni >= 0 && rowIni <= 9){
                int colIniAd = colIni - 1;
                if(colIniAd >= 0 && colIniAd <= 9){
                    celdasAdyacentes.add(rowIni + "" + colIniAd);
                }
            }

            if(rowFin >= 0 && rowFin <= 9){
                int colFinAd = colFin + 1;
                if(colFinAd >= 0 && colFinAd <= 9){
                    celdasAdyacentes.add(rowFin + "" + colFinAd);
                }
            }

            for(int i = 0; i < celdasSelected.size(); i++){
                int rIni = Integer.valueOf(celdasSelected.get(i).substring(0, 1));
                int cIni = Integer.valueOf(celdasSelected.get(i).substring(1));

                if(cIni >= 0 && cIni <= 9){
                    int rIniSupAd = rIni - 1;
                    if(rIniSupAd >= 0 && rIniSupAd <= 9){
                        celdasAdyacentes.add(rIniSupAd + "" + cIni);
                    }

                    int rIniInfAd = rIni + 1;
                    if(rIniInfAd >= 0 && rIniInfAd <= 9){
                        celdasAdyacentes.add(rIniInfAd + "" + cIni);
                    }
                }
            }
        } else {
            // nave con ubicación Vertical
            if(colIni >= 0 && colIni <= 9){
                int rowIniAd = rowIni - 1;
                if(rowIniAd >= 0 && rowIniAd <= 9){
                    celdasAdyacentes.add(rowIniAd + "" + colIni);
                }
            }

            if(colIni >= 0 && colIni <= 9){
                int rowFinAd = rowFin + 1;
                if(rowFinAd >= 0 && rowFinAd <= 9){
                    celdasAdyacentes.add((rowFin + 1) + "" + colFin);
                }
            }

            for (int i = 0; i < celdasSelected.size(); i++) {
                int rIni = Integer.valueOf(celdasSelected.get(i).substring(0, 1));
                int cIni = Integer.valueOf(celdasSelected.get(i).substring(1));

                if(rIni >= 0 && rIni <= 9){
                    int colAdIzq = cIni - 1;
                    if(colAdIzq >= 0 && colAdIzq <= 9){
                        celdasAdyacentes.add(rIni + "" + colAdIzq);
                    }

                    int colAdDer = cIni + 1;
                    if(colAdDer >= 0 && colAdDer <= 9){
                        celdasAdyacentes.add(rIni + "" + colAdDer);
                    }
                }
            }
        }

        System.out.println(celdasAdyacentes);

        for(String cAd: celdasAdyacentes){
            int rIni = Integer.valueOf(cAd.substring(0, 1));
            int cIni = Integer.valueOf(cAd.substring(1));

            if(tablero[rIni][cIni] != '~'){
                return false;
            }
        }
        return true;
    }

    //se marca en el tablero del juego la posicion del barco actual
    public static void marcarPosicion(char[][] selectedPos, char[][] tablero){

        for(int i = 0; i < selectedPos.length;i++){
            for(int j = 0; j< selectedPos[0].length; j++){
                if(selectedPos[i][j] == 'O'){
                    tablero[i][j] = 'O';
                }
            }
        }
    }

    //se marca en el tablero del juego la posicion del barco actual
    public static char marcarShotPosition(char[][] tablero, char[][] tableroFog, char letra, int cellNumber){

        int letraIndex = RowIndex.indexFromChar(letra);
        int numberIndex = cellNumber -1;

        if(tablero[letraIndex][numberIndex] == 'O'){
            tablero[letraIndex][numberIndex] = 'X';
            tableroFog[letraIndex][numberIndex] = 'X';
            return 'X';
        }else if(tablero[letraIndex][numberIndex] == '~'){
            tablero[letraIndex][numberIndex] = 'M';
            tableroFog[letraIndex][numberIndex] = 'M';
            return 'M';
        }
        else if(tablero[letraIndex][numberIndex] == 'M'){
            return 'R';
        }else{
            return 'E';
        }

    }

}

