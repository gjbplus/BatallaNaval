import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static char letraIni = ' ';
    private static char letraFin = ' ' ;
    private static int posIni = -1;
    private static int posFin = -1;

    private static char[][] tableroPlayerOne = new char[10][10];
    private static char[][] tableroFogPlayerOne = new char[10][10];
    private static char[][] tableroPlayerTwo = new char[10][10];
    private static char[][] tableroFogPlayerTwo = new char[10][10];
    private static char[][] selectedPos = new char[10][10];

    private static char letraShotPlayerOne = ' ';
    private static int numberShotPlayerOne = -1;

    private static char letraShotPlayerTwo = ' ';
    private static int numberShotPlayerTwo = -1;

    private static List<Barco> barcosPlayerOne = new ArrayList<>();
    private static List<Barco> barcosPlayerTwo = new ArrayList<>();
    private static boolean finishGame = false;

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

        String validationResult= "";
        String playerNumber = "one";
        int players = 2;
        iniciarTableros();

        for (int i = 0; i < players; i++) {
            String currentPlayer = (i == 0) ? "Player 1" : "Player 2";
            playerNumber = (i == 0) ? "one" : "two";
            char[][] currentTablero = (i == 0) ? tableroPlayerOne : tableroPlayerTwo;
            char[][] currentFog = (i == 0) ? tableroFogPlayerOne : tableroFogPlayerTwo;
            List<Barco> currentFleet = (i == 0) ? barcosPlayerOne : barcosPlayerTwo;
            System.out.println(currentPlayer + ", place your ships to the game field:");
            imprimirTablero(currentTablero);

            colocarBarco("Aircraft Carrier", 5, currentTablero, currentFleet, sc, playerNumber);
            colocarBarco("Battleship", 4, currentTablero, currentFleet, sc, playerNumber);
            colocarBarco("Submarine", 3, currentTablero, currentFleet, sc, playerNumber);
            colocarBarco("Cruiser", 3, currentTablero, currentFleet, sc, playerNumber);
            colocarBarco("Destroyer", 2, currentTablero, currentFleet, sc, playerNumber);

            if(i == 0){
                System.out.println("Press Enter and pass the move to another player");
                System.out.println("...");
                sc.nextLine();
            }
        }

        playerNumber = "one";

        do{
            try{
                System.out.println("Press Enter and pass the move to another player");
                System.out.println("...");
                sc.nextLine();

                if(playerNumber.equals("one")){
                    imprimirTablero(tableroFogPlayerTwo);
                    System.out.println("---------------------");
                    imprimirTablero(tableroPlayerOne);
                    System.out.println("\nPlayer 1, it's your turn:");

                    String shotPlayerOnePosition = sc.nextLine().toUpperCase();
                    extraeLetrayNumShotPlayer(shotPlayerOnePosition, 'O');
                    validationResult = validarRangoShot(letraShotPlayerOne, numberShotPlayerOne);

                    if (validationResult.equals("Ok")) {

                        char shotResult = marcarShotPosition(letraShotPlayerOne, numberShotPlayerOne, playerNumber);
                        char gameEventResult = atacarCelda(letraShotPlayerOne, numberShotPlayerOne, playerNumber);

                        gameEvent(gameEventResult, shotResult);
                        playerNumber = "two";

                    }else if(validationResult.equals("Error")){
                        System.out.println("Error! You entered the wrong coordinates! Try again:");
                    }

                }else if (playerNumber.equals("two")){

                    imprimirTablero(tableroFogPlayerOne);
                    System.out.println("---------------------");
                    imprimirTablero(tableroPlayerTwo);
                    System.out.println("\nPlayer 2, it's your turn:");

                    String shotPlayerTwoPosition = sc.nextLine().toUpperCase();
                    extraeLetrayNumShotPlayer(shotPlayerTwoPosition, 'T');
                    validationResult = validarRangoShot(letraShotPlayerTwo, numberShotPlayerTwo);

                    if (validationResult.equals("Ok")) {

                        char shotResult = marcarShotPosition(letraShotPlayerTwo, numberShotPlayerTwo, playerNumber);
                        char gameEventResult = atacarCelda(letraShotPlayerTwo, numberShotPlayerTwo, playerNumber);

                        gameEvent(gameEventResult, shotResult);
                        playerNumber = "one";

                    }else if(validationResult.equals("Error")){
                        System.out.println("Error! You entered the wrong coordinates! Try again:");
                    }
                }

            }catch(Exception e){
                System.out.println( e.getMessage());
            }

        }while(!finishGame);

    }

    /////////////////////////////////////
    // Static functions and procedures //

    public static void gameEvent(char gameEventResult, char shotResult){

        if(gameEventResult == 'S'){
            System.out.println("You sank a ship! Specify a new target:\n");

        }else if(gameEventResult == 'W'){
            System.out.println("You sank the last ship. You won. Congratulations!\n");
            finishGame = true;

        }else if(gameEventResult == 'M'){

            if(shotResult == 'X'){
                System.out.println("You hit a ship! Try again:");
            }else if(shotResult == 'M' || shotResult == 'R'){
                System.out.println("You missed. Try again:");
            }else if(shotResult == 'E'){
                System.out.println("Shot Error...");
            }
        }

    }

    public static void colocarBarco(String tipo, int tamano, char[][] tablero, List<Barco> listaBarcos, Scanner sc, String playerNumber) {
        boolean barcoOk = false;
        System.out.println("Enter the coordinates of the " + tipo + " (" + tamano + " cells):");

        do {
            try {
                String[] posiciones = sc.nextLine().toUpperCase().split(" ");

                extraeLetrasyNumeros(posiciones);
                String validationResult = validarRango(posIni, posFin, letraIni, letraFin);
                int countPos = cellsCount(posIni, posFin, letraIni, letraFin);

                if (validationResult.equals("Ok")) {
                    if (countPos == tamano) {
                        prepararPosiciones(posiciones);
                        if (cumpleLasReglasDePosicion(selectedPos, tablero)) {
                            if (cumpleLasReglasDeAdyacencia(selectedPos, tablero)) {
                                marcarPosicion(selectedPos, tablero, tipo.toLowerCase(),playerNumber );
                                imprimirTablero(tablero);
                                barcoOk = true;
                            } else {
                                System.out.println("Error! You placed it too close to another one. Try again:");
                            }
                        } else {
                            System.out.println("Error! position rules " + tipo);
                        }
                    } else {
                        System.out.println("Error! Wrong length of the " + tipo + "! Try again:");
                    }
                } else {
                    System.out.println(validationResult);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!barcoOk);
    }

    public static void iniciarTableros(){

        for(int i = 0; i < tableroPlayerOne.length; i++){
            for(int j = 0; j < tableroPlayerOne[0].length; j++){
                tableroPlayerOne[i][j] = '~';
                tableroPlayerTwo[i][j] = '~';

                tableroFogPlayerOne[i][j] = '~';
                tableroFogPlayerTwo[i][j] = '~';
            }
        }

    }

    public static void iniciarTablero(char[][] tablero){

        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero[0].length; j++){
                tablero[i][j] = '~';
            }
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
    public static void prepararPosiciones(String[] pos){
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

    public static void extraeLetrayNumShotPlayer(String position, char player){
        if(player == 'O'){
            letraShotPlayerOne = extraeLetraShotPlayer(position);
            numberShotPlayerOne = extraeNumeroShotPlayer(position);
        }else if(player == 'T'){
            letraShotPlayerTwo = extraeLetraShotPlayer(position);
            numberShotPlayerTwo = extraeNumeroShotPlayer(position);
        }
    }

    public static char extraeLetraShotPlayer(String positions){
        return positions.charAt(0);
    }

    public static int extraeNumeroShotPlayer(String positions){
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
    public static void marcarPosicion(char[][] selectedPos, char[][] tablero, String name, String player){
        List<String> celdas = new ArrayList();

        for(int i = 0; i < selectedPos.length;i++){
            for(int j = 0; j< selectedPos[0].length; j++){
                if(selectedPos[i][j] == 'O'){
                    celdas.add(i + "" + j);
                }
            }
        }


        if(name.equals("aircraft carrier")){

            Barco aircraft = new Barco(celdas);
            if(player.equals("one")){
                barcosPlayerOne.add(aircraft);
            }else if(player.equals("two")){
                barcosPlayerTwo.add(aircraft);
            }

        }else if(name.equals("battleship")){

            Barco battleShip = new Barco(celdas);
            if(player.equals("one")){
                barcosPlayerOne.add(battleShip);
            }else if(player.equals("two")){
                barcosPlayerTwo.add(battleShip);
            }

        }else if(name.equals("submarine")){

            Barco submarine = new Barco(celdas);
            if(player.equals("one")){
                barcosPlayerOne.add(submarine);
            }else if(player.equals("two")){
                barcosPlayerTwo.add(submarine);
            }

        }else if(name.equals("cruiser")){

            Barco cruiser = new Barco(celdas);
            if(player.equals("one")){
                barcosPlayerOne.add(cruiser);
            }else if(player.equals("two")){
                barcosPlayerTwo.add(cruiser);
            }

        }else if(name.equals("destroyer")){

            Barco destroyer = new Barco(celdas);
            if(player.equals("one")){
                barcosPlayerOne.add(destroyer);
            }else if(player.equals("two")){
                barcosPlayerTwo.add(destroyer);
            }
        }

        for(int i = 0; i < selectedPos.length;i++){
            for(int j = 0; j< selectedPos[0].length; j++){
                if(selectedPos[i][j] == 'O'){
                    tablero[i][j] = 'O';
                }
            }
        }
    }

    //se marca en el tablero del juego la posicion del barco actual
    public static char marcarShotPosition(char letra, int cellNumber, String player){

        int letraIndex = RowIndex.indexFromChar(letra);
        int numberIndex = cellNumber -1;

        if(player.equals("one")){

            if(tableroPlayerTwo[letraIndex][numberIndex] == 'O'){
                tableroPlayerTwo[letraIndex][numberIndex] = 'X';
                tableroFogPlayerTwo[letraIndex][numberIndex] = 'X';
                return 'X';
            }else if(tableroPlayerTwo[letraIndex][numberIndex] == '~'){
                tableroPlayerTwo[letraIndex][numberIndex] = 'M';
                tableroFogPlayerTwo[letraIndex][numberIndex] = 'M';
                return 'M';
            }else if(tableroPlayerTwo[letraIndex][numberIndex] == 'M'){
                return 'R';
            }

        }else if(player.equals("two")){

            if(tableroPlayerOne[letraIndex][numberIndex] == 'O'){
                tableroPlayerOne[letraIndex][numberIndex] = 'X';
                tableroFogPlayerOne[letraIndex][numberIndex] = 'X';
                return 'X';
            }else if(tableroPlayerOne[letraIndex][numberIndex] == '~'){
                tableroPlayerOne[letraIndex][numberIndex] = 'M';
                tableroFogPlayerOne[letraIndex][numberIndex] = 'M';
                return 'M';
            }else if(tableroPlayerOne[letraIndex][numberIndex] == 'M'){
                return 'R';
            }
        }
        return 'E';
    }

    public static char atacarCelda(char letra, int cellNumber, String player) {
        int letraIndex = RowIndex.indexFromChar(letra);
        int numberIndex = cellNumber -1;
        String celda = letraIndex + "" + numberIndex;

        if(player.equals("one")){
            for (Barco barco : barcosPlayerTwo) {

                if (barco.registrarImpacto(celda)) {
                    barcosPlayerTwo.remove(barco);
                    if (barcosPlayerTwo.isEmpty()) {
                        return 'W';
                    } else {
                        return 'S';
                    }
                }
            }
        }else if(player.equals("two")){
            for (Barco barco : barcosPlayerOne) {

                if (barco.registrarImpacto(celda)) {
                    barcosPlayerOne.remove(barco);
                    if (barcosPlayerOne.isEmpty()) {
                        return 'W';
                    } else {
                        return 'S';
                    }
                }
            }
        }
        return 'M';
    }

}

