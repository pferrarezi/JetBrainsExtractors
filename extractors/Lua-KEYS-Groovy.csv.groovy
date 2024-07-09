/*
 * JetBrains DataGrip Lua-KEYS Extractor add-on.
 * Extractor for the JetBrains DataGrip IDE (Groovy add-on)
 * Place it in the folder: Extensions> Database Tools and SQL> data> extractors.
 * The snipet creates a Lua Table with the SQL query header keys when clicking "copy" with the right mouse button in the IDE.
 *
 * Available context bindings:
 *   COLUMNS     List<DataColumn>
 *   ROWS        Iterable<DataRow>
 *   OUT         { append() }
 *   FORMATTER   { format(row, col); formatValue(Object, col); getTypeName(Object, col); isStringLiteral(Object, col); }
 *   TRANSPOSED  Boolean
 * plus ALL_COLUMNS, TABLE, DIALECT
 *
 * where:
 *   DataRow     { rowNumber(); first(); last(); data(): List<Object>; value(column): Object }
 *   DataColumn  { columnNumber(), name() }
 */

SEP = "', '"
QUOTE     = "\'"
STRING_PREFIX = DIALECT.getDbms().isMicrosoft() ? "N" : ""
NEWLINE   = System.getProperty("line.separator")

KEYWORDS_LOWERCASE = com.intellij.database.util.DbSqlUtil.areKeywordsLowerCase(PROJECT)
KW_NULL = KEYWORDS_LOWERCASE ? "null" : "NULL"

def record(columns, dataRow) {    
    if (TABLE == null) OUT.append("table_fields = ")
    else OUT.append(TABLE.getParent().getName()).append(".").append(TABLE.getName())
    OUT.append(" {'")

    columns.eachWithIndex { column, idx ->
        OUT.append(column.name()).append(idx != columns.size() - 1 ? SEP : "")
    } 
    OUT.append("'}").append(NEWLINE)
}

record(COLUMNS, ROWS.first())
