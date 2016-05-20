package de.freitag.stefan.sht21.export.excel;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import java.nio.file.Path;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * Exports {@link Measurement} to an Excel workbook. For each {@link MeasureType}
 * a separate sheet is created inside the workbook.
 */
public final class MeasurementExcelExporter extends AbstractExcelExporter {

    /**
     * The {@link Logger} for this class.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(MeasurementExcelExporter.class.getCanonicalName());
    /**
     * The name of this exporter.
     */
    private static final String NAME = "Excel Exporter";
    /**
     * The description for this exporter.
     */
    private static final String DESCRIPTION = "Exports temperature and humidity measurements to an Excel workbook";
    /**
     * The {@link ResourceBundle} containing the localization information.
     */
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("de.freitag.stefan.sht21.export.MeasurementExcelExporter", Locale.getDefault());

    /**
     * Create a new MeasurementExcelExporter.
     *
     * @param path The path of the output file.
     */
    public MeasurementExcelExporter(
            final Path path) {
        super(NAME, DESCRIPTION, path);
    }

    @Override
    public void setData(final List<Measurement> measurements) {
        if (measurements == null) {
            throw new IllegalArgumentException("Measurements are null");
        }

        final Map<MeasureType, List<Measurement>> map = splitMeasurements(measurements);
        if (!map.get(MeasureType.HUMIDITY).isEmpty()) {
            final Sheet sheet = this.addSheet(BUNDLE.getString("HUMIDITY"));
            addData(map.get(MeasureType.HUMIDITY), sheet);
        }
        if (!map.get(MeasureType.TEMPERATURE).isEmpty()) {
            final Sheet sheet = this.addSheet(BUNDLE.getString("TEMPERATURE"));
            addData(map.get(MeasureType.TEMPERATURE), sheet);
        }

        //TODO:
        setSheetHeader();
        final List<String> headers = setColumnHeaders();


        createFooter();
        drawLineChart();
        setAutoColumnSize(headers);


        export();
    }

    private List<String> setColumnHeaders() {
        final List<String> headers = new ArrayList<>();
        headers.add("Datum");
        headers.add("Messwert [%RH ]");
        setColumnHeaders(this.getWorkbook().getSheetAt(0), headers);

        final List<String> headers2 = new ArrayList<>();
        headers2.add("Datum");
        headers2.add("Messwert [°C ]");
        setColumnHeaders(this.getWorkbook().getSheetAt(1), headers2);
        return headers;
    }

    private void setAutoColumnSize(final List<String> headers) {
        //TODO: Fix usage of header length
        for (final Sheet sheet : this.getWorkbook()) {
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

        }
    }

    private void addData(final List<Measurement> measurements, final Sheet sheet) {
        assert measurements != null;
        assert sheet != null;
        final CreationHelper createHelper = this.getWorkbook().getCreationHelper();
        int i = 1;
        for (final Measurement measurement : measurements) {
            final Row row = sheet.createRow(i++);

            final Cell cell = row.createCell(0);
            final CellStyle cellStyle = this.getWorkbook().createCellStyle();
            final String datePattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
                    FormatStyle.MEDIUM, FormatStyle.MEDIUM, IsoChronology.INSTANCE,
                    Locale.getDefault());
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat(datePattern));
            cell.setCellStyle(cellStyle);
            cell.setCellValue(measurement.getCreatedAt());
            row.createCell(1).setCellValue(measurement.getValue());
        }
    }


    private void drawLineChart() {
        for (int i = 0; i < this.getWorkbook().getNumberOfSheets(); i++) {
            final Sheet sheet = this.getWorkbook().getSheetAt(i);
            final Drawing drawing = sheet.createDrawingPatriarch();

            final ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 15, 10, 25);
            final Chart chart = drawing.createChart(anchor);

            final ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
            final ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

            final ChartLegend legend = chart.getOrCreateLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);

            // -1 because of the header
            final ChartDataSource<Number> xSource = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, sheet.getPhysicalNumberOfRows() - 1, 0, 0));
            final ChartDataSource<Number> ySource = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, sheet.getPhysicalNumberOfRows() - 1, 1, 1));

            final LineChartData data = chart.getChartDataFactory().createLineChartData();
            final LineChartSeries series = data.addSeries(xSource, ySource);
            final CellReference ref = new CellReference(sheet.getSheetName(), 0, 1, true, true);
            series.setTitle(ref);
            chart.plot(data, bottomAxis, leftAxis);

        }

    }
}
