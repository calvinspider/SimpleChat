package org.yang.zhang.module;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 16:07
 */
@Entity
@Table(name="t_clientinfo")
public class ClientInfo {

    @Id
    @NotNull
    private Integer clientid;
    private Short connected;
    private Long mostsignbits;
    private Long leastsignbits;
    private Date lastconnecteddate;

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public Short getConnected() {
        return connected;
    }

    public void setConnected(Short connected) {
        this.connected = connected;
    }

    public Long getMostsignbits() {
        return mostsignbits;
    }

    public void setMostsignbits(Long mostsignbits) {
        this.mostsignbits = mostsignbits;
    }

    public Long getLeastsignbits() {
        return leastsignbits;
    }

    public void setLeastsignbits(Long leastsignbits) {
        this.leastsignbits = leastsignbits;
    }

    public Date getLastconnecteddate() {
        return lastconnecteddate;
    }

    public void setLastconnecteddate(Date lastconnecteddate) {
        this.lastconnecteddate = lastconnecteddate;
    }
}
