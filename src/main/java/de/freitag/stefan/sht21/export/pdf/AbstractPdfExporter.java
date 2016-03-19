package de.freitag.stefan.sht21.export.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import de.freitag.stefan.sht21.export.AbstractExporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by stefan on 08.03.16.
 */
public abstract class AbstractPdfExporter extends AbstractExporter {

    public static final String RESULT
            = "hello_letter.pdf";

    private final Document document;

    private PdfWriter writer;

    /**
     * The {@link Logger} for this class.
     */
    private Logger LOG = LogManager.getLogger(AbstractPdfExporter.class.getCanonicalName());

    /**
     * Create a new {@link AbstractExporter}
     *
     * @param name        The non-null and non-empty name of the exporter.
     * @param description The non-null description of the exporter.
     */
    protected AbstractPdfExporter(final String name, final String description) {
        super(name, description);
        this.document = new Document(PageSize.A4);
        try {
            this.writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            this.writer.setPageEvent(event);
        } catch (final DocumentException exception) {
            exception.printStackTrace();
        } catch (final FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    protected Document getDocument() {
        return this.document;
    }

    protected PdfWriter getWriter() {
        return this.writer;
    }
}
