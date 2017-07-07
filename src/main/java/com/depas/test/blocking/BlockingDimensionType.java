package com.depas.test.blocking;

public enum BlockingDimensionType {

	SQL 	('S',"CON_SQL_SUM","SQLHASH","IZHO"),
	EVENT 	('E',"CON_EVENT_SUM","EVENTID","KEEQ"),
	PROGRAM ('P',"CON_PROGRAM_SUM","PROGRAMID","UDPW"),
	MACHINE ('C',"CON_MACHINE_SUM","MACHINEID","PWMY"),
	DBUSER 	('D',"CON_DBUSER_SUM","DBUSERID","XCUW"),
	OSUSER 	('U',"CON_OSUSER_SUM","OSUSERID","IXOY"),
	PLAN 	('L',"CON_PLAN_SUM","PLANHASH","ORPH"),
	OBJECT 	('O',"CON_OBJECT_SUM","OBJECTID","HGOB"),
	MODULE 	('M',"CON_MODULE_SUM","MODULEID","DBML"),
	ACTION 	('A',"CON_ACTION_SUM","ACTIONID","RMAL"),
	FILE 	('F',"CON_FILE_SUM","FILEID","CTFL"),
	IDLE_BLOCKER ('I',"Idle Blocker Dummy Dimension",-90);

	private char key;
	private String tableName;
	private String idColumn;
	private String dataColumn;
	private int hashCode = Integer.MIN_VALUE;

	private BlockingDimensionType(char key,  String tableName, String idColumn, String dataColumn){
		this.key=key;
		this.tableName=tableName;
		this.idColumn=idColumn;
		this.dataColumn=dataColumn;
	}

	private BlockingDimensionType(char key,  String tableName, int hashCode){
		this.key=key;
		this.tableName=tableName;
		this.hashCode=hashCode;
	}

	public char getKey(){
		return key;
	}

	public String getSummaryTable(){
		return tableName;
	}

	public String getIdColumn(){
		return idColumn;
	}

	public long getHashCode() {
		if (this!=IDLE_BLOCKER){
			throw new UnsupportedOperationException("Only the Idle Blocker psuedo-dimension has an associated hashcode.");
		}
		return hashCode;
	}

	public String getTableName(int databaseId){
		if (this==IDLE_BLOCKER){
			throw new UnsupportedOperationException("Idle Blocker is a psuedo-dimension and doesn't have a table associated with it.");
		}

		StringBuilder sqlBuf = new StringBuilder(24);
        sqlBuf.append(tableName).append("_").append(databaseId);
        return sqlBuf.toString();
	}

	//  This is the column name in consw for this dimension
	public String getDataColumn(){
		if (this==IDLE_BLOCKER){
			throw new UnsupportedOperationException("Idle Blocker is a psuedo-dimension and doesn't have a data column associated with it.");
		}

        return dataColumn;
	}

    public static String getBlockingSummaryTableName() {
    	return "CON_BLOCKING_SUM";
    }

    public static String getSummaryTableDimIDColumnName() {
    	return "DIMENSIONID";
    }

    public static String getSummaryTableDimTypeColumnName() {
    	return "DIMENSIONTYPE";
    }

    public static String getBlockerWaitTimeColumnName() {
    	return "BLERTIMESECS";
    }

    public static String getBlockeeWaitTimeColumnName() {
    	return "BLEETIMESECS";
    }

    public static String getRootBlockerWaitTimeColumnName() {
    	return "ROOTBLERSECS";
    }

    public static String getRootBlockeeImpactTimeColumnName() {
    	return "ROOTIMPACTSECS";
    }

    public static BlockingDimensionType fromKey(char key) {
		for (BlockingDimensionType dimType : values()) {
			if (dimType.getKey() == key) {
				return dimType;
			}
		}
		throw new IllegalArgumentException("Unknown blocking dimension type key [" + key + "]");
    }
}
