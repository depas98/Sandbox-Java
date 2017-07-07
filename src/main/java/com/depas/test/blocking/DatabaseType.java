package com.depas.test.blocking;

import java.util.Comparator;

public enum DatabaseType {

    UNKNOWN("U","Unknown", null, -1,false,false,false,false,-1,null,null,null),
    ORACLE("O","Oracle",null,0,true,true,false,false,new String[]{"O","E","B","Z"},1521,"select 1 from dual","com.confio.idc.database.validationQuery.oracle",";"),
    SQLSERVER("S","SQL Server","MS SQL",1,true,true,false,false,new String[]{"S","I","C","R"},1433,"select 1","com.confio.idc.database.validationQuery.sqlServer",";"),
    DB2("D","DB2",null,2,false,false,false,false,new String[]{"D","L"},50000,"select 1 from SYSIBM.SYSDUMMY1","com.confio.idc.database.validationQuery.db2",";"),
    SYBASE("Y","Sybase",null,3,false,false,false,false,new String[]{"Y","N"},5000,"select 1","com.confio.idc.database.validationQuery.sybaseServer",";"),
    MYSQL("M","MySQL",null,4,false,false,true,true,new String[]{"Y","N"},3306,"select 1","com.confio.idc.database.validationQuery.mysql","&");

    private int id;
    private transient String strValue;
    private transient String displayName;
    private transient boolean textPollRequired;
    private transient boolean planPollRequired;
    private transient boolean supportsSqlMapping;
    private transient boolean supportsSqlExamples;
    private String typeCode;
    private String[] codes;
    private int defaultPort;

    private String defaultValidatonQuery;
    private String validationQuerySystemProperty;
    private String propertySeparator;

    private DatabaseType(String typeCode, String strValue, String displayName, int id, boolean textPollRequired, boolean planPollRequired, boolean supportsSqlMapping, boolean supportsSqlExamples, int defaultPort, String defaultValidatonQuery, String validationQuerySystemProperty, String propertySeparator) {
    	this(typeCode,strValue,displayName,id,textPollRequired,planPollRequired,supportsSqlMapping,supportsSqlExamples,new String[0],defaultPort,defaultValidatonQuery,validationQuerySystemProperty,propertySeparator);
    }

    private DatabaseType(String typeCode, String strValue, String displayName, int id, boolean textPollRequired, boolean planPollRequired, boolean supportsSqlMapping, boolean supportsSqlExamples, String[] codes, int defaultPort, String defaultValidatonQuery, String validationQuerySystemProperty, String propertySeparator) {
    	this.typeCode = typeCode;
        this.strValue = strValue;
        this.displayName = displayName == null ? strValue : displayName;
        this.id = id;
        this.textPollRequired = textPollRequired;
        this.planPollRequired = planPollRequired;
        this.supportsSqlMapping = supportsSqlMapping;
        this.supportsSqlExamples = supportsSqlExamples;
        this.codes = codes;
        this.defaultPort = defaultPort;
        this.defaultValidatonQuery = defaultValidatonQuery;
        this.validationQuerySystemProperty = validationQuerySystemProperty;
        this.propertySeparator = propertySeparator;
    }

    public String getTypeCode() {
    	return typeCode;
    }

    public boolean isTextPollRequired() {
        return textPollRequired;
    }

    public boolean isPlanPollRequired() {
        return planPollRequired;
    }

    public boolean getSupportsSqlMapping() {
    	return supportsSqlMapping;
    }

    public boolean getSupportsSqlExamples() {
    	return supportsSqlExamples;
    }

    public boolean isIOStatsSupported() {
    	return (this==ORACLE || this==SQLSERVER);
    }

    public boolean isMissingIndexesSupported() {
    	return (this==ORACLE || this==SQLSERVER);
    }

	public boolean isDeadlockPollSupported() {
		return this==SQLSERVER;
	}

    public int getInt() {
    	return id;
    }
    public int getId() {
    	return id;
    }

    public boolean isValidCode(String code) {
    	for (int i=0; i<codes.length; i++) {
    		if (codes[i].equals(code)) {
    			return true;
    		}
    	}
    	return false;
    }

    public int getDefaultPort() {
    	return defaultPort;
    }

    public static DatabaseType fromInt(int dbTypeId) {
    	for (DatabaseType dbType : values()) {
    		if (dbTypeId==dbType.id) {
    			return dbType;
    		}
    	}
    	return UNKNOWN;
    }

    public String toString() {
        return strValue;
    }
    public String getString() {
    	return strValue;
    }
    public String getDisplayName() {
    	return displayName;
    }

    public String getDefaultValidatonQuery() {
		return defaultValidatonQuery;
	}

	public String getValidationQuerySystemProperty() {
		return validationQuerySystemProperty;
	}

	public String getPropertySeparator() {
		return propertySeparator;
	}

	/**
	 * Returns all types other than {@link DatabaseType#UNKNOWN}
	 */
	public static DatabaseType[] getValidTypes() {
		return new DatabaseType[]{ORACLE,SQLSERVER,DB2,SYBASE,MYSQL};
	}

	public static DatabaseType fromString(String dbTypeName) {
    	if ("oracle".equalsIgnoreCase(dbTypeName)) {
    		return ORACLE;
    	} else if ("sql server".equalsIgnoreCase(dbTypeName) || "sqlserver".equalsIgnoreCase(dbTypeName)) {
    		return SQLSERVER;
    	} else if ("db2".equalsIgnoreCase(dbTypeName)) {
    		return DB2;
    	} else if ("sybase".equalsIgnoreCase(dbTypeName)) {
    		return SYBASE;
    	} else if ("mysql".equalsIgnoreCase(dbTypeName)) {
    		return MYSQL;
    	} else {
    		return UNKNOWN;
    	}
    }

	/**
	 * Returns database type for given type code.
	 * @see #getTypeCode()
	 * @param typeCode Case-insenstive type code
	 * @throws IllegalArgumentException if type code does not correspond to a valid database type
	 */
	public static DatabaseType fromTypeCode(String typeCode) {

		for (DatabaseType type : values()) {
			if (type.getTypeCode().equalsIgnoreCase(typeCode)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown type code [" + typeCode + "]");

    }

	public int compareByName(DatabaseType other) {
		return this.getString().compareTo(other.getString());
	}

	public int compareByDisplayName(DatabaseType other) {
		return this.getDisplayName().compareTo(other.getDisplayName());
	}

	public static Comparator<DatabaseType> getDatabaseTypeNameComparator() {
		return new Comparator<DatabaseType>() {
			public int compare(DatabaseType type1, DatabaseType type2) {
				return type1.getString().compareTo(type2.getString());
			}
		};
	}


}
