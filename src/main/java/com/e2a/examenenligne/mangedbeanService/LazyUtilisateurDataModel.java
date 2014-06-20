package com.e2a.examenenligne.mangedbeanService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.e2a.examenenligne.ejb.serviceDAO.DataAccessService;
import com.e2a.examenenligne.entities.UtilisateurExpert;

// to activate  the sorting and the selection mode in the presentation  
public class LazyUtilisateurDataModel  extends LazyDataModel<UtilisateurExpert>  implements Serializable{
	
	
	// Data Source for binding data to the DataTable
    private List<UtilisateurExpert> datasource;
    // Selected Page size in the DataTable
    private int pageSize;
    // Current row index number
    private int rowIndex;
    // Total row number
    private int rowCount;
    // Data Access Service for create read update delete operations
    private DataAccessService crudService;
    
    public LazyUtilisateurDataModel(DataAccessService das){
    	this.crudService=das ;
    }
    
    
    

    /**
     * Lazy loading user list with sorting ability
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @param filters
     * @return List<User>
     */ 
     @Override
    public List<UtilisateurExpert> load(int first, int pageSize,
    		String sortField, SortOrder sortOrder, Map<String, String> filters) {

         datasource = crudService.findWithNamedQuery("findAllUsers", first, first + pageSize);    	 
         setRowCount(crudService.countTotalRecord("countUsers"));   
         return datasource;
         
    }
    
     
     @Override
    public boolean isRowAvailable() {
         if(datasource == null) 
             return false;
         int index = rowIndex % pageSize ; 
         return index >= 0 && index < datasource.size();
     }
     
     /**
      * Gets the user object's primary key
      * @param user
      * @return Object
      */
     @Override
     public Object getRowKey(UtilisateurExpert user) {
         return user.getId();
     }
    
     
     @Override
    public UtilisateurExpert getRowData() {

    	 if(datasource == null)
             return null;
         int index =  rowIndex % pageSize;
         if(index > datasource.size()){
             return null;
         }

         return datasource.get(index);
    }
     
     @Override
    public UtilisateurExpert getRowData(String rowKey) {

    	   if(datasource == null)
               return null;
    	   int i = Integer.parseInt(rowKey);
          for(UtilisateurExpert user : datasource) {  
              if(user.getId() == i)  
              return user;  
          }  
          return null; 
    }
    
    
    
  //********************************Getters  & Setters**********************************
	
     public List<UtilisateurExpert> getDatasource() {
		return datasource;
	}
	public void setDatasource(List<UtilisateurExpert> datasource) {
		this.datasource = datasource;
	}
	@Override
	public int getPageSize() {
		return pageSize;
	}
	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public int getRowIndex() {
		return rowIndex;
	}
	@Override
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	@Override
	public int getRowCount() {
		return rowCount;
	}
	@Override
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	   /**
     * Sets wrapped data
     * @param list
     */
    @Override
    public void setWrappedData(Object list) {
        this.datasource = (List<UtilisateurExpert>) list;
    }
    
    /**
     * Returns wrapped data
     * @return
     */
    @Override
    public Object getWrappedData() {
        return datasource;
    }
    
    

}
