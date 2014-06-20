package com.e2a.examenenligne.mangedbeanService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import com.e2a.examenenligne.ejb.serviceDAO.DataAccessService;
import com.e2a.examenenligne.entities.HisExam;


// to activate  the sorting and the selection mode in the presentation  
public class LazyHistoriqueExamensDataModel  extends LazyDataModel<HisExam>  implements Serializable{
	
	
	// Data Source for binding data to the DataTable
    private List<HisExam> datasource;


	// Selected Page size in the DataTable
    private int pageSize;
    // Current row index number
    private int rowIndex;
    // Total row number
    private int rowCount;
    // Data Access Service for create read update delete operations
    private DataAccessService crudService;
    String login;
    
    public LazyHistoriqueExamensDataModel(DataAccessService das, String expertLogin){
    	this.crudService=das ;
    	this.login=expertLogin;
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
    public List<HisExam> load(int first, int pageSize,
    		String sortField, SortOrder sortOrder, Map<String, String> filters) {

         datasource = crudService.findWithNamedQuery("findAllHisExamsByUtilisateur","login", login, first, first + pageSize);    	 
         setRowCount(crudService.countTotalRecord("countExamens"));   
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
     public Object getRowKey(HisExam examen) {
         return examen.getId();
     }
    
     
     @Override
    public HisExam getRowData() {

    	 if(datasource == null)
             return null;
         int index =  rowIndex % pageSize;
         if(index > datasource.size()){
             return null;
         }

         return datasource.get(index);
    }
     
     @Override
    public HisExam  getRowData(String rowKey) {

    	   if(datasource == null)
               return null;
    	   int i = Integer.parseInt(rowKey);
          for(HisExam hexamen : datasource) {  
              if(hexamen.getId() == i)  
              return hexamen;  
          }  
          return null; 
    }
    
    
    
     //********************************Getters  & Setters**********************************
     public List<HisExam> getDatasource() {
 		return datasource;
 	}

 	public void setDatasource(List<HisExam> datasource) {
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
        this.datasource = (List<HisExam>) list;
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
