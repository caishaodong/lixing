package com.shaoxing.lixing.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 价目表
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MPriceCategory extends Model<MPriceCategory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 价目名称
     */
    private String name;

    /**
     * 是否删除（0：未删除，1：删除）
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
