import java.util.List;

class Barco {
    private List<String> celdas;
    private int impactos;

    public Barco(List<String> celdas) {
        this.celdas = celdas;
        this.impactos = 0;
    }

    public boolean registrarImpacto(String celda) {
        if (celdas.contains(celda)) {
            impactos++;
            if (impactos == celdas.size()) {
                return true; // Barco hundido
            }
        }
        return false; // Barco no hundido
    }

    public boolean estaHundido() {
        return impactos == celdas.size();
    }

    public List<String> getCeldas() {
        return celdas;
    }
}

