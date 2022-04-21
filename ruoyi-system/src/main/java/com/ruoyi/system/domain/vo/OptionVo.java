package com.ruoyi.system.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuwei
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionVo {
    private String value;
    private String label;
}
