import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;

import java.io.File;
import java.io.IOException;

public class MetadataEditor {
    public static void main(String[] args) {
        try {
            // Archivos de entrada y salida
            File originalFile = new File("ruta/a/tu/archivo-original.tiff");
            File editedFile = new File("ruta/a/tu/archivo-editado.tiff");

            // Cargar metadata del archivo TIFF
            ImageMetadata metadata = Imaging.getMetadata(originalFile);
            if (!(metadata instanceof TiffImageMetadata)) {
                throw new IllegalArgumentException("El archivo no es un TIFF válido.");
            }

            TiffImageMetadata tiffMetadata = (TiffImageMetadata) metadata;

            // Crear un TiffOutputSet para modificar los datos
            TiffOutputSet outputSet = tiffMetadata.getOutputSet();

            // Ejemplo: Agregar un campo de texto personalizado
            TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
            exifDirectory.add(TiffOutputDirectory.TagInfoAscii.create("Comentario"), "Editado con Commons Imaging");

            // Escribir los datos modificados en un nuevo archivo
            Imaging.writeImageToBytes(outputSet, originalFile, editedFile);

            System.out.println("Archivo TIFF editado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

