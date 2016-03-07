package de.freitag.stefan.sht21.export.excel;

/**
 * Contains the core, extended and some custom excel properties.
 */
final class ExcelDocumentProperties {

    /**
     * The core properties.
     */
    public static class Core {
        /**
         * The document creator.
         */
        private static final String CREATOR = "SHT21 Exporter";
        /**
         * The document description.
         */
        private static final String DESCRIPTION = "SHT21 Measurements Data Export";
        /**
         * The document keywords.
         */
        private static final String KEYWORDS = "SHT21, Export, Measurements, Temperature, Humidity";
        /**
         * The title of the document.
         */
        private static final String TITLE = "Data Export";
        /**
         * The subject of this document.
         */
        private static final String SUBJECT = "";

        /**
         * The category for this document.
         */
        private static final String CATEGORY = "Data Export";

        public static String getCreator() {
            return CREATOR;
        }

        public static String getDescription() {
            return DESCRIPTION;
        }

        public static String getKeywords() {
            return KEYWORDS;
        }

        public static String getTitle() {
            return TITLE;
        }

        public static String getSubject() {
            return SUBJECT;
        }

        public static String getCategory() {
            return CATEGORY;
        }
    }

    /**
     * The extended properties.
     */
    public static class Extended {
        private static final String COMPANY = "not available";

        public static String getCompany() {
            return COMPANY;
        }
    }

    /**
     * The custom properties.
     */
    public static class Custom {
        private static final String AUTHOR = "Stefan Freitag";
        private static final int YEAR = 2016;
        private static final boolean PUBLISHED = false;

        public static String getAuthor() {
            return AUTHOR;
        }

        public static int getYear() {
            return YEAR;
        }

        public static boolean isPublished() {
            return PUBLISHED;
        }
    }


}
