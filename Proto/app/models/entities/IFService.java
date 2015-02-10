package models.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

public interface IFService {
    
    /** 回線サービス */
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    public List<SvcKaisen> svcKaisen = new ArrayList<SvcKaisen>();
    
    /** メールサービス */
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    public List<SvcMail> svcMail = new ArrayList<SvcMail>();
    
    /** セキュリティ関連サービス */
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    public List<SvcSecurity> svcSecurity = new ArrayList<SvcSecurity>();
    
    /** オプションサービス */
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    public List<SvcOption> svcOption = new ArrayList<SvcOption>();

}
