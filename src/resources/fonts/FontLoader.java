package resources.fonts;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class FontLoader {

    private static String croogla;

    public static void loadFonts() {
        final String Croogla = "Croogla4F.ttf";
        final String Helvetica = "Helvetica.ttf";
        final String HelveticaBold = "Helvetica-Bold.ttf";
        final String HelveticaBoldOblique = "Helvetica-BoldOblique.ttf";
        final String HelveticaCompressed = "helvetica-compressed-5871d14b6903a.otf";
        final String HelveticaLight = "helvetica-light-587ebe5a59211.ttf";
        final String HelveticaOblique = "Helvetica-Oblique.ttf";
        final String HelveticaRoundedBold = "helvetica-rounded-bold-5871d05ead8de.otf";

        try {
            String[] fontFiles = {Croogla,Helvetica,HelveticaBold,HelveticaBoldOblique
                    ,HelveticaCompressed,HelveticaLight,HelveticaOblique,HelveticaRoundedBold}; // Add paths to your font files

            for (String fontFile : fontFiles) {
                try (InputStream inputStream = FontLoader.class.getResourceAsStream(fontFile)) {
                    if (inputStream == null) {
                        JOptionPane.showMessageDialog(null, "Font file " + fontFile + " not found");
                        continue;
                    }
                    Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    ge.registerFont(font);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
