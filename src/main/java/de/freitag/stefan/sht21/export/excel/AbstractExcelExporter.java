package de.freitag.stefan.sht21.export.excel;

import de.freitag.stefan.sht21.export.AbstractExporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.POIXMLProperties;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Base class for all exporters with Excel output
 */
abstract class AbstractExcelExporter extends AbstractExporter {

    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOG = LogManager.getLogger(AbstractExcelExporter.class.getCanonicalName());
    /**
     * The {@link ResourceBundle} containing the localization information.
     */
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("de.freitag.stefan.sht21.export.AbstractExcelExporter", Locale.getDefault());
    /**
     * The Excel workbook to fill with the data to export.
     */
    private final XSSFWorkbook workbook;

    /**
     * Create a new {@link AbstractExcelExporter}.
     *
     * @param name        The non-null and non-empty name of the exporter.
     * @param description The non-null description of the exporter.
     * @param path        The {@link Path} for the output file.
     */
    AbstractExcelExporter(final String name, final String description, final Path path) {
        super(name, description, path);
        this.workbook = new XSSFWorkbook();
        setProperties(this.workbook.getProperties());
    }

    /**
     * Set the core properties values in {@code properties}.
     *
     * @param properties Wrapper around the two different kinds of OOXML properties
     *                   a document can have.
     */
    private static void setCoreProperties(final POIXMLProperties properties) {
        assert properties != null;
        final POIXMLProperties.CoreProperties coreProperties = properties.getCoreProperties();
        coreProperties.setCreator(ExcelDocumentProperties.Core.getCreator());
        coreProperties.setDescription(ExcelDocumentProperties.Core.getDescription());
        coreProperties.setKeywords(ExcelDocumentProperties.Core.getKeywords());
        coreProperties.setTitle(ExcelDocumentProperties.Core.getTitle());
        coreProperties.setSubjectProperty(ExcelDocumentProperties.Core.getSubject());
        coreProperties.setCategory(ExcelDocumentProperties.Core.getCategory());
    }

    /**
     * Set the extended properties values in {@code properties}.
     *
     * @param properties Wrapper around the two different kinds of OOXML properties
     *                   a document can have.
     */
    private static void setExtendedProperties(final POIXMLProperties properties) {
        assert properties != null;
        final POIXMLProperties.ExtendedProperties extendedProperties = properties.getExtendedProperties();
        extendedProperties.getUnderlyingProperties().setCompany(ExcelDocumentProperties.Extended.getCompany());
    }

    private static void setCustomProperties(final POIXMLProperties properties) {
        assert properties != null;
        final POIXMLProperties.CustomProperties customProperties = properties.getCustomProperties();
        customProperties.addProperty("Author", ExcelDocumentProperties.Custom.getAuthor());
        customProperties.addProperty("Year", ExcelDocumentProperties.Custom.getYear());
        customProperties.addProperty("Published", ExcelDocumentProperties.Custom.isPublished());
    }

    /**
     * Add a new sheet with the given {@code name} to the workbook..
     * If the {@code name} is not valid, a valid name close to the original one is created.
     *
     * @param name The name of the new sheet.
     * @return The created sheet.
     */
    Sheet addSheet(final String name) {
        assert name != null;
        final String safeName = WorkbookUtil.createSafeSheetName(name);
        return this.workbook.createSheet(safeName);
    }

    /**
     * Set column headers for the given sheet.
     *
     * @param sheet   The sheet to apply the column headers to.
     * @param headers Non-null list of column headers
     */
    void setColumnHeaders(final Sheet sheet, final List<String> headers) {
        assert sheet != null;
        assert headers != null;

        final Row row = sheet.createRow(0);
        for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
            final Cell cell = row.createCell(colIndex);
            cell.setCellValue(headers.get(colIndex));
        }
    }

    /**
     * Sets the {@link Header} for all workbook sheets.
     */
    void setSheetHeader() {
        final double numberOfSheets = this.workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            final Header header = this.workbook.getSheetAt(i).getHeader();
            header.setCenter(BUNDLE.getString("TITLE"));
        }
    }

    /**
     * Sets the {@link Footer} for all workbook sheets.
     */
    void createFooter() {
        final int numberOfSheets = this.workbook.getNumberOfSheets();
        final Date createdAt = new Date();
        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                DateFormat.MEDIUM, Locale.getDefault());
        for (int i = 0; i < numberOfSheets; i++) {
            final Footer footer = this.workbook.getSheetAt(i).getFooter();
            footer.setLeft(BUNDLE.getString("CREATED_AT") + ": " + dateFormat.format(createdAt));
            footer.setRight(BUNDLE.getString("SHEET") + " " + (i + 1) + " " + BUNDLE.getString("OF") + " " + numberOfSheets);
        }
    }

    @Override
    public boolean export() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(this.getFilename().toString());
            this.workbook.write(fos);
            return true;
        } catch (final IOException exception) {
            LOG.error(exception.getMessage(), exception);
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException exception) {
                    LOG.error(exception.getMessage(), exception);

                }
            }
        }

    }

    /**
     * Set the core, extended and custom properties values in {@code properties}.
     *
     * @param properties Wrapper around the two different kinds of OOXML properties
     *                   a document can have.
     */
    private void setProperties(final POIXMLProperties properties) {
        assert properties != null;
        setCoreProperties(properties);
        setExtendedProperties(properties);
        setCustomProperties(properties);
    }

    /**
     * Return the {@link XSSFWorkbook} containing the data.
     *
     * @return the {@link XSSFWorkbook}.
     */
    XSSFWorkbook getWorkbook() {
        return this.workbook;
    }
}
