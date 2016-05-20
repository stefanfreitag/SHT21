package de.freitag.stefan.sht21.export.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Used for adding header and footer to pdf pages.
 */
public final class HeaderFooterPageEvent extends PdfPageEventHelper {

    /**
     * The {@link ResourceBundle} containing the localization information.
     */
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("de.freitag.stefan.sht21.export.pdf.HeaderFooterPageEvent", Locale.getDefault());


    public void onStartPage(final PdfWriter writer, final Document document) {
        if (writer == null) {
            throw new IllegalArgumentException("PdfWriter is null");
        }
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(BUNDLE.getString("TITLE")), 280, 810, 0);
    }

    public void onEndPage(final PdfWriter writer, final Document document) {
        if (writer == null) {
            throw new IllegalArgumentException("PdfWriter is null");
        }
        final Date createdAt = new Date();
        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                DateFormat.MEDIUM, Locale.getDefault());
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Erzeugt um: " + dateFormat.format(createdAt)), 110, 20, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Seite " + document.getPageNumber()), 550, 20, 0);
    }

}
