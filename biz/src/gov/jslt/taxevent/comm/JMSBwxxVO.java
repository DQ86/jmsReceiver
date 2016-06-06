package gov.jslt.taxevent.comm;
import com.ctp.core.bpo.CLOBObject;
import com.ctp.core.bpo.CssBaseVOWithLOB;

public class JMSBwxxVO extends CssBaseVOWithLOB {

public JMSBwxxVO() {
super();
}

    //业务主键;
    private String bizkey;

    //报文类型0金三报文1大集中报文;
    private String bwlxdm;

    //错误日志;
    private CLOBObject cwrz;

    //金三登记序号;
    private String djxh;

    //返回报文;
    private CLOBObject fhbw;

    //发送报文;
    private CLOBObject fsbw;

    //1表示内网发起0表示外网发起;
    private String sjly;

    //税务管理码;
    private String swglm;

    //流水好;
    private String uuid;

    //T_DM_GY_JMSJSZT;
    private String ztdm;


 public String getBizkey() {
   return bizkey;
}

 public String getBwlxdm() {
   return bwlxdm;
}

 public CLOBObject getCwrz() {
   return cwrz;
}

 public String getDjxh() {
   return djxh;
}

 public CLOBObject getFhbw() {
   return fhbw;
}

 public CLOBObject getFsbw() {
   return fsbw;
}

 public String getSjly() {
   return sjly;
}

 public String getSwglm() {
   return swglm;
}

 public String getUuid() {
   return uuid;
}

 public String getZtdm() {
   return ztdm;
}


  public void  setBizkey(String bizkey) {
    status.put("BIZKEY", bizkey);
    this.bizkey=bizkey;
}

  public void  setBwlxdm(String bwlxdm) {
    status.put("BWLX_DM", bwlxdm);
    this.bwlxdm=bwlxdm;
}

  public void  setCwrz(CLOBObject cwrz) {
    clobBuffer.put("CWRZ", cwrz);
    this.cwrz=cwrz;
}

  public void  setDjxh(String djxh) {
    status.put("DJXH", djxh);
    this.djxh=djxh;
}

  public void  setFhbw(CLOBObject fhbw) {
    clobBuffer.put("FHBW", fhbw);
    this.fhbw=fhbw;
}

  public void  setFsbw(CLOBObject fsbw) {
    clobBuffer.put("FSBW", fsbw);
    this.fsbw=fsbw;
}

  public void  setSjly(String sjly) {
    status.put("SJLY", sjly);
    this.sjly=sjly;
}

  public void  setSwglm(String swglm) {
	/** 数据库SWGLM为Long类型;
	 	类型转换 String  ->Long */
    if(null!=swglm&&swglm.length()>0 ) {
    	status.put("SWGLM", new Long(swglm));
    }
    this.swglm=swglm;
}

  public void  setUuid(String uuid) {
    status.put("UUID", uuid);
    this.uuid=uuid;
}

  public void  setZtdm(String ztdm) {
    status.put("ZT_DM", ztdm);
    this.ztdm=ztdm;
}

////////////////////////////////////////以下为【clob的字段转换为String的部分】//////////////////////////////////////////////////// 
  //cwrz 的String类型  cwrz  转换Clob类型 Set方法
  public void  setCwrzStr(String str_cwrz ){
	if (str_cwrz!=""){
 		CLOBObject addclob=  new CLOBObject();
 		addclob.setContent(str_cwrz.toCharArray() );
		this.setCwrz(addclob);
	}else
		this.setCwrz(null);
}

  //cwrz  Clob类转换 cwrz 的String 的类型 Get方法
  public String  getCwrzStr(){
	if (null!=this.cwrz)
		return cwrz.toString();
	return "";
}

  //fhbw 的String类型  fhbw  转换Clob类型 Set方法
  public void  setFhbwStr(String str_fhbw ){
	if (str_fhbw!=""){
 		CLOBObject addclob=  new CLOBObject();
 		addclob.setContent(str_fhbw.toCharArray() );
		this.setFhbw(addclob);
	}else
		this.setFhbw(null);
}

  //fhbw  Clob类转换 fhbw 的String 的类型 Get方法
  public String  getFhbwStr(){
	if (null!=this.fhbw)
		return fhbw.toString();
	return "";
}

  //fsbw 的String类型  fsbw  转换Clob类型 Set方法
  public void  setFsbwStr(String str_fsbw ){
	if (str_fsbw!=""){
 		CLOBObject addclob=  new CLOBObject();
 		addclob.setContent(str_fsbw.toCharArray() );
		this.setFsbw(addclob);
	}else
		this.setFsbw(null);
}

  //fsbw  Clob类转换 fsbw 的String 的类型 Get方法
  public String  getFsbwStr(){
	if (null!=this.fsbw)
		return fsbw.toString();
	return "";
}

////////////////////////////////////////以下为【Calendar的字段转换为String的部分】/////////////////////////////////////////////// 
/*
	yyyy-MM-dd HH:mm:ss 第0种
	yyyy/MM/dd HH:mm:ss	第1种
	yyyy年MM月dd日HH时mm分ss秒 第2种
	yyyy-MM-dd 第3种
	yyyy/MM/dd 第4种
	y-MM-dd 第5种
	yy/MM/dd 第6种
	yyyy年MM月dd日 第7种
	HH:mm:ss 第8种
	yyyyMMddHHmmss 第9种
	yyyyMMdd 第10种
	yyyy.MM.dd 第11种
	yy.MM.dd 第12种
*/
////////////////////////////////////////以下为【自定义部分】//////////////////////////////////////////////////////////////
}

