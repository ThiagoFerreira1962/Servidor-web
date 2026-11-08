package presentacion;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class Imagenes {

    private static final String CARPETA = "src/img";

    public static ImageIcon cargar(String nombreArchivo, String porDefecto, int ancho, int alto) {
        File f = new File(CARPETA, (nombreArchivo == null || nombreArchivo.isBlank()) ? porDefecto : nombreArchivo);
        if (!f.exists()) {
            f = new File(CARPETA, porDefecto);
        }
        if (!f.exists()) {
            return null;
        }
        Image escalada = new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }
}