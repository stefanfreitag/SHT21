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
import java.nio.file.Path;

/**
 * Base class for PDF exporter
 */
public abstract class AbstractPdfExporter extends AbstractExporter {

    private static final String RESULT
            = "output.pdf";
    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOG = LogManager.getLogger(AbstractPdfExporter.class.getCanonicalName());
    private final Document document;
    private PdfWriter writer;

    /**
     * Create a new {@link AbstractExporter}
     *
     * @param name        The non-null and non-empty name of the exporter.
     * @param description The non-null description of the exporter.
     * @param path    The {@link Path} for the output file.
     */
    protected AbstractPdfExporter(final String name, final String description, final Path path) {
        super(name, description, path);
        this.document = new Document(PageSize.A4);
        try {
            this.writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
            final HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            this.writer.setPageEvent(event);
        } catch (final DocumentException | FileNotFoundException exception) {
            LOG.error(exception.getMessage(), exception);
        }
    }

    Document getDocument() {
        return this.document;
    }

    PdfWriter getWriter() {
        return this.writer;
    }
}
