package org.yang.zhang.module;

import java.util.logging.StreamHandler;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 05 15:18
 */
@Entity
@Table(name = "t_contract_group")
@Data
public class ContractGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String groupName;
}
