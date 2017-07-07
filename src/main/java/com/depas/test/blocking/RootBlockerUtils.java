package com.depas.test.blocking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RootBlockerUtils {
				
	private static final RootBlockerUtils rootBlockerUtils = new RootBlockerUtils();
	
	private RootBlockerUtils(){}
	
	public static Map<BlockingDimensionType, Map<Long, RootBlockerDimensionTime>> getRootBlockerDimensionTimeMap(final List<BlockingRow> blockingRowList){
		return getRootBlockerDimensionTimeMap(blockingRowList, null);
	}
	
	/**
	 * Given a list of blockers and waiters, this method will determine the Root Blockers/Idle Blockers and their descendants.
	 * Aggregate the wait time of the descendants to their Root Blockers and return a Map of Root BLockers dimensions with
	 * their impact and blocking times.
	 * 
	 * @param blockingRowList
	 * @param includeNegativeSqlHashes - false and it will throw out all Root Blockers and descendants with SQL Hashes <= -10 
	 * @return
	 */
	public static Map<BlockingDimensionType, Map<Long, RootBlockerDimensionTime>> getRootBlockerDimensionTimeMap(final List<BlockingRow> blockingRowList, 
												Map<DataDimension, List<String>> dimensionFilters){
		
		if (blockingRowList==null){
			throw new IllegalArgumentException("The blocking row list cannot be null.");
		}
		
		boolean includeNegativeSqlHashes=false;
		if (dimensionFilters!=null && dimensionFilters.size()>0){
			includeNegativeSqlHashes=true;
		}
		
		// create a convenience to quickly lookup blocker rows   
//		Map<Long,BlockingRow> blockersMap = new HashMap<>();
//						
//		for (BlockingRow blockingRow : blockingRowList) {
//			if (blockingRow.getBlockerId()!=null){
//				blockersMap.put(blockingRow.getBlockerId(), blockingRow);
//			}
//		}
		
		// (br1, br2) -> br2) this is for the merge function for keys that collide this says just take the second object, you could also do (s, a) -> s + ", " + a this combines the two objects
//		Map<Long,BlockingRow> blockersMap = blockingRowList.stream().filter(b -> b.getBlockerId()!=null).collect(Collectors.toMap(BlockingRow::getBlockerId, Function.identity(), (br1, br2) -> br1));	
		Map<Long,BlockingRow> blockersMap = 
				blockingRowList.stream()
				.filter(b -> b.getBlockerId()!=null)
				.collect(Collectors.toMap(BlockingRow::getBlockerId, Function.identity()));	
		
		// update all the blocking rows root blockers
//		for (BlockingRow blockingRow : blockingRowList) {	
//			int recursiveCount=0;
//			updateBlockersRootBlocker(blockingRow, blockingRow, blockersMap, recursiveCount);
//		}
				
		// this is most likely slower than just the plan for loop above
		blockingRowList.forEach((blockingRow) -> {int recursiveCount=0;
												  updateBlockersRootBlocker(blockingRow, blockingRow, blockersMap, recursiveCount);});
		
		// create the Root Blocker Map
//		Map<Long,RootBlocker> rootBlockerMap = new HashMap<>();
		
		// add all root blockers to Root Blocker Map
//		for (BlockingRow blockingRow : blockingRowList) {			
//			if (blockingRow.isRootBlocker()){										
//				rootBlockerMap.put(blockingRow.getBlockerId(), rootBlockerUtils.new RootBlocker(blockingRow));														
//			}
//		}
		
		Map<Long,RootBlocker> rootBlockerMap = blockingRowList.stream()
						.filter(b -> b.isRootBlocker())
						.collect(Collectors.toMap(BlockingRow::getBlockerId,b -> rootBlockerUtils.new RootBlocker(b)));
		
		// need a specific IDLE Blocking Map to handle the dimension Filter
		// IDLE Blocking time only gets counted if one of the IDLE waiters matches the criteria 
		Map<Long,RootBlocker> idleRootBlockersMap = new HashMap<>();

		// use IDLE_BLOCKER enum hash code (-90) as the idle blocker id
		Long idleRootBlockerId = new Long(BlockingDimensionType.IDLE_BLOCKER.getHashCode());		
		
		// add all non root blockers time to their Roots in the Root Blocker Map
		for (BlockingRow blockingRow : blockingRowList) {										
			RootBlocker rootBlocker = null;
			if (blockingRow.isIdleBlocker()){
				// put idle blockers into idleRootBlockersMap based on their waiter id (blee which is the negative sid of the blocker)					
				rootBlocker = idleRootBlockersMap.get(blockingRow.getRootBlockerId());	
				if (rootBlocker==null){
					// create and add the idle root blocker to the idleRootBlockersMap
					BlockingRow idleRootBlockingRow = new BlockingRow(blockingRow.getRootBlockerId(),null,null,0);
					idleRootBlockingRow.setIsIdleBlocker(true);
					idleRootBlockingRow.setIsRootBlocker(true);
					rootBlocker = rootBlockerUtils.new RootBlocker(idleRootBlockingRow);
					idleRootBlockersMap.put(blockingRow.getRootBlockerId(), rootBlocker);
				}
			}
			else if (!blockingRow.isRootBlocker()){
				Long rootBlockerId = blockingRow.getRootBlockerId();
				rootBlocker = rootBlockerMap.get(rootBlockerId);
			}				
			if (rootBlocker!=null){
				if (isBlockingRowAFilterMatch(blockingRow,dimensionFilters)){
					// if the blocking row matches the dimension filter criteria then flag the root blocker
					// so it can be included in the result (dimensionWaitTimeMap)
					rootBlocker.setHasWaiterThatMatchesDimensionFilter(true);
				}					
				rootBlocker.addImpactTime(blockingRow.getWaitTime());
			}
//				else{
//					//this is a root blocker so skip
//				}				
		}
		
		// create the map of Map<BlockingDimensionType, Map<Long, Double>>  the internal map is dimension Ids as the key and the value is the total weight time
		// this makes it faster to add to time to the correct bucket
		Map<BlockingDimensionType, Map<Long, RootBlockerDimensionTime>> dimensionWaitTimeMap = new HashMap<>();
		// load up the dimension map with all the Blocking Dimension Types
		for (BlockingDimensionType blockingDimensionType : BlockingDimensionType.values()) {			
			if (BlockingDimensionType.IDLE_BLOCKER == blockingDimensionType){				
				Map<Long, RootBlockerDimensionTime> dimensionIdWaitTime = new HashMap<>();
				dimensionIdWaitTime.put(idleRootBlockerId, new RootBlockerDimensionTime(BlockingDimensionType.IDLE_BLOCKER,idleRootBlockerId.longValue()));				
				dimensionWaitTimeMap.put(blockingDimensionType, dimensionIdWaitTime);
			}
			else{
				dimensionWaitTimeMap.put(blockingDimensionType, new HashMap<>());
			}			
		} 
		
		// go through the root blocker map and get all the wait times by dimension type and Id
		// store in dimensionWaitTimeMap 
		for (Map.Entry<Long, RootBlocker> rootBlockerRowEntry : rootBlockerMap.entrySet()) {
			RootBlocker rootBlocker = rootBlockerRowEntry.getValue();			
			// only include root blockers that have at least one waiter that matched the filter criteria 
			if (rootBlocker.hasWaiterThatMatchesDimensionFilter()){ 				
				Map<BlockingDimensionType, Long> dimensions = rootBlocker.getBlockingRow().getDimensions();
				
				// rip through all the dimensions of the root blocker and add their time to correct dimension in the map 
				for (Map.Entry<BlockingDimensionType, Long> dimensionEntry : dimensions.entrySet()) {
					BlockingDimensionType dimType = dimensionEntry.getKey();
					Long dimId = dimensionEntry.getValue();
					
					if (BlockingDimensionType.SQL!=dimType || includeNegativeSqlHashes || !isNegativeSQLHash(dimId)){
						Map<Long, RootBlockerDimensionTime> dimensionWaitTime = dimensionWaitTimeMap.get(dimType);
						RootBlockerDimensionTime rootBlockersDimensionTime =  dimensionWaitTime.get(dimId);
						if (rootBlockersDimensionTime==null){
							// no entry for this dimension and dimension Id so create the map
							rootBlockersDimensionTime = new RootBlockerDimensionTime(dimType,dimId.longValue());						
							dimensionWaitTime.put(dimId, rootBlockersDimensionTime);					
						}					
											
						rootBlockersDimensionTime.addImpactTime(rootBlocker.getImpactTime());	
						rootBlockersDimensionTime.addBlockingTime(rootBlocker.getBlockingTime());	
					}
										
				}
			}			
		}
				
		// go through the idle root blocker map and get all the wait times 
		// and store in dimensionWaitTimeMap under the Idle Dimension 
		for (Map.Entry<Long, RootBlocker> idleRootBlockerRowEntry : idleRootBlockersMap.entrySet()) {
			RootBlocker idleRootBlocker = idleRootBlockerRowEntry.getValue();	
			if (idleRootBlocker.hasWaiterThatMatchesDimensionFilter()){ 
				Map<Long, RootBlockerDimensionTime> dimensionWaitTime = dimensionWaitTimeMap.get(BlockingDimensionType.IDLE_BLOCKER);
				RootBlockerDimensionTime rootBlockersDimensionTime = dimensionWaitTime.get(idleRootBlockerId);
				if (rootBlockersDimensionTime!=null){
					rootBlockersDimensionTime.addImpactTime(idleRootBlocker.getImpactTime());						
				}					
			}		
		}
						
		return dimensionWaitTimeMap;				
	}
	
	public static List<RootBlockerDimensionTime> getRootBlockerDimensionTimeList(final List<BlockingRow> blockingRowList){
		 return getRootBlockerDimensionTimeList(blockingRowList, null);
	}
	
	/**
	 * Given a list of blockers and waiters, this method will determine the Root Blockers/Idle Blockers and their descendants.
	 * Aggregate the wait time of the descendants to their Root Blockers and return a List of RootBlockerDimensionTime(dimensions with
	 * their impact and blocking times).
	 * 
	 * @param blockingRowList
	 * @param includeNegativeSqlHashes - false and it will throw out all Root Blockers and descendants with SQL Hashes <= -10 
	 * @return List of RootBlockerDimensionTime
	 */
	public static List<RootBlockerDimensionTime> getRootBlockerDimensionTimeList(final List<BlockingRow> blockingRowList, 
							final Map<DataDimension, List<String>> dimensionFilters){
		
		Map<BlockingDimensionType, Map<Long, RootBlockerDimensionTime>> dimensionWaitTimeMap = getRootBlockerDimensionTimeMap(blockingRowList, dimensionFilters);
		
		List<RootBlockerDimensionTime> rootBlockerDimensionTimeList = new ArrayList<>();
		for (Map.Entry<BlockingDimensionType, Map<Long, RootBlockerDimensionTime>> impactTimeMapEntry : dimensionWaitTimeMap.entrySet()) {
			for (Map.Entry<Long, RootBlockerDimensionTime> entry : impactTimeMapEntry.getValue().entrySet()) {
								
//				System.out.println(entry.getValue());					
				rootBlockerDimensionTimeList.add(entry.getValue());
			}
		}
								
		return rootBlockerDimensionTimeList;
	}
	
	/**
	 * Returns true if a blocking row matches the dimension filter criteria.
	 * A match is true if the blocking row matches a Dimension Id for each 
	 * dimension type.
	 * 
	 * Ex. 
	 * If dimension filter is [SQL,(1000,2000)],[PROGRAM,(3000,4000)]
	 * 
	 * Row 1 has [SQL,1000] and [PROGRAM,(4000)] this returns true
	 * Row 2 has [SQL,1000] and [PROGRAM,(1000)] this returns false
	 * 
	 * @param blockingRow
	 * @param dimensionFilters
	 * @return
	 */
	public static boolean isBlockingRowAFilterMatch(BlockingRow blockingRow,Map<DataDimension, List<String>> dimensionFilters){
		if (dimensionFilters==null){						
//			System.out.println("No filter has been set.");				
			return true;
		}
		for (Map.Entry<DataDimension, List<String>> dimensionFilterEntry : dimensionFilters.entrySet()) {
						
			DataDimension dimType = dimensionFilterEntry.getKey();
			List<String> dimValueList = dimensionFilterEntry.getValue();
			boolean foundDimId = false;
			
			if (DataDimension.SESSION != dimType){
				BlockingDimensionType blockingDimType = dimensionFilterEntry.getKey().getBlockingDimensionType();
				for (String dimValueStr : dimValueList) {
					try {
						Long dimValue = Long.valueOf(dimValueStr);
						Long blockingRowDimId = blockingRow.getDimensions().get(blockingDimType);					 
						if (dimValue.equals(blockingRowDimId)){
							// blocking row matched the dimension id of the dimension type
							foundDimId = true;
							break;
						}												
					} catch (NumberFormatException e) {											
//						System.out.println("Filter dimension id is not a long so it doesn't match, dimension: " + dimType + " value: " + dimValueStr);							
						return false;
					}										
				}				
			}
			else{
				// session is not an enum in BlockingDimensionType
				// and is a not in the BlockingRow Dimensions
				// so need to handle it separate from the other dimensions
				for (String dimValueStr : dimValueList) {
					// assume if null is one of the values it will match
					if (dimValueStr==null || dimValueStr.equals(blockingRow.getSessionId())){
						foundDimId = true;
						break;
					}
				}
			}
			
			if (!foundDimId){								
//				System.out.println("Didn't find a match for dimension: " + dimType + " and values: " + dimValueList);					
				return false;	
			}
		}
		
		return true;
	}
	
	public static void updateBlockersRootBlocker(BlockingRow blocker, BlockingRow originalBlocker, Map<Long,BlockingRow> blockersMap, int recursiveCount){

		if(originalBlocker!=null && !originalBlocker.isRootBlocker() && !originalBlocker.isDeadlock()){
			if (originalBlocker.getRootBlockerId() == null ){
				Long waiterId = blocker.getWaiterId();
				if (blocker.isDeadlock()){
					// this is a deadlock, so the original is deadlock as well
					originalBlocker.setIsDeadlock(true);
				}
				else if (blocker.isRootBlocker()){
					// this the root blocker
					originalBlocker.setRootBlockerId(blocker.getBlockerId());
				}
				else if (waiterId == null){
					// this is the root blocker but hasn't been marked as yet
					originalBlocker.setRootBlockerId(blocker.getBlockerId());
					blocker.setRootBlockerId(blocker.getBlockerId());
					blocker.setIsRootBlocker(true);
				}
				else if (originalBlocker.getBlockerId()!=null && blocker.getWaiterId()!=null 
								&& originalBlocker.getBlockerId().equals(blocker.getWaiterId())){
					// this is a deadlock need to get out there is no root blocker
					originalBlocker.setIsDeadlock(true);
					blocker.setIsDeadlock(true);
				}		
				else{					
					if (waiterId.longValue() < 0) {
						// blocked by an idle blocker
						originalBlocker.setIsIdleBlocker(true);
						originalBlocker.setRootBlockerId(waiterId);
					}
					else{
						// haven't found the root blocker yet get the blocker that the parent is waiting on and keep going
						BlockingRow parentBlocker = blockersMap.get(waiterId);
						if (parentBlocker!=null){
							if (originalBlocker.getWaiterId() != null && originalBlocker.getWaiterId().equals(parentBlocker.getWaiterId())){
								// this is a deadlock need to get out there is no root blocker
								originalBlocker.setIsDeadlock(true);
								blocker.setIsDeadlock(true);
								parentBlocker.setIsDeadlock(true);
							}
							else{
								if (recursiveCount++ < 500){
									updateBlockersRootBlocker(parentBlocker, originalBlocker, blockersMap,recursiveCount);		
								}
								else{
									// this is most likely a deadlock case
									originalBlocker.setIsDeadlock(true);
									blocker.setIsDeadlock(true);
									parentBlocker.setIsDeadlock(true);									
//									System.out.println("Recursive count was too high marking blocker as a deadlock: " + originalBlocker);																												
								}									
							}														
						}
						else{							 							
//							System.out.println("The parent for the blocker " + originalBlocker + " was not available");								
							
						}						
					}
				}				
			}
		}				
	}
	
	public static boolean isNegativeSQLHash(Long sqlHash){
		if (sqlHash==null){
			return false;
		}
		return sqlHash.longValue() <= -10L;
	}
	
	/**
	 * Given a list of blockers and waiters, this method will determine the Root Blockers/Idle Blockers and their descendants.
	 * Aggregate the wait time of the descendants to their Root Blockers and return a Map of Root BLockers dimensions with
	 * their impact and blocking times.
	 * 
	 * @param blockerWaiterList
	 * @param includeNegativeSqlHashes - false and it will throw out all Root Blockers and descendants with SQL Hashes <= -10 
	 * @return
	 */
	class RootBlocker{
		private BlockingRow blockingRow;	
		private double totalImpactTime=0;		
		private boolean hasWaiterThatMatchesDimensionFilter=false;
		
		public boolean hasWaiterThatMatchesDimensionFilter() {
			return hasWaiterThatMatchesDimensionFilter;
		}

		public void setHasWaiterThatMatchesDimensionFilter(boolean matchesDimensionFilter) {
			this.hasWaiterThatMatchesDimensionFilter = matchesDimensionFilter;
		}

		public RootBlocker(BlockingRow blockingRow) {
			this.blockingRow=blockingRow;		
		}

		public BlockingRow getBlockingRow(){
			return blockingRow;
		}
			
		public void addImpactTime(double impactTime){
			totalImpactTime=totalImpactTime + impactTime;
		}
		
		public double getImpactTime(){
			return totalImpactTime;
		}
		
		public double getBlockingTime(){
			return blockingRow.getWaitTime();
		}
		
		public boolean isIdleRootBlocker(){
			return blockingRow.isIdleBlocker();
		}
	}
}