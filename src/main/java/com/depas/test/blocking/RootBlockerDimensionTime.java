package com.depas.test.blocking;

public class RootBlockerDimensionTime {

	private BlockingDimensionType type;
	private long dimensionId;
	private double totalImpactTime=0.0;
	private double totalBlockingTime=0.0;
	
	public RootBlockerDimensionTime (BlockingDimensionType type, long dimensionId){
		this.type=type;
		this.dimensionId=dimensionId;
	}
	
	public BlockingDimensionType getType() {
		return type;
	}

	public long getId() {
		return dimensionId;
	}

	public double getImpactTime() {
		return totalImpactTime;
	}

	public void addImpactTime(double impactTime){
		totalImpactTime=totalImpactTime + impactTime;
	}
	
	public double getBlockingTime() {
		return totalBlockingTime;
	}	
	
	public void addBlockingTime(double blockingTime){
		totalBlockingTime=totalBlockingTime + blockingTime;
	}

	public String toString(){
		StringBuilder buf = new StringBuilder("RootBlockersDimensionTime[");
		buf.append("BlockingDimensionType=").append(type);
		buf.append(",dimensionId=").append(dimensionId);
		buf.append(",totalImpactTime=").append(totalImpactTime);		
		buf.append(",totalBlockingTime=").append(totalBlockingTime);		
		buf.append("]");
		return buf.toString();
	}
	
}
