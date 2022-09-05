package com.chensino.core.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单权限表
 * @TableName t_menu
 */
@TableName(value ="t_menu")
@Data
public class SysMenu implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "菜单id")
    private Integer menuId;

    /**
     * 
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 
     */
    @ApiModelProperty(value = "菜单权限标识")
    private String permission;

    /**
     * 
     */
    @ApiModelProperty(value = "前端路由标识路径")
    private String path;

    /**
     * 父菜单ID
     */	@ApiModelProperty(value = "父菜单id")

    private Integer parentId;

    /**
     * 
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值")
    private Integer sort;

    /**
     * 
     */
    @ApiModelProperty(value = "路由缓冲")
    private String keepAlive;

    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @ApiModelProperty(value = "菜单类型,0:菜单 1:按钮")
    private String type;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 
     */
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public int hashCode() {
        return menuId.hashCode();
    }
    /**
     * menuId 相同则相同
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SysMenu) {
            Integer targetMenuId = ((SysMenu) obj).getMenuId();
            return menuId.equals(targetMenuId);
        }
        return super.equals(obj);
    }

}