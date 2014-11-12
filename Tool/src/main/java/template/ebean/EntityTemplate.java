package template.ebean;

import java.util.List;
import ebean.model.TableEntity;
import ebean.model.ImportManager;
import ebean.model.ColumnEntity;

public class EntityTemplate
{
  protected static String nl;
  public static synchronized EntityTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    EntityTemplate result = new EntityTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package models.entities;" + NL;
  protected final String TEXT_2 = NL + "import java.sql.Timestamp;";
  protected final String TEXT_3 = NL + "import java.util.ArrayList;" + NL + "import java.util.List;";
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL + "import javax.persistence.CascadeType;";
  protected final String TEXT_6 = NL + "import javax.persistence.Column;";
  protected final String TEXT_7 = NL + "import javax.persistence.Entity;";
  protected final String TEXT_8 = NL + "import javax.persistence.Id;";
  protected final String TEXT_9 = NL + "import javax.persistence.JoinColumn;";
  protected final String TEXT_10 = NL + "import javax.persistence.OneToMany;";
  protected final String TEXT_11 = NL + "import javax.persistence.OneToOne;";
  protected final String TEXT_12 = NL + "import javax.persistence.Version;";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL + "import com.avaje.ebean.annotation.CreatedTimestamp;";
  protected final String TEXT_15 = NL + "import com.avaje.ebean.annotation.UpdatedTimestamp;";
  protected final String TEXT_16 = NL + "/**" + NL + " * ";
  protected final String TEXT_17 = NL + " * <p>" + NL + " * ";
  protected final String TEXT_18 = NL + " * </p>" + NL + " * @author ";
  protected final String TEXT_19 = NL + " * @version ";
  protected final String TEXT_20 = NL + " */" + NL + "@Entity" + NL + "public class ";
  protected final String TEXT_21 = " extends BaseEntity {" + NL;
  protected final String TEXT_22 = NL + "\t/** ";
  protected final String TEXT_23 = " */" + NL + "\t";
  protected final String TEXT_24 = "@Id";
  protected final String TEXT_25 = NL + "\t@Column(nullable = ";
  protected final String TEXT_26 = ", columnDefinition =\"";
  protected final String TEXT_27 = "(";
  protected final String TEXT_28 = ")";
  protected final String TEXT_29 = " default columnEntity.initValue";
  protected final String TEXT_30 = "\"";
  protected final String TEXT_31 = ", updatable=true";
  protected final String TEXT_32 = ")" + NL + "\tpublic String ";
  protected final String TEXT_33 = ";";
  protected final String TEXT_34 = NL + "\t" + NL + "\t/** ";
  protected final String TEXT_35 = "のFinderクラス */" + NL + "\tpublic static Finder<String, ";
  protected final String TEXT_36 = "> find = " + NL + "    \t\tBaseEntity.getFinder(String.class, ";
  protected final String TEXT_37 = ".class);" + NL + "" + NL + "}";
  protected final String TEXT_38 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     TableEntity tableEntity = (TableEntity) argument; 
    stringBuffer.append(TEXT_1);
     ImportManager importManager = tableEntity.getImportManager(); 
     if(importManager.hasTimestamp) {
    stringBuffer.append(TEXT_2);
     }
     if(importManager.hasList) {
    stringBuffer.append(TEXT_3);
     }
    stringBuffer.append(TEXT_4);
     if(importManager.hasCascadeType) {
    stringBuffer.append(TEXT_5);
     }
     if(importManager.hasColumn) {
    stringBuffer.append(TEXT_6);
     }
     if(importManager.hasEntity) {
    stringBuffer.append(TEXT_7);
     }
     if(importManager.hasId) {
    stringBuffer.append(TEXT_8);
     }
     if(importManager.hasJoinColumn) {
    stringBuffer.append(TEXT_9);
     }
     if(importManager.hasOneToMany) {
    stringBuffer.append(TEXT_10);
     }
     if(importManager.hasOneToOne) {
    stringBuffer.append(TEXT_11);
     }
     if(importManager.hasVersion) {
    stringBuffer.append(TEXT_12);
     }
    stringBuffer.append(TEXT_13);
     if(importManager.hasCreatedTimestamp) {
    stringBuffer.append(TEXT_14);
     }
     if(importManager.hasUpdatedTimestamp) {
    stringBuffer.append(TEXT_15);
     }
    stringBuffer.append(TEXT_16);
    stringBuffer.append(tableEntity.tableLogicName );
    stringBuffer.append(TEXT_17);
    stringBuffer.append(tableEntity.usage );
    stringBuffer.append(TEXT_18);
    stringBuffer.append(tableEntity.writer );
    stringBuffer.append(TEXT_19);
    stringBuffer.append(tableEntity.version );
    stringBuffer.append(TEXT_20);
    stringBuffer.append(tableEntity.tableName);
    stringBuffer.append(TEXT_21);
     List<ColumnEntity> columnEntityList = tableEntity.getColumnEntityList(); 
     for(ColumnEntity columnEntity: columnEntityList){ 
    stringBuffer.append(TEXT_22);
    stringBuffer.append(columnEntity.columnLogicName );
    stringBuffer.append(TEXT_23);
     if(columnEntity.isPrimaryKey){
    stringBuffer.append(TEXT_24);
    }
    stringBuffer.append(TEXT_25);
    stringBuffer.append(columnEntity.nullable);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(columnEntity.type );
    stringBuffer.append(TEXT_27);
    stringBuffer.append(columnEntity.length );
    stringBuffer.append(TEXT_28);
     if(columnEntity.initValue!=null&&!columnEntity.initValue.equals("")){
    stringBuffer.append(TEXT_29);
    }
    stringBuffer.append(TEXT_30);
     if(columnEntity.updatable){
    stringBuffer.append(TEXT_31);
    }
    stringBuffer.append(TEXT_32);
    stringBuffer.append(columnEntity.columnName );
    stringBuffer.append(TEXT_33);
     }
    stringBuffer.append(TEXT_34);
    stringBuffer.append(tableEntity.tableLogicName );
    stringBuffer.append(TEXT_35);
    stringBuffer.append(tableEntity.tableName );
    stringBuffer.append(TEXT_36);
    stringBuffer.append(tableEntity.tableName );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(TEXT_38);
    return stringBuffer.toString();
  }
}
