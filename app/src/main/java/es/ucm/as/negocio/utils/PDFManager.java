package es.ucm.as.negocio.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.ucm.as.R;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.vista.main.Manager;

public class PDFManager {

    private final static String NOMBRE_DOCUMENTO = "Informe.pdf";
    private final static String NOMBRE_DIRECTORIO = "AS";

    public static File crearFichero(String nombreFichero){
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }


    public static File getRuta() {
        // El fichero será almacenado en un directorio dentro del directorio Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        }
        return ruta;
    }


    public static String generarPDF(TransferUsuario usuario, TransferReto reto,
                                    ArrayList<TransferTarea> tareas){
        Document document = new Document();
        File f = crearFichero(NOMBRE_DOCUMENTO);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(f.getAbsolutePath()));
            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            document.add(new Paragraph("\n", paragraphFont));
            document.add(new Paragraph("Informe AS", titleFont));
            document.add(new Paragraph(usuario.getNombre(), paragraphFont));
            document.add(new Paragraph("\n", paragraphFont));


            // Insertamos el logo
            Bitmap bitmap = BitmapFactory.decodeResource(Manager.getInstance().getContext().getResources(), R.drawable.logo_registro);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            Image imagen = null;
            imagen = Image.getInstance(stream.toByteArray());
            imagen.scaleToFit(75f, 75f);
            imagen.setAbsolutePosition(480f, 730f);
            document.add(imagen);


            // Mostramos la puntuacion anterior y la actual para comparar el progreso
            document.add(new Paragraph("Puntuacion", chapterFont));
            document.add(new Paragraph("\n", paragraphFont));
            PdfPTable table = new PdfPTable(2);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Anterior\n" + usuario.getPuntuacionAnterior() );
            table.addCell("Actual\n" + usuario.getPuntuacion());
            document.add(table);
            document.add(new Paragraph("\n", paragraphFont));

            // Mostramos el reto
            document.add(new Paragraph("Reto", chapterFont));
            document.add(new Paragraph("\n", paragraphFont));
            if(reto.getTexto() != null) {
                document.add(new Paragraph("Texto: " + reto.getTexto(), paragraphFont));
                if (reto.getPremio() != null)
                    document.add(new Paragraph("Premio: " + reto.getPremio(), paragraphFont));
                document.add(new Paragraph("Contador: " + reto.getContador().toString() + "/30", paragraphFont));
                if (reto.getSuperado())
                    document.add(new Paragraph("Superado", paragraphFont));
                else
                    document.add(new Paragraph("No superado", paragraphFont));
            }else
                document.add(new Paragraph("No tiene ningún reto asignado\n", paragraphFont));
            document.add(new Paragraph("\n", paragraphFont));


            // Insertamos una tabla con las tareas y sus puntuaciones.
            document.add(new Paragraph("Tareas", chapterFont));
            document.add(new Paragraph("\n", paragraphFont));
            float[] columnWidths = {1, 5, 1, 1, 1};
            PdfPTable tabla = new PdfPTable(columnWidths);
            tabla.setWidthPercentage(100);
            tabla.getDefaultCell().setUseAscender(true);
            tabla.getDefaultCell().setUseDescender(true);

            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            PdfPCell cell1 = new PdfPCell(new Phrase("Nº", font));
            PdfPCell cell2 = new PdfPCell(new Phrase("Tarea", font));
            PdfPCell cell3 = new PdfPCell(new Phrase("Si", font));
            PdfPCell cell4 = new PdfPCell(new Phrase("No", font));
            PdfPCell cell5 = new PdfPCell(new Phrase("Total", font));
            cell1.setBackgroundColor(BaseColor.BLUE);
            cell2.setBackgroundColor(BaseColor.BLUE);
            cell3.setBackgroundColor(BaseColor.BLUE);
            cell4.setBackgroundColor(BaseColor.BLUE);
            cell5.setBackgroundColor(BaseColor.BLUE);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell1);
            tabla.addCell(cell2);
            tabla.addCell(cell3);
            tabla.addCell(cell4);
            tabla.addCell(cell5);

            tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);

            if(tareas.size() >= 1 && tareas.get(0).getTextoAlarma()!=null)
                for (Integer i = 0; i < tareas.size(); i++) {
                    TransferTarea tarea = tareas.get(i);
                    tabla.addCell(i.toString());
                    tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                    tabla.addCell(tarea.getTextoAlarma());
                    tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabla.addCell(tarea.getNumSi().toString());
                    tabla.addCell(tarea.getNumNo().toString());
                    Integer total = tarea.getNumSi() - tarea.getNumNo();
                    tabla.addCell(total.toString());
                }

            document.add(tabla);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();
        return f.getAbsolutePath();
    }
}
