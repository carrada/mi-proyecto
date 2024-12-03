package com.ejemplo;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.GpsDirectory;

import java.awt.Desktop;
import java.io.File;
import java.util.Scanner;

public class MetadataExtractor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

	System.out.println();
	System.out.println("Bienvenido al extractor de METADATOS");

        System.out.println("Ingrese la ruta de la imagen (JPG, JPEG, PNG): ");
        String filePath = scanner.nextLine();

        File imageFile = new File(filePath);

        // Verificar que el archivo exista y tenga un formato aceptado
        if (!imageFile.exists() || (!filePath.endsWith(".jpg") && !filePath.endsWith(".jpeg") && !filePath.endsWith(".png"))) {
            System.out.println("Archivo no encontrado o formato no compatible.");
            return;
        }

        try {
            // Leer los metadatos de la imagen
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);

            System.out.println("Metadatos encontrados:");
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.format("[%s] - %s = %s%n", directory.getName(), tag.getTagName(), tag.getDescription());
                }
            }

            // Obtener la ubicación GPS (si está disponible)
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            if (gpsDirectory != null && gpsDirectory.getGeoLocation() != null) {
                double latitude = gpsDirectory.getGeoLocation().getLatitude();
                double longitude = gpsDirectory.getGeoLocation().getLongitude();

                System.out.println("Coordenadas GPS encontradas:");
                System.out.println("Latitud: " + latitude);
                System.out.println("Longitud: " + longitude);

                // Abrir Google Maps con las coordenadas
                String googleMapsUrl = String.format("https://www.google.com/maps?q=%f,%f", latitude, longitude);
                System.out.println("Abriendo Google Maps en: " + googleMapsUrl);
                Desktop.getDesktop().browse(new java.net.URI(googleMapsUrl));
            } else {
                System.out.println("No se encontraron datos GPS en los metadatos.");
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error al leer los metadatos: " + e.getMessage());
        }

        scanner.close();
    }
}
