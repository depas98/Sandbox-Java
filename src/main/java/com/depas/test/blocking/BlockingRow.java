package com.depas.test.blocking;


import java.util.Collections;
import java.util.Map;

/**
 * Represents one active session at one point in time (quick poll row) that was causing
 * and/or experiencing wait time due to blocking.  Used by the summarizer to summarize 
 * Root Blockers dimension time into CON_BLOCKING_SUM_X table. 
 * 
 * This class can also set 
 * 	if this is a root blocker
 *  the root parent blocker (root blocker will be null)
 *  if it's involved in a deadlock (root blocker will be null)
 */
public class BlockingRow {
		
	// BLER
	private Long blockerId;
	// BLEE
	private Long waiterId;
			
	// Session Id
	private String sessionId;
	
	// Dimensions for Row (SQL,Wait,Program,etc
	private Map<BlockingDimensionType,Long> dimensionMap;
	
	// Wait time in seconds for this row
	private double waitTime;
	
	// is this row the Root Blocker
	private boolean isRootBlocker=false;

	// is this row involved in a deadlock
	private boolean isDeadlock=false;
	
	//the root blocker of this row, this will be null if this is a root blocker
	private Long rootBlockerId;
	
	// Is this row blocked by a Idle Blocker, blee is negative
	private boolean isIdleBlocker=false;
	
	public BlockingRow(Long blockerId, Long waiterId, Map<BlockingDimensionType,Long> dimensionMap, double waitTime){
		 this(blockerId, waiterId, dimensionMap, waitTime, null);
	}
	
	public BlockingRow(Long blockerId, Long waiterId, Map<BlockingDimensionType,Long> dimensionMap, double waitTime, String sessionId){
		this.blockerId = blockerId;
		this.waiterId = waiterId;
		this.dimensionMap = dimensionMap!=null ? Collections.unmodifiableMap(dimensionMap) : null;
		this.waitTime = waitTime;
		this.sessionId=sessionId;
	}
	
	public Long getBlockerId() {
		return blockerId;
	}

	public Long getWaiterId() {
		return waiterId;
	}

	public String getSessionId() {
		return sessionId;
	}
	
	public Map<BlockingDimensionType,Long> getDimensions() {
		return dimensionMap;
	}

	public boolean hasNegativeSQLHash(){
		if (dimensionMap==null){return false;}
		
		Long value = dimensionMap.get(BlockingDimensionType.SQL);
		if (value==null){
			return false;
		}
		return dimensionMap.get(BlockingDimensionType.SQL).longValue() <= -10L;
	}
	
	public double getWaitTime() {
		return waitTime;
	}
	
	/*
	 * This will be null if this is a Root Blocker
	 */
	public Long getRootBlockerId(){
		return rootBlockerId;
	}

	public void setRootBlockerId(Long rootBlockerId){
		this.rootBlockerId=rootBlockerId;
	}
	
	public boolean isRootBlocker(){
		return isRootBlocker;
	}

	public void setIsRootBlocker(boolean isRootBlocker){
		this.isRootBlocker=isRootBlocker;
	}
	
	public boolean isDeadlock(){
		return isDeadlock;
	}
	
	public void setIsDeadlock(boolean isDeadlock){
		this.isDeadlock=isDeadlock;
	}

	public boolean isIdleBlocker(){
		return isIdleBlocker;
	}
	
	public void setIsIdleBlocker(boolean isIdleBlocker){
		this.isIdleBlocker=isIdleBlocker;
	}	
	
	public String toString(){
		StringBuilder buf = new StringBuilder("BlockingRow[");
		buf.append("rootBlockerId=").append(rootBlockerId);
		buf.append(",waiterId=").append(waiterId);
		buf.append(",blockerId=").append(blockerId);
		if (sessionId!=null){
			buf.append(",sessionId=").append(sessionId);	
		}		
		buf.append(",isRootBlocker=").append(isRootBlocker);
		buf.append(",isIdleBlocker=").append(isIdleBlocker);
		buf.append(",isDeadlock=").append(isDeadlock);
		
		for (Map.Entry<BlockingDimensionType,Long> entry : dimensionMap.entrySet()) {
			buf.append(",").append(entry.getKey()).append("=").append(entry.getValue());
		}
		
		buf.append(",waitTime=").append(waitTime);		
		buf.append("]");
		return buf.toString();
	}
}
