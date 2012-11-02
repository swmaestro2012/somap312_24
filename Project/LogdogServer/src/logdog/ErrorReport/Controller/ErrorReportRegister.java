package logdog.ErrorReport.Controller;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import logdog.Common.DataStore.PMF;
import logdog.ErrorReport.DAO.ErrorReportInfo;
import logdog.ErrorReport.DTO.ClientReportData;
import logdog.ErrorReport.DTO.ErrorUniqueID;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

public class ErrorReportRegister {
	
	private PersistenceManager jdoConnector;
	
	private ErrorTypeClassifier EClassifier;
	
	public ErrorReportRegister() {
		super();
		jdoConnector=null;
		EClassifier=new ErrorTypeClassifier();
	}
	
	public Key insertErrorReport(ClientReportData reportInfo)
	{
		jdoConnector = PMF.getPMF().getPersistenceManager();
		
		ErrorReportInfo eInfo = new ErrorReportInfo(reportInfo);
		
		try{
				jdoConnector.makePersistent(eInfo);
		}
		catch(Exception e){
				
			return null;
				
		}
		finally{
		
			jdoConnector.close();
			
		}
	
		return eInfo.getE_ReportCode();
	}
	/**
	 *
	 * @since 2012. 11. 2.오후 12:43:20
	 * TODO 에러 파일의 타입을 매칭한다.
	 * @author Karuana
	 * @param reportKey
	 * @param uid
	 */
	public void MatchingErrorType(Key reportKey, ErrorUniqueID uid)
	{
		jdoConnector = PMF.getPMF().getPersistenceManager();
		List<ErrorReportInfo> ReportResult;
		Query SearchReportQuery = jdoConnector.newQuery(ErrorReportInfo.class);
		try{
			
		 	ErrorTypeClassifier TypeClassifier = new ErrorTypeClassifier();
			Key ErrTypeKey = TypeClassifier.UpdateErrorType(uid);
			
			SearchReportQuery.setFilter("E_ReportCode == reportKey");
			SearchReportQuery.declareParameters("Key reportKey");
			
			ReportResult = (List<ErrorReportInfo>) 
					SearchReportQuery.execute(reportKey);
			
			ErrorReportInfo targetReport =  ReportResult.get(0);
			targetReport.setE_ClassificationCode(ErrTypeKey);
		}
		catch(Exception e){
				
			
				
		}
		finally{
			SearchReportQuery.closeAll();
			jdoConnector.close();
			
		}
	
	}
	public void MatchingLogFile(Key reportKey, BlobKey filekey)
	{
		jdoConnector = PMF.getPMF().getPersistenceManager();
		List<ErrorReportInfo> ReportResult;
		Query SearchReportQuery = jdoConnector.newQuery(ErrorReportInfo.class);
		try{
			
		 	
			SearchReportQuery.setFilter("E_ReportCode == reportKey");
			SearchReportQuery.declareParameters("Key reportKey");
			
			ReportResult = (List<ErrorReportInfo>) 
					SearchReportQuery.execute(reportKey);
			
			ErrorReportInfo targetReport =  ReportResult.get(0);
			targetReport.setLogBolbKey(filekey);
		}
		catch(Exception e){
				
			
				
		}
		finally{
			SearchReportQuery.closeAll();
			jdoConnector.close();
			
		}
	}
	
	public List<ErrorReportInfo> getErrorReport(Key ErrorType)
	{
		return null;
	}
	
	public boolean deleteErrorReport(Key reportKey)
	{
		return true;
	}	
	
}