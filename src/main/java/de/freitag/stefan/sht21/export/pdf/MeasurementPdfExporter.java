package de.freitag.stefan.sht21.export.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * PDF exporter for measurements.
 */
public final class MeasurementPdfExporter extends AbstractPdfExporter {

    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOG = LogManager.getLogger(MeasurementPdfExporter.class.getCanonicalName());

    /**
     * The name of this exporter.
     */
    private static final String NAME = "PDF Exporter";
    /**
     * The description for this exporter.
     */
    private static final String DESCRIPTION = "Exports temperature and humidity measurements to a pdf file";


    /**
     * Create a new {@link MeasurementPdfExporter}.
     *
     * @param path The {@link Path} for the output file.
     */
    public MeasurementPdfExporter(final Path path) {
        super(NAME, DESCRIPTION, path);
    }

    /**
     * Pass the {@link Measurement} objects to export to the exporter.
     * The data items will be processed/ exported in order.
     *
     * @param measurements The {@link Measurement} objects to export.
     */
    @Override
    public void setData(final List<Measurement> measurements) {
        if (measurements == null) {
            throw new IllegalArgumentException("Measurements are null");
        }

        this.getDocument().open();

        final Map<MeasureType, List<Measurement>> map = splitMeasurements(measurements);
        if (!map.get(MeasureType.HUMIDITY).isEmpty()) {
            final PdfPTable table = createPdfTable(map.get(MeasureType.HUMIDITY), MeasureType.HUMIDITY);
            try {
                this.getDocument().add(table);
            } catch (final DocumentException exception) {
                LOG.error(exception.getMessage(), exception);
            }
        }
        this.getDocument().newPage();
        if (!map.get(MeasureType.TEMPERATURE).isEmpty()) {
            final PdfPTable table = createPdfTable(map.get(MeasureType.TEMPERATURE), MeasureType.TEMPERATURE);
            try {
                this.getDocument().add(table);
            } catch (final DocumentException exception) {
                LOG.error(exception.getMessage(), exception);
            }
        }
        this.getDocument().close();
        export();

    }

    /**
     * Starts the process of exporting data.
     *
     * @return {@code true} if the export is successful, otherwise
     * {@code false} is returned.
     */
    @Override
    public boolean export() {
        return false;
    }

    private PdfPTable createPdfTable(final List<Measurement> measurements, final MeasureType measureType) {
        final PdfPTable table = new PdfPTable(2);
        PdfPCell cell = new PdfPCell(new Phrase("Date"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);


        cell = new PdfPCell(new Phrase("Measurement"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        table.setHeaderRows(1);
        table.setSplitLate(true);
        table.setSplitRows(true);
        for (Measurement measurement : measurements) {
            final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.getDefault());
            table.addCell(new Phrase(dateFormat.format(measurement.getCreatedAt())));
            table.addCell(new Phrase(String.valueOf(measurement.getValue())));
        }
        return table;

    }
}
