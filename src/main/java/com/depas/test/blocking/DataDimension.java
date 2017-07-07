package com.depas.test.blocking;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public enum DataDimension {

    TIMESLICE(0,"Time Slice",null,false,false,null,"IEDX"),

    SQL(1,"SQL Statement","CON_SQL_NAME",false,true,"HASH","IZHO","CON_SQL","SQLHASH","CON_EXCLUDED_SQL","SQLHASH"),
    WAIT(2,"Wait","CONEV",true,false,"ID","KEEQ","CON_EVENT","EVENTID"),
    PROGRAM(3,"Program","CONPR",true,true,"ID","UDPW","CON_PROGRAM","PROGRAMID"),
    MACHINE(4,"Machine","CONM",true,true,"ID","PWMY","CON_MACHINE","MACHINEID"),
    OSUSER(5,"O/S User","CONO",true,true,"ID","IXOY","CON_OSUSER","OSUSERID"),
    DBUSER(6,"Database User","CONU",true,true,"ID","XCUW","CON_DBUSER","DBUSERID"),
    SESSION(7,"Session",null,false,false,null,"VDSI",true),
    DATABASE_INSTANCE(9,"Database",null,false,false,null,null),

    // For DB2 monitoring, partitions are stored in Oracle osuser tables/fields
    PARTITION(15,"Partition","CONO",true,true,"ID","IXOY","CON_OSUSER","OSUSERID"),

    // For Oracle Monitoring
    PLAN(16,"Plan",null,false,false,null,"ORPH","CON_PLAN","PLANHASH"),
    OBJECT(17,"Object","CONOBJ",true,true,"ID","HGOB","CON_OBJECT","OBJECTID"),
    MODULE(11,"Module","CONMOD",true,true,"ID","DBML","CON_MODULE","MODULEID"),
    ACTION(12,"Action","CONACT",true,true,"ID","RMAL","CON_ACTION","ACTIONID"),

    // Oracle and SQL Server
    FILE(13,"File","CONF",true,true,"ID","CTFL","CON_FILE","FILEID"),

    // SQL Server and Oracle: Wait Time data derived from file wait time and file/drive mapping
    DRIVE(40,"Drive","CONF_DRIVE",true,true,"ID",FILE,"CONF_DRIVE_MAP","FILEID","DRIVEID"),

    // SQL Server
    DEADLOCK(50,"Deadlock","CON_DEADLOCK",true,true,null,"ID",null,null),

    // For Sybase monitoring, procedures stored in Oracle module tables/fields
    PROCEDURE(21,"Procedure","CONMOD",true,true,"ID","DBML","CON_MODULE","MODULEID"),

    // For MySQL
    OPERATION(24,"Operation","CONMOD",true,true,"ID","DBML","CON_MODULE","MODULEID"),
    WAIT_INSTRUMENT(25,"Wait Instrument","CONACT",true,true,"ID","RMAL","CON_ACTION","ACTIONID"),

    // For SQL Server/Sybase monitoring, databases are stored in Oracle osuser tables/fields
    // For DB2, databases (when monitoring instance-wide) are stored in Oracle module tables/fields
    // Database Types: 0=Oracle, 1=SQL Server, 2=DB2, 3=Sybase, 4=MySQL
    DATABASE(10,
    		 "Database",
    		 new String[]{"CONO","CONO","CONMOD","CONO","CONO"},
    		 true,
    		 true,
    		 "ID",
    		 new String[]{"IXOY","IXOY","DBML","IXOY","IXOY"},
    		 new String[]{"CON_OSUSER","CON_OSUSER","CON_MODULE","CON_OSUSER","CON_OSUSER"},
    		 new String[]{"OSUSERID","OSUSERID","MODULEID","OSUSERID","OSUSERID"}),


    TIME_PERIOD(8,"Time Period",null,false,false,null,null),
    HOUR_OF_DAY(14,"Hour of Day",null,false,false,null,null),

    WAIT_TYPE(20,"Wait Type",null,false,false,null,null),
    BLOCKING_SESSION(22,"Blocking Session",null,false,false,null,"BLER"),
    BLOCKING_SQL(23, "Blocking SQL", null, false, false, null, null),
    SQL_STATISTIC(30,"SQL Statistic",null,false,false,null,null);

	/**
	 * Table used to find name for database dimension by ID.
	 * Element index is by database-type ID.
	 */
    private String[] lookupTablePrefixes = new String[5];

    /** Indicates if name lookup table is specific to a monitored database */
    private boolean lookupTableIsDatabaseSpecific;

    /** ID column in the lookup table */
    private String lookupIdColumn;

    /** Indicates that name column in lookup table supports unicode (ie, NVARCHAR for multi-byte systems) */
    private boolean unicodeNameColumn;

	/**
	 * Column in CONSW used to store this dimension's data
	 * Element index is by database-type ID.
	 */
    private String[] dataColumns = new String[5];

    private boolean stringData = false;

	/**
	 * Summary table name (if applicable).
	 * Element index is by database-type ID.
	 */
    private String[] summaryTablePrefixes = new String[5];

	/**
	 * ID column in summary table (if applicable).
	 * Element index is by database-type ID.
	 */
    private String[] summaryTableIDColumns = new String[5];

    /**
     * Global (repository-wide) exclusion table name (if applicable).
     * Element index is by database-type ID.
     */
    private String[] globalExclusionTableNames = new String[5];

    /**
     * ID column in global exclusion table (if applicable).
     * Element index is by database-type ID.
     */
    private String[] globalExclusionTableIDColumns = new String[5];

    /**
     * Dimension where data is derived from
     * Example: Drive wait time aggregrated from file data
     */
    private DataDimension baseDimension;

    // for aggregate dimensions (e.g, DRIVE)
    private String dimensionMappingTable;
    private String baseDimensionMappingTableColumn;
    private String aggregateDimensionMappingTableColumn;

    private int id;
    private String description;

    // Used for dimensions that use different columns/tables between database types
    private DataDimension(int id, String description,
    		                  String[] lookupTablePrefixes, boolean dbSpecificLookup,
    		                  boolean unicodeNameColumn, String lookupIdColumn,
    		                  String[] dataColumns,
    		                  String[] summaryTablePrefixes, String[] summaryTableIDCols) {

        this.id = id;
        this.description = description;
        this.lookupTablePrefixes = lookupTablePrefixes;
        this.lookupTableIsDatabaseSpecific = dbSpecificLookup;
        this.unicodeNameColumn = unicodeNameColumn;
        this.lookupIdColumn = lookupIdColumn;
        this.dataColumns = dataColumns;
        this.summaryTablePrefixes = summaryTablePrefixes;
        this.summaryTableIDColumns = summaryTableIDCols;
    }

    private DataDimension(int id, String description, String lookupTablePrefix, boolean dbSpecificLookup,
    		                  boolean unicodeNameColumn, String lookupIdColumn, String dataColumn,
    		                  String summaryTablePrefix, String summaryTableIDCol) {
    	this(id, description, lookupTablePrefix, dbSpecificLookup, unicodeNameColumn, lookupIdColumn, dataColumn, summaryTablePrefix, summaryTableIDCol, null, null);
    }

    private DataDimension(int id, String description, String lookupTablePrefix, boolean dbSpecificLookup,
            boolean unicodeNameColumn, String lookupIdColumn, String dataColumn,
            String summaryTablePrefix, String summaryTableIDCol,
            String globalExclusionTableName, String globalExclusionTableIDCol) {
    	this.id = id;
        this.description = description;
        Arrays.fill(this.lookupTablePrefixes,lookupTablePrefix);
        this.lookupTableIsDatabaseSpecific = dbSpecificLookup;
        this.unicodeNameColumn = unicodeNameColumn;
        this.lookupIdColumn = lookupIdColumn;
        Arrays.fill(this.dataColumns,dataColumn);
        Arrays.fill(this.summaryTablePrefixes,summaryTablePrefix);
        Arrays.fill(this.summaryTableIDColumns,summaryTableIDCol);
        Arrays.fill(this.globalExclusionTableNames,globalExclusionTableName);
        Arrays.fill(this.globalExclusionTableIDColumns,globalExclusionTableIDCol);
    }

    private DataDimension(int id, String description, String lookupTablePrefix, boolean dbSpecificLookup,
    		                  boolean unicodeNameColumn, String lookupIdColumn, String dataColumn) {
        this.id = id;
        this.description = description;
        Arrays.fill(this.lookupTablePrefixes,lookupTablePrefix);
        this.lookupTableIsDatabaseSpecific = dbSpecificLookup;
        this.unicodeNameColumn = unicodeNameColumn;
        this.lookupIdColumn = lookupIdColumn;
        Arrays.fill(this.dataColumns,dataColumn);
    }

    private DataDimension(int id, String description, String lookupTablePrefix, boolean dbSpecificLookup,
   		                  boolean unicodeNameColumn, String lookupIdColumn, String dataColumn, boolean isStringData) {
        this.id = id;
        this.description = description;
        Arrays.fill(this.lookupTablePrefixes,lookupTablePrefix);
        this.lookupTableIsDatabaseSpecific = dbSpecificLookup;
        this.unicodeNameColumn = unicodeNameColumn;
        this.lookupIdColumn = lookupIdColumn;
        Arrays.fill(this.dataColumns,dataColumn);
        this.stringData = isStringData;
    }

    private DataDimension(int id, String description, String lookupTablePrefix, boolean dbSpecificLookup,
    		              boolean unicodeNameColumn, String lookupIdColumn, DataDimension baseDimension,
    		              String mappingTable, String baseDimensionColumn, String aggregateDimensionColumn) {
        this.id = id;
        this.description = description;
        Arrays.fill(this.lookupTablePrefixes,lookupTablePrefix);
        this.lookupTableIsDatabaseSpecific = dbSpecificLookup;
        this.unicodeNameColumn = unicodeNameColumn;
        this.lookupIdColumn = lookupIdColumn;
        this.baseDimension = baseDimension;
        this.dimensionMappingTable = mappingTable;
        this.baseDimensionMappingTableColumn = baseDimensionColumn;
        this.aggregateDimensionMappingTableColumn = aggregateDimensionColumn;
    }

    public int toInt() {
        return id;
    }

    public int getInt() {
        return id;
    }
    public int getId() {
    	return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
	public String toString() {
    	return description;
    }

    /**
     * Returns true if dimension is not one that is actually collected
     */
    public boolean isVirtualDimension() {
    	return (this==TIMESLICE ||
    			this==TIME_PERIOD ||
    			this==DATABASE_INSTANCE ||
    			this==HOUR_OF_DAY ||
    			this==BLOCKING_SESSION ||
    			this==WAIT_TYPE ||
    			this==SQL_STATISTIC);
    }

    public boolean isUnicodeNameColumn() {
    	return unicodeNameColumn;
    }


    public String getDataColumn(DatabaseType databaseType) {
   		if (databaseType==null || databaseType==DatabaseType.UNKNOWN) {
   			throw new IllegalArgumentException("DatabaseType argument cannot be null or 'UNKNOWN' for Dimension [" + this + "]");
   		}
    	return dataColumns[databaseType.getId()];
    }

    public boolean isStringData() {
    	return stringData;
    }

    public boolean hasLookupTable(DatabaseType databaseType) {
   		if (databaseType==null || databaseType==DatabaseType.UNKNOWN) {
   			throw new IllegalArgumentException("DatabaseType argument cannot be null or 'UNKNOWN' for Dimension [" + this + "]");
   		}
   		return (lookupTablePrefixes[databaseType.getId()]!=null);
    }

    public boolean isNamedDimension(DatabaseType databaseType) {
   		if (databaseType==null || databaseType==DatabaseType.UNKNOWN) {
   			throw new IllegalArgumentException("DatabaseType argument cannot be null or 'UNKNOWN' for Dimension [" + this + "]");
   		}
   		return (lookupTablePrefixes[databaseType.getId()]!=null);
    }

    public String getLookupTable(String databaseId, DatabaseType databaseType) {
   		if (databaseType==null || databaseType==DatabaseType.UNKNOWN) {
   			throw new IllegalArgumentException("DatabaseType argument cannot be null or 'UNKNOWN' for Dimension [" + this + "]");
   		}
    	String lookupTable = lookupTablePrefixes[databaseType.getId()];
    	if (lookupTable!=null) {
    		if (lookupTableIsDatabaseSpecific) {
    	   		if (StringUtils.isEmpty(databaseId)) {
    	   			throw new IllegalArgumentException("DatabaseID argument cannot be null or emtpy for Dimension [" + this + "]");
    	   		}
    			return lookupTable + "_" + databaseId;
    		} else {
    			return lookupTable;
    		}
    	}
        return null;
    }

    public String getLookupIdColumn() {
        return lookupIdColumn;
    }

    public boolean hasSummaryData(DatabaseType databaseType) {
   		if (databaseType==null || databaseType==DatabaseType.UNKNOWN) {
   			throw new IllegalArgumentException("DatabaseType argument cannot be null or 'UNKNOWN' for Dimension [" + this + "]");
   		}
   		// drive data is summarized through file wait time
   		if (isAggregateDimension()) {
   			return (baseDimension.summaryTablePrefixes[databaseType.getId()]!=null);
   		} else {
   			return (summaryTablePrefixes[databaseType.getId()]!=null);
   		}
    }

    public String getSummaryTablePrefix(DatabaseType databaseType) {
   		if (databaseType==null || databaseType==DatabaseType.UNKNOWN) {
   			throw new IllegalArgumentException("DatabaseType argument cannot be null or 'UNKNOWN' for Dimension [" + this + "]");
   		}
    	return summaryTablePrefixes[databaseType.getId()];
    }

	public String getHourlySummaryTableName(String databaseId, DatabaseType databaseType) {
		return getSummaryTablePrefix(databaseType) + "_SUM_" + databaseId;
	}

    public String getTenMinuteSummaryTableName(String databaseId, DatabaseType databaseType) {
        return getSummaryTablePrefix(databaseType) + "_TEN_MINUTE_" + databaseId;
    }

	public String getSummaryTableIDColumn(DatabaseType databaseType) {
   		if (databaseType==null || databaseType==DatabaseType.UNKNOWN) {
   			throw new IllegalArgumentException("DatabaseType argument cannot be null or 'UNKNOWN' for Dimension [" + this + "]");
   		}
		return summaryTableIDColumns[databaseType.getId()];
	}

	public boolean hasGlobalExclusionTable(DatabaseType databaseType) {
   		if (databaseType==null || databaseType==DatabaseType.UNKNOWN) {
   			throw new IllegalArgumentException("DatabaseType argument cannot be null or 'UNKNOWN' for Dimension [" + this + "]");
   		}
		return (globalExclusionTableNames[databaseType.getId()]!=null);
    }

	public String getGlobalExclusionTableName(DatabaseType databaseType) {
		if (databaseType==null || databaseType==DatabaseType.UNKNOWN) {
   			throw new IllegalArgumentException("DatabaseType argument cannot be null or 'UNKNOWN' for Dimension [" + this + "]");
   		}
    	return globalExclusionTableNames[databaseType.getId()];
    }

	public String getGlobalExclusionTableIDColumn(DatabaseType databaseType) {
   		if (databaseType==null || databaseType==DatabaseType.UNKNOWN) {
   			throw new IllegalArgumentException("DatabaseType argument cannot be null or 'UNKNOWN' for Dimension [" + this + "]");
   		}
		return globalExclusionTableIDColumns[databaseType.getId()];
	}

	/**
	 * Returns true if data for this dimension is aggregated
	 * from another dimension (e.g., Drive data aggregated from File Data)
	 */
	public boolean isAggregateDimension() {
		return (baseDimension!=null);
	}

	public DataDimension getBaseDimensionForAggregrate() {
		return baseDimension;
	}

	public String getAggregateDimensionMappingTable(String databaseId) {
		if (isAggregateDimension()) {
			return dimensionMappingTable + "_" + databaseId;
		} else {
			return null;
		}
	}

	public String getBaseDimensionMappingTableColumn() {
		return baseDimensionMappingTableColumn;
	}

	public String getAggregateDimensionMappingTableColumn() {
		return aggregateDimensionMappingTableColumn;
	}

	public boolean hasExcludedIds() {
		return (this==PLAN || this==OBJECT || this==SQL);
	}

	public String getExcludedIdClause() {
		if (this==SQL) 		return " > -10";
		if (this==PLAN) 	return " > 0 ";
		if (this==OBJECT)	return " > 0 ";
		return null;
	}

	public boolean isExcludedId(String id) {
		if (this==PLAN || this==OBJECT) {
			if (id==null || StringUtils.isLong(id)==false) return false;
			return (Long.parseLong(id)<=0);
		}
		if (this==SQL) {
			if (id==null || StringUtils.isLong(id)==false) return false;
			return (Long.parseLong(id)<=-10);
		}
		return false;
	}

	public boolean isNoItemName(String name) {
		if (StringUtils.isEmpty(name)) return true;

		if (this==OBJECT) {
			return name.equals("No object specified");
		}
		else if (this==PROGRAM || this==MACHINE || this==OSUSER) {
			return (name.equals("Not Set by Session") ||
					name.equals("NULL - may be DBMS_JOB") ||
					name.equals("NULL_-_may_be_DBMS_JOB"));
		}
		else if (this==MODULE || this==ACTION) {
			return (name.equals("Not Set by Session"));
		}
		return false;
	}

	public boolean isSummarizedForBlocking() {
		try {
			getBlockingDimensionType();
		} catch( IllegalArgumentException iae ) {
			return false;
		}
		return true;
	}

	public BlockingDimensionType getBlockingDimensionType() {
		BlockingDimensionType bType = null;
		switch( this ) {
			case SQL:
				bType = BlockingDimensionType.SQL;
				break;
			case WAIT:
				bType = BlockingDimensionType.EVENT;
				break;
			case PROGRAM:
				bType = BlockingDimensionType.PROGRAM;
				break;
			case MACHINE:
				bType = BlockingDimensionType.MACHINE;
				break;
			case DBUSER:
				bType = BlockingDimensionType.DBUSER;
				break;
			case DATABASE:
			case OSUSER:
				bType = BlockingDimensionType.OSUSER;
				break;
			case PLAN:
				bType = BlockingDimensionType.PLAN;
				break;
			case OBJECT:
				bType = BlockingDimensionType.OBJECT;
				break;
			case MODULE:
			case OPERATION:
			case PROCEDURE:
				bType = BlockingDimensionType.MODULE;
				break;
			case ACTION:
				bType = BlockingDimensionType.ACTION;
				break;
			case FILE:
				bType = BlockingDimensionType.FILE;
				break;
			case WAIT_INSTRUMENT:
				bType = BlockingDimensionType.ACTION;
				break;
/*
			case IDLE_BLOCKER:
				bType = BlockingDimensionType.IDLE_BLOCKER;
				break;
*/
			default:
				throw new IllegalArgumentException("Could not find a BlockingDimensionType where DataDimension=" + this.toString());
		}
		return bType;
	}

	private static final Map<Integer,DataDimension> DIMENSION_MAP = new HashMap<Integer,DataDimension>();

	static {
		for (DataDimension dimension : DataDimension.values()) {
			// check for duplicate IDs
	        Integer key = Integer.valueOf(dimension.getId());
	        if (DIMENSION_MAP.containsKey(key)) {
	        	throw new IllegalStateException("Duplicate ID for DatabaseDimension [id=" + key + ", dimension=" + dimension + "]");
	        }
	        DIMENSION_MAP.put(key,dimension);
		}
	}

    public static DataDimension fromString(String id) {

        try {
            return fromInt(Integer.parseInt(id));
        } catch (Exception ex) {}
        return null;
    }

    public static DataDimension fromInt(int id) {
        try {
            return DIMENSION_MAP.get(id);
        } catch (Exception ex) {}
        return null;
    }

    public static void main(String[] args) {

    	DataDimension[] dimensions = values();
    	for (DatabaseType dbType : DatabaseType.values()) {
    		if (dbType==DatabaseType.UNKNOWN) continue;

    		System.out.println(dbType);
    		for (DataDimension d : dimensions) {
    			String dataCol = d.getDataColumn(dbType);
    			if (dataCol!=null) {
    				System.out.println("\t" + d + "\t" + dataCol);
    			}
    		}
    	}
    }
}
